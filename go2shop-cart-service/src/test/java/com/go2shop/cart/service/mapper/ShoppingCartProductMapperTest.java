package com.go2shop.cart.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.go2shop.cart.CartApplication;
import com.go2shop.cart.entity.ShoppingCartProduct;
import com.go2shop.model.cart.ShoppingCartProductDTO;
import com.go2shop.model.product.ProductDTO;

@SpringBootTest(classes = CartApplication.class)
class ShoppingCartProductMapperTest {
	
	@Autowired
	private ShoppingCartProductMapper shoppingCartProductMapper;
	
	private ShoppingCartProductDTO constructShoppingCartProductDTO() {
		ShoppingCartProductDTO shoppingCartProductDTO = new ShoppingCartProductDTO();
		shoppingCartProductDTO.setId(111l);
		shoppingCartProductDTO.setProduct(this.constructProduct());
		shoppingCartProductDTO.setQuantity(111);
		shoppingCartProductDTO.setShoppingCartId(111l);
		return shoppingCartProductDTO;
	}
	
	private ShoppingCartProduct constructShoppingCartProduct() {
		ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
		shoppingCartProduct.setId(111l);
		shoppingCartProduct.setProductId(111l);
		shoppingCartProduct.setQuantity(111);
		shoppingCartProduct.setShoppingCartId(111l);
		return shoppingCartProduct;
	}
	
	private ProductDTO constructProduct() {
		ProductDTO product = new ProductDTO();
		product.setId(111l);
		return product;
	}
	
	@Test
	void toDtoTest() {
		ShoppingCartProduct shoppingCartProduct = constructShoppingCartProduct();
		ShoppingCartProductDTO shoppingCartProductDTO = shoppingCartProductMapper.toDto(shoppingCartProduct);
		assertEquals(shoppingCartProduct.getId(), shoppingCartProductDTO.getId());
		assertEquals(shoppingCartProduct.getQuantity(), shoppingCartProductDTO.getQuantity());
		assertEquals(shoppingCartProduct.getShoppingCartId(), shoppingCartProductDTO.getShoppingCartId());
	}
	
	@Test
	void toEntityTest() {
		ShoppingCartProductDTO shoppingCartProductDTO = constructShoppingCartProductDTO();
		ShoppingCartProduct shoppingCartProduct = shoppingCartProductMapper.toEntity(shoppingCartProductDTO);
		assertEquals(shoppingCartProductDTO.getId(), shoppingCartProduct.getId());
		assertEquals(shoppingCartProductDTO.getProduct().getId(), shoppingCartProduct.getProductId());
		assertEquals(shoppingCartProductDTO.getQuantity(), shoppingCartProduct.getQuantity());
		assertEquals(shoppingCartProductDTO.getShoppingCartId(), shoppingCartProduct.getShoppingCartId());
	}

}
