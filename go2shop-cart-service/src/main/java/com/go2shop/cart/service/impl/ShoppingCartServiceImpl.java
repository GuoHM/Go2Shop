package com.go2shop.cart.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.go2shop.model.cart.UserToProductDTO;

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
				ShoppingCart shoppingCart = shoppingCartRepository.saveAndFlush(shoppingCartMapper.toEntity(shoppingCartDTO));
				return shoppingCartMapper.toDto(shoppingCart);
			} else {
				return null;
			}
		}
		return null;
	}

//	@Override
//	public ShoppingCartProductDTO createShoppingCartProduct(ShoppingCartProductDTO shoppingCartProductDTO) {
//		if (shoppingCartProductDTO != null && shoppingCartProductDTO.getProductId() != null) {
//			Optional<ShoppingCartProduct> existingShoppingCartProduct = 
//					shoppingCartProductRepository.findByProductId(shoppingCartProductDTO.getProductId());
//			if (existingShoppingCartProduct.isPresent()) {
//				return updateQuantity(shoppingCartProductDTO.getProductId(), shoppingCartProductDTO.getQuantity());
//			} else {
//				ShoppingCartProduct shoppingCartProduct = shoppingCartProductRepository
//						.save(shoppingCartProductMapper.toEntity(shoppingCartProductDTO));
//				return shoppingCartProductMapper.toDto(shoppingCartProduct);
//			}
//		}
//		return null;
//	}

//	@Override
//	public void deleteShoppingCartProduct(Long productID) {
//		shoppingCartProductRepository.deleteByProductId(productID);
//	}
//	
	@Override
	public void deleteAllProduct(Long shoppingCartID) {
		shoppingCartProductRepository.deleteByShoppingCartId(shoppingCartID);
	}
	
//	@Override
//	public ShoppingCartProductDTO updateQuantity(Long productID, int productQuantity) {
//		Optional<ShoppingCartProduct> retrievedCartProduct = shoppingCartProductRepository.findByProductId(productID);
//		if (retrievedCartProduct.isPresent()) {
//			if (productQuantity == 0) {
//				deleteShoppingCartProduct(productID);
//				return null;
//			} else {
//				ShoppingCartProduct shoppingCartProduct = retrievedCartProduct.get();
//				shoppingCartProduct.setQuantity(productQuantity);
//				return shoppingCartProductMapper.toDto(shoppingCartProductRepository.save(shoppingCartProduct));
//			}
//		}
//		return null;
//	}

	@Override
	public ShoppingCartDTO getShoppingCart(Long shoppingCartID) {
		if (shoppingCartID != null) {
			Optional<ShoppingCart> result = shoppingCartRepository.findById(shoppingCartID);
			if (result.isPresent()) {
				return shoppingCartMapper.toDto(result.get());
			}
			return null;
		}
		return null;
	}

	@Override
	public ShoppingCartProductDTO getShoppingCartProduct(Long shoppingCartProductID) {
		if (shoppingCartProductID != null) {
			Optional<ShoppingCartProduct> result =  shoppingCartProductRepository.findById(shoppingCartProductID);
			if (result.isPresent()) {
				return shoppingCartProductMapper.toDto(result.get());
			}
			return null;
		}
		return null;
	}
	
	@Override
	public ShoppingCartProductDTO createOrUpdateShoppingCartProduct(UserToProductDTO userToProductDTO) {
		if (userToProductDTO != null && userToProductDTO.getProductId() != null
				&& userToProductDTO.getUserId() != null) {
			ShoppingCartDTO userCart = getShoppingCartByUserId(userToProductDTO.getUserId());
			if(userCart != null) {
				Optional<ShoppingCartProduct> existingShoppingCartProduct = 
						shoppingCartProductRepository.findByShoppingCartIdAndProductId(userCart.getId(), userToProductDTO.getProductId());
				if (existingShoppingCartProduct.isPresent()) {
					return updateQuantity(userCart.getId(), userToProductDTO.getProductId(), 
							existingShoppingCartProduct.get().getQuantity() + userToProductDTO.getQuantity());
				} else {
					ShoppingCartProduct shoppingCartProduct = shoppingCartProductRepository
							.save(shoppingCartProductMapper.toEntityFromUserToProductAndCart(userToProductDTO, userCart));
					return shoppingCartProductMapper.toDto(shoppingCartProduct);
				}
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
			return null;
		}
		return null;
	}
	
	@Override
	public ShoppingCartProductDTO updateQuantity(Long cartId, Long productId, int productQuantity) {
		Optional<ShoppingCartProduct> retrievedCartProduct = shoppingCartProductRepository.findByShoppingCartIdAndProductId(cartId, productId);
		if (retrievedCartProduct.isPresent()) {
			if (productQuantity == 0) {
				deleteShoppingCartProduct(cartId, productId);
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
	public void deleteShoppingCartProduct(Long cartId, Long productID) {
		shoppingCartProductRepository.deleteByShoppingCartIdAndProductId(cartId, productID);
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
