package com.go2shop.catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.go2shop.catalogue.entity.Product;


/** 
 * 
 * <Write a short description on the purpose of the class> 
 * 
 * @author P1326154 
 * Created Date Aug 10, 2021 2:26:35 PM 
 * 
*/
@Repository
public interface CatalogueRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}
