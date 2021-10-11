package com.go2shop.order.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.go2shop.model.order.OrderDetailDTO;
import com.go2shop.order.entity.OrderDetail;

@Mapper(componentModel = "spring", uses = {})
public interface OrderDetailMapper {

	@Mapping(target = "order", ignore = true)
	public OrderDetailDTO toDto(OrderDetail order);
	
	@Mapping(target = "order", ignore = true)
	public OrderDetail toEntity(OrderDetailDTO orderDTO);
	
	public List<OrderDetailDTO> toDto(List<OrderDetail> order);
	
	public List<OrderDetail> toEntity(List<OrderDetailDTO> orderDTO);
}
