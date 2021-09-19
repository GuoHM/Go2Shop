package com.go2shop.cart.repository;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.go2shop.cart.CartApplication;
import com.go2shop.cart.entity.ShoppingCart;
import com.go2shop.cart.repository.ShoppingCartRepository;

@SpringBootTest(classes = CartApplication.class)
class ShoppingCartRepositoryTest {
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	private ShoppingCart shoppingCart;
	
	@BeforeEach
	void setup() {
		shoppingCart = new ShoppingCart();
		shoppingCart.setDiscount(new BigDecimal(2));
		shoppingCart.setPrice(new BigDecimal(2));
		shoppingCart.setUserId(2l);
		shoppingCart = shoppingCartRepository.save(shoppingCart);
	}
	
	
	
	@AfterEach
	void tearDown() {
		shoppingCartRepository.delete(shoppingCart);
	}
	
	@Test
	void test() {
		List<ShoppingCart> result = shoppingCartRepository.findAll();
		result.forEach(x -> {
			System.out.println("****** " + x.toString());
		});
		assertNotEquals(0, result.size());
	}

}
