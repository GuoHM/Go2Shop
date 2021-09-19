package com.go2shop.cart.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.go2shop.cart.CartApplication;
import com.go2shop.cart.entity.ShoppingCart;
import com.go2shop.model.cart.ShoppingCartDTO;

@SpringBootTest(classes = CartApplication.class)
class ShoppingCartMapperTest {
	
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;

	private ShoppingCartDTO constructShoppingCartDTO() {
		ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
		shoppingCartDTO.setId(111l);
		shoppingCartDTO.setUserId(111l);
		shoppingCartDTO.setPrice(new BigDecimal(111));
		shoppingCartDTO.setDiscount(new BigDecimal(111));
		return shoppingCartDTO;
	}
	
	private ShoppingCart constructShoppingCart() {
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setId(222l);
		shoppingCart.setUserId(222l);
		shoppingCart.setPrice(new BigDecimal(222));
		shoppingCart.setDiscount(new BigDecimal(222));
		return shoppingCart;
	}
	
	@Test
	void toDtoTest() {
		ShoppingCart shoppingCart = constructShoppingCart();
		ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.toDto(shoppingCart);
		assertEquals(shoppingCart.getId(), shoppingCartDTO.getId());
		assertEquals(shoppingCart.getUserId(), shoppingCartDTO.getUserId());
		assertEquals(shoppingCart.getPrice(), shoppingCartDTO.getPrice());
		assertEquals(shoppingCart.getDiscount(), shoppingCartDTO.getDiscount());
	}
	
	@Test
	void toEntityTest() {
		ShoppingCartDTO shoppingCartDTO = constructShoppingCartDTO();
		ShoppingCart shoppingCart = shoppingCartMapper.toEntity(shoppingCartDTO);
		assertEquals(shoppingCartDTO.getId(), shoppingCart.getId());
		assertEquals(shoppingCartDTO.getUserId(), shoppingCart.getUserId());
		assertEquals(shoppingCartDTO.getPrice(), shoppingCart.getPrice());
		assertEquals(shoppingCartDTO.getDiscount(), shoppingCart.getDiscount());
	}
}
