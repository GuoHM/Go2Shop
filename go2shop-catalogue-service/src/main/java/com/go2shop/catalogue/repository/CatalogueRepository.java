package com.go2shop.catalogue.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.go2shop.catalogue.entity.Product;
import com.go2shop.catalogue.entity.RecommendedProduct;


@Repository
public interface CatalogueRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	
	@Query("SELECT new com.go2shop.catalogue.entity.RecommendedProduct(p, SUM(pr.rating) / COUNT(*) as total) from "
			+ "Product p join ProductReview pr on p.id = pr.product.id "
			+ "where (select SUM(pr1.rating) from "
			+ "ProductReview pr1 where pr1.product.id = p.id) > 0"
			+ "GROUP BY p.id "
			+ "ORDER BY total desc ")
	Page<RecommendedProduct> getRecommendationsByRatings(Pageable pageable);
}
