package com.go2shop.order.service.mapper;

import org.mapstruct.Mapper;

import com.go2shop.model.order.OrderDTO;
import com.go2shop.order.entity.Order;

@Mapper(componentModel = "spring", uses = { OrderDetailMapper.class })
public interface OrderMapper {

	public OrderDTO toDto(Order order);
	
	public Order toEntity(OrderDTO orderDTO);
}
