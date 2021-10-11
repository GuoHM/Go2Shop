package com.go2shop.order.service;

import java.util.List;
import java.util.Optional;

import com.go2shop.common.exception.BusinessException;
import com.go2shop.model.order.CreateOrderDTO;
import com.go2shop.model.order.OrderDTO;
import com.go2shop.model.order.OrderSearchDTO;

public interface OrderService {
	List<OrderDTO> createOrder(CreateOrderDTO order) throws BusinessException;
	Optional<OrderDTO> findOrderById(Long id);
	void cancelPayment(List<Long> orderIds);
	void confirmPayment(String payOption, List<Long> orderIds) throws BusinessException;
	List<OrderDTO> searchOrders(OrderSearchDTO searchDTO);
	void orderReceived(OrderDTO order);
	void confirmDelivery(OrderDTO order);
}
