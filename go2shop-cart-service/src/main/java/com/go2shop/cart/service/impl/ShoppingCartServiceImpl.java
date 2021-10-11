package com.go2shop.cart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.go2shop.cart.entity.ShoppingCart;
import com.go2shop.cart.entity.ShoppingCartProduct;
import com.go2shop.cart.repository.ShoppingCartProductRepository;
import com.go2shop.cart.repository.ShoppingCartRepository;
import com.go2shop.cart.service.ShoppingCartService;
import com.go2shop.cart.service.feign.CatalogueService;
import com.go2shop.cart.service.mapper.ShoppingCartMapper;
import com.go2shop.cart.service.mapper.ShoppingCartProductMapper;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.common.exception.EmBusinessError;
import com.go2shop.model.cart.ShoppingCartDTO;
import com.go2shop.model.cart.ShoppingCartProductDTO;
import com.go2shop.model.product.ProductDTO;
import com.netflix.servo.util.Throwables;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	private ShoppingCartProductRepository shoppingCartProductRepository;
	
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;
	
	@Autowired
	private ShoppingCartProductMapper shoppingCartProductMapper;
	
	@Autowired
	private CatalogueService catalogueService;
	
	public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
			ShoppingCartProductRepository shoppingCartProductRepository, ShoppingCartMapper shoppingCartMapper,
			ShoppingCartProductMapper shoppingCartProductMapper) {
		this.shoppingCartRepository = shoppingCartRepository;
		this.shoppingCartProductRepository = shoppingCartProductRepository;
		this.shoppingCartMapper = shoppingCartMapper;
		this.shoppingCartProductMapper = shoppingCartProductMapper;
	}

	@Override
	public ShoppingCartDTO createShoppingCart(ShoppingCartDTO shoppingCartDTO) {
		if (shoppingCartDTO != null && shoppingCartDTO.getUserId() != null) {
			Optional<ShoppingCart> existingShoppingCart = shoppingCartRepository.findByUserId(shoppingCartDTO.getUserId());
			if (!existingShoppingCart.isPresent()) {
				ShoppingCart shoppingCart = shoppingCartRepository.save(shoppingCartMapper.toEntity(shoppingCartDTO));
				return shoppingCartMapper.toDto(shoppingCart);
			} else {
				return null;
			}
		}
		return null;
	}
	
	@Override
	public void deleteAllProduct(Long shoppingCartID) {
		shoppingCartProductRepository.deleteByShoppingCartId(shoppingCartID);
	}
	
	@Override
	public void deleteShoppingCartProductByProductId(Long productID) {
		shoppingCartProductRepository.deleteByProductId(productID);
	}
	
	@Override
	public void deleteShoppingCartProductByCartIdAndProductId(Long cartId, Long productID) {
		shoppingCartProductRepository.deleteByShoppingCartIdAndProductId(cartId, productID);
	}

	@Override
	public void deleteShoppingCartProductById(Long shoppingCartProductId) {
		shoppingCartProductRepository.deleteById(shoppingCartProductId);
	}
	
	@Override
	public Optional<ShoppingCartProductDTO> getShoppingCartProduct(Long shoppingCartProductID) throws BusinessException {
		if (shoppingCartProductID != null) {
			Optional<ShoppingCartProduct> result =  shoppingCartProductRepository.findById(shoppingCartProductID);
			if (result.isPresent()) {
				ShoppingCartProductDTO dto = shoppingCartProductMapper.toDto(result.get());
				dto.setProduct(retrieveProduct(result.get().getProductId()));
				return Optional.of(dto);
			}
			return Optional.empty();
		}
		return Optional.empty();
	}
	
	@Override
	public List<ShoppingCartProductDTO> getAllShoppingCartProduct(Long shoppingCartID) throws BusinessException {
		if (shoppingCartID != null) {
			List<ShoppingCartProduct> result = shoppingCartProductRepository.findByShoppingCartId(shoppingCartID);
			return result.stream().map(cartItem -> {
				try {
					ShoppingCartProductDTO dto = shoppingCartProductMapper.toDto(cartItem);
					dto.setProduct(retrieveProduct(cartItem.getProductId()));
					return dto;
				} catch (BusinessException e) {
					throw Throwables.propagate(e);
				}
			}).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
	
	private ProductDTO retrieveProduct(Long productId) throws BusinessException {
		if(productId != null) {
			ResponseEntity<ProductDTO> response = catalogueService.getProductById(productId);
			if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
				throw new BusinessException(EmBusinessError.SERVICE_NOT_AVAILABLE);
			} else if(!response.hasBody() || (response.hasBody() && response.getBody() == null)) {
				throw new BusinessException(EmBusinessError.PRODUCT_NOT_EXIST);
			}
			if(response.hasBody() && response.getBody() != null) {
				return response.getBody();
			}
		}
		throw new BusinessException(EmBusinessError.PRODUCT_NOT_EXIST);
	}
	
	@Override
	public ShoppingCartProductDTO createShoppingCartProduct(ShoppingCartProductDTO shoppingCartProductDTO) {
		if (shoppingCartProductDTO != null && shoppingCartProductDTO.getProduct().getId() != null) {
			Optional<ShoppingCartProduct> existingShoppingCartProduct = 
					shoppingCartProductRepository.findByShoppingCartIdAndProductId(
							shoppingCartProductDTO.getShoppingCartId(), shoppingCartProductDTO.getProduct().getId());
			if (existingShoppingCartProduct.isPresent()) {
				return updateQuantity(shoppingCartProductDTO.getShoppingCartId(), shoppingCartProductDTO.getProduct().getId(), 
						shoppingCartProductDTO.getQuantity());
			} else {
				ShoppingCartProduct shoppingCartProduct = shoppingCartProductRepository
						.saveAndFlush(shoppingCartProductMapper.toEntity(shoppingCartProductDTO));
				return shoppingCartProductMapper.toDto(shoppingCartProduct);
			}
		}
		return null;
	}
	
	@Override
	public ShoppingCartDTO getShoppingCartByUserId(Long userId) {
		if (userId != null) {
			Optional<ShoppingCart> result = shoppingCartRepository.findByUserId(userId);
			if (result.isPresent()) {
				return shoppingCartMapper.toDto(result.get());
			}
		}
		return null;
	}
	
	@Override
	public ShoppingCartProductDTO updateQuantity(Long cartId, Long productId, int productQuantity) {
		Optional<ShoppingCartProduct> retrievedCartProduct = shoppingCartProductRepository.findByShoppingCartIdAndProductId(cartId, productId);
		if (retrievedCartProduct.isPresent()) {
			if (productQuantity == 0) {
				deleteShoppingCartProductByCartIdAndProductId(cartId, productId);
				return null;
			} else {
				ShoppingCartProduct shoppingCartProduct = retrievedCartProduct.get();
				shoppingCartProduct.setQuantity(productQuantity);
				return shoppingCartProductMapper.toDto(shoppingCartProductRepository.save(shoppingCartProduct));
			}
		}
		return null;
	}
	
	@Override
	public Long countCartSizeByDistinctProductIds(Long userId) {
		ShoppingCartDTO cartDTO = getShoppingCartByUserId(userId);
		if(cartDTO != null) {
			return shoppingCartProductRepository.countSizeByDistinctProductIds(cartDTO.getId());
		}
		return null;
	}
}
