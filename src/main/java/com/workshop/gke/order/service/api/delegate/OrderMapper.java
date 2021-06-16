package com.workshop.gke.order.service.api.delegate;

import java.util.List;

import org.mapstruct.Mapper;

import com.workshop.gke.order.service.dto.OrderDto;
import com.workshop.gke.order.service.model.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	public OrderDto toOrderDto(Order order);

	public List<OrderDto> toOrdersDto(List<Order> order);

}
