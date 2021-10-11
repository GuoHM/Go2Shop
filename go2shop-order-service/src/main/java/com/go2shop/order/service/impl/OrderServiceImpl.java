package com.go2shop.order.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.go2shop.common.exception.BusinessException;
import com.go2shop.common.exception.EmBusinessError;
import com.go2shop.model.cart.ShoppingCartProductDTO;
import com.go2shop.model.order.CreateOrderDTO;
import com.go2shop.model.order.OrderDTO;
import com.go2shop.model.order.OrderSearchDTO;
import com.go2shop.order.entity.Order;
import com.go2shop.order.entity.OrderDetail;
import com.go2shop.order.repository.OrderDetailRepository;
import com.go2shop.order.repository.OrderRepository;
import com.go2shop.order.service.OrderService;
import com.go2shop.order.service.feign.CatalogueService;
import com.go2shop.order.service.feign.PaymentService;
import com.go2shop.order.service.feign.ShoppingCartService;
import com.go2shop.order.service.mapper.OrderMapper;
import com.netflix.servo.util.Throwables;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private CatalogueService catalogueService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private PaymentService paymentService;
	
	@Override
	public Optional<OrderDTO> findOrderById(Long id) {
		if (id != null) {
			Optional<Order> result = orderRepository.findById(id);
			if (result.isPresent()) {
				return Optional.of(orderMapper.toDto(result.get()));
			}
			return Optional.empty();
		}
		return Optional.empty();
	}
	
	@Override
	public List<OrderDTO> createOrder(CreateOrderDTO createOrder) throws BusinessException {
		if(createOrder != null && createOrder.getBuyerId() != null && !createOrder.getCartProducts().isEmpty()) {
			List<ShoppingCartProductDTO> missingProductsCartProduct = createOrder.getCartProducts().stream().filter(p -> p.getProduct() == null).collect(Collectors.toList());
			if(!missingProductsCartProduct.isEmpty()) {
				throw new BusinessException(EmBusinessError.PRODUCT_NOT_EXIST);
			}
			Map<Long, List<ShoppingCartProductDTO>> sellerProductsMap = new HashMap<>();
			createOrder.getCartProducts().forEach(p -> {
				if(!sellerProductsMap.containsKey(p.getProduct().getUserId())) {
					sellerProductsMap.put(p.getProduct().getUserId(), new ArrayList<>());
				}
				sellerProductsMap.get(p.getProduct().getUserId()).add(p);
			});
			
			List<OrderDTO> createdOrders = new ArrayList<>();
			sellerProductsMap.keySet().forEach(key -> {
				Order order = new Order();
				order.setBuyerId(createOrder.getBuyerId());
				order.setSellerId(key);
				order.setOrderDate(LocalDateTime.now());
				order.setStatus("Pending Payment");
				order.setPaymentType(createOrder.getPaymentType());
				order = orderRepository.save(order);
				createOrderDetails(order, sellerProductsMap.get(key));
				removeProductsFromCart(sellerProductsMap.get(key));
				deductProductStock(sellerProductsMap.get(key));
				createdOrders.add(orderMapper.toDto(order));
			});
			
			return createdOrders;
		}
		return null;
	}
	
	private void createOrderDetails(Order order, List<ShoppingCartProductDTO> products) {
		List<OrderDetail> orderDetails = new ArrayList<>();
		if(order != null && products != null && !products.isEmpty()) {
			products.forEach(product -> {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrder(order);
				orderDetail.setOrderDate(order.getOrderDate());
				orderDetail.setProductID(product.getProduct().getId());
				orderDetail.setDeductQty(product.getQuantity());
				orderDetail.setPayment(product.getProduct().getPrice().multiply(BigDecimal.valueOf(product.getQuantity())));
				orderDetail.setPaymentDate(LocalDateTime.now());
				orderDetails.add(orderDetail);
			});
		}
		if(!orderDetails.isEmpty()) {
			orderDetailRepository.saveAll(orderDetails);
		}
	}
	
	private void removeProductsFromCart(List<ShoppingCartProductDTO> products) {
		if(products != null) {
			products.forEach(product -> {
				try {
					deleteShoppingCartProduct(product);
				} catch (BusinessException e) {
					throw Throwables.propagate(e);
				}
			});
		}
	}
	
	private void deleteShoppingCartProduct(ShoppingCartProductDTO product) throws BusinessException {
		ResponseEntity<Void> response = shoppingCartService.deleteShoppingCartProductById(product.getId());
		if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
			throw new BusinessException(EmBusinessError.SERVICE_NOT_AVAILABLE);
		}
	}
	
	private void deductProductStock(List<ShoppingCartProductDTO> products) {
		if(products != null) {
			products.forEach(product -> {
				try {
					deductProductStockById(product.getProduct().getId(), product);
				} catch (BusinessException e) {
					throw Throwables.propagate(e);
				}
			});
		}
	}
	
	private void deductProductStockById(Long productId, ShoppingCartProductDTO product) throws BusinessException {
		ResponseEntity<Void> response = catalogueService.deductProductStock(productId, product);
		if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
			throw new BusinessException(EmBusinessError.SERVICE_NOT_AVAILABLE);
		}
	}
	
	@Override
	public void cancelPayment(List<Long> orderIds) {
		if(!orderIds.isEmpty()) {
			orderIds.forEach(id -> {
				Optional<Order> orderOpt = orderRepository.findById(id);
				if(orderOpt.isPresent()) {
					Order order = orderOpt.get();
					order.setStatus("Cancelled");
					orderRepository.save(order);
					returnProductStock(order);
				}
			});
		}
	}
	
	private void returnProductStock(Order order) {
		if(order != null) {
			List<OrderDetail> details = orderDetailRepository.findByOrderId(order.getId());
			details.forEach(detail -> {
				try {
					returnProductStockById(detail.getProductID(), detail.getDeductQty());
				} catch (BusinessException e) {
					throw Throwables.propagate(e);
				}
			});
		}
	}
	
	private void returnProductStockById(Long productId, Integer quantity) throws BusinessException {
		ResponseEntity<Void> response = catalogueService.returnProductStock(productId, quantity);
		if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
			throw new BusinessException(EmBusinessError.SERVICE_NOT_AVAILABLE);
		}
	}
	
	@Override
	public void confirmPayment(String payOption, List<Long> orderIds) throws BusinessException {
		if(payOption != null) {
			makePayment(payOption);
		}
		if(!orderIds.isEmpty()) {
			orderIds.forEach(id -> {
				Optional<Order> orderOpt = orderRepository.findById(id);
				if(orderOpt.isPresent()) {
					Order order = orderOpt.get();
					order.setStatus("Completed Payment");
					orderRepository.save(order);
				}
			});
		}
	}
	
	private void makePayment(String payOption) throws BusinessException {
		if(payOption.equalsIgnoreCase("C")) {
			ResponseEntity<Void> response = paymentService.payByCard();
			if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
				throw new BusinessException(EmBusinessError.SERVICE_NOT_AVAILABLE);
			}
		} else if(payOption.equalsIgnoreCase("PN")) {
			ResponseEntity<Void> response = paymentService.payByPayNow();
			if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
				throw new BusinessException(EmBusinessError.SERVICE_NOT_AVAILABLE);
			}
		}
	}
	
	@Override
	public List<OrderDTO> searchOrders(OrderSearchDTO searchDTO) {
		Specification<Order> spec = getSearchOrderSpec(searchDTO);
		List<Order> result = orderRepository.findAll(spec);
		return result.stream().map(order -> {
			return orderMapper.toDto(order);
		}).collect(Collectors.toList());
	}
	
	private Specification<Order> getSearchOrderSpec(OrderSearchDTO searchDTO) {
		return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if (searchDTO == null) {
				Predicate[] p = new Predicate[predicates.size()];
				return cb.and(predicates.toArray(p));
			}
			
			if (!StringUtils.isBlank(searchDTO.getRole()) && searchDTO.getRoleId() != null) {
				Predicate p2 = null;
				if(searchDTO.getRole().equalsIgnoreCase("BUYER")) {
					p2 = cb.equal(root.get("buyerId"), searchDTO.getRoleId());
				} else if(searchDTO.getRole().equalsIgnoreCase("SELLER")) {
					p2 = cb.equal(root.get("sellerId"), searchDTO.getRoleId());
				}
				if(p2 != null) {
					predicates.add(p2);
				}
			}
			
			if (!StringUtils.isBlank(searchDTO.getOrderStatus())) {
				Predicate p3 = cb.equal(root.get("status"), searchDTO.getOrderStatus());
				predicates.add(p3);
			}
			query.distinct(true);
			Predicate[] p = new Predicate[predicates.size()];
			return cb.and(predicates.toArray(p));
		};
	}
	
	@Override
	public void orderReceived(OrderDTO orderDto) {
		if(orderDto != null) {
			Optional<Order> orderOpt = orderRepository.findById(orderDto.getId());
			if(orderOpt.isPresent()) {
				Order order = orderOpt.get();
				order.setStatus("Completed");
				orderRepository.save(order);
			}
		}
	}
	
	@Override
	public void confirmDelivery(OrderDTO orderDto) {
		if(orderDto != null) {
			Optional<Order> orderOpt = orderRepository.findById(orderDto.getId());
			if(orderOpt.isPresent()) {
				Order order = orderOpt.get();
				order.setStatus("Delivery in progress");
				orderRepository.save(order);
			}
		}
	}
}
