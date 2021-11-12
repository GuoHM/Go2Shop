package com.go2shop;

import java.math.BigDecimal;
import java.util.List;

public class ProductDTO {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private int stock;

    private Long userId;

    List<ProductImageDTO> productImages;

    List<ProductReviewDTO> productReviews;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ProductImageDTO> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImageDTO> productImages) {
        this.productImages = productImages;
    }

    public List<ProductReviewDTO> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(List<ProductReviewDTO> productReviews) {
        this.productReviews = productReviews;
    }

}
