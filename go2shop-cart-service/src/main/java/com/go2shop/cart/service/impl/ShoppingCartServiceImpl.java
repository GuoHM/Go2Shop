package com.go2shop.cart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.go2shop.cart.entity.ShoppingCart;
import com.go2shop.cart.entity.ShoppingCartProduct;
import com.go2shop.cart.repository.ShoppingCartProductRepository;
import com.go2shop.cart.repository.ShoppingCartRepository;
import com.go2shop.cart.service.ShoppingCartService;
import com.go2shop.cart.service.mapper.ShoppingCartMapper;
import com.go2shop.cart.service.mapper.ShoppingCartProductMapper;
import com.go2shop.model.cart.ShoppingCartDTO;
import com.go2shop.model.cart.ShoppingCartProductDTO;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	private ShoppingCartRepository shoppingCartRepository;
	
	private ShoppingCartProductRepository shoppingCartProductRepository;
	
	private ShoppingCartMapper shoppingCartMapper;
	
	private ShoppingCartProductMapper shoppingCartProductMapper;
	
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
	public ShoppingCartProductDTO createShoppingCartProduct(ShoppingCartProductDTO shoppingCartProductDTO) {
		if (shoppingCartProductDTO != null && shoppingCartProductDTO.getProductId() != null) {
			Optional<ShoppingCartProduct> existingShoppingCartProduct = 
					shoppingCartProductRepository.findByProductIdAndShoppingCartId(
							shoppingCartProductDTO.getProductId(), shoppingCartProductDTO.getShoppingCartId());
			if (existingShoppingCartProduct.isPresent()) {
				return updateQuantity(shoppingCartProductDTO.getProductId(), 
						shoppingCartProductDTO.getQuantity(), shoppingCartProductDTO.getShoppingCartId());
			} else {
				ShoppingCartProduct shoppingCartProduct = shoppingCartProductRepository
						.saveAndFlush(shoppingCartProductMapper.toEntity(shoppingCartProductDTO));
				return shoppingCartProductMapper.toDto(shoppingCartProduct);
			}
		}
		return null;
	}

	@Override
	public void deleteShoppingCartProduct(Long productID) {
		shoppingCartProductRepository.deleteByProductId(productID);
	}
	
	@Override
	public void deleteAllProduct(Long shoppingCartID) {
		shoppingCartProductRepository.deleteByShoppingCartId(shoppingCartID);
	}
	
	@Override
	public ShoppingCartProductDTO updateQuantity(Long productID, int productQuantity, Long shoppingCartID) {
		Optional<ShoppingCartProduct> retrievedCartProduct = shoppingCartProductRepository.findByProductIdAndShoppingCartId
				(productID, shoppingCartID);
		if (retrievedCartProduct.isPresent()) {
			if (productQuantity == 0) {
				deleteShoppingCartProduct(productID);
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
	public Optional<ShoppingCartDTO> getShoppingCartByUserId(Long userID) {
		if (userID != null) {
			Optional<ShoppingCart> result = shoppingCartRepository.findByUserId(userID);
			if (result.isPresent()) {
				return Optional.of(shoppingCartMapper.toDto(result.get())); 
			}
			return Optional.empty();
		}
		return Optional.empty();
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
	public List<ShoppingCartProductDTO> getAllShoppingCartProduct(Long shoppingCartID) {
		if (shoppingCartID != null) {
			List<ShoppingCartProduct> result = shoppingCartProductRepository.findByShoppingCartId(shoppingCartID);
			return shoppingCartProductMapper.toDto(result);
		}
		return new ArrayList<>();
	}

}
