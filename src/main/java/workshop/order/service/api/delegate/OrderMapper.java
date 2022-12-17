package workshop.order.service.api.delegate;

import java.util.List;

import org.mapstruct.Mapper;

import workshop.order.service.dto.OrderDto;
import workshop.order.service.model.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	public OrderDto toOrderDto(Order order);

	public List<OrderDto> toOrdersDto(List<Order> order);

}
