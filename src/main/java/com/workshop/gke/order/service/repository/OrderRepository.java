package com.workshop.gke.order.service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workshop.gke.order.service.model.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

//	public Optional<Order> findByNumber(Long number);

//	public Optional<NewOrder> create(CreateOrder order);

}