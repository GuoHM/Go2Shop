package com.go2shop.cart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	public Optional<ShoppingCartProductDTO> getShoppingCartProduct(Long shoppingCartProductID) {
		if (shoppingCartProductID != null) {
			Optional<ShoppingCartProduct> result =  shoppingCartProductRepository.findById(shoppingCartProductID);
			if (result.isPresent()) {
				return Optional.of(shoppingCartProductMapper.toDto(result.get()));
			}
			return Optional.empty();
		}
		return Optional.empty();
	}
	
	@Override
	public List<ShoppingCartProductDTO> getAllShoppingCartProduct(Long shoppingCartID) throws BusinessException {
		if (shoppingCartID != null) {
			List<ShoppingCartProduct> result = shoppingCartProductRepository.findByShoppingCartId(shoppingCartID);
			List<ShoppingCartProductDTO> dtos = shoppingCartProductMapper.toDto(result);
			dtos.stream().forEach(cartItem -> {
				try {
					cartItem.setProduct(retrieveProduct(cartItem.getProductId()));
				} catch (BusinessException e) {
					throw Throwables.propagate(e);
				}
			});
			
			return dtos;
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
		if (shoppingCartProductDTO != null && shoppingCartProductDTO.getProductId() != null) {
			Optional<ShoppingCartProduct> existingShoppingCartProduct = 
					shoppingCartProductRepository.findByShoppingCartIdAndProductId(
							shoppingCartProductDTO.getShoppingCartId(), shoppingCartProductDTO.getProductId());
			if (existingShoppingCartProduct.isPresent()) {
				return updateQuantity(shoppingCartProductDTO.getShoppingCartId(), shoppingCartProductDTO.getProductId(), 
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
