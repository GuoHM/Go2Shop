package com.go2shop.cart.repository;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.go2shop.cart.CartApplication;
import com.go2shop.cart.entity.ShoppingCartProduct;

@SpringBootTest(classes = CartApplication.class)
class ShoppingCartProductRepositoryTest {

	@Autowired
	private ShoppingCartProductRepository shoppingCartProductRepository;
	
	@Test
	void test() {
		List<ShoppingCartProduct> result = shoppingCartProductRepository.findAll();
		assertNotEquals(0, result.size());
	}
}
