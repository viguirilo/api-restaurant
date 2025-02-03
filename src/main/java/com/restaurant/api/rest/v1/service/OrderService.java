package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.*;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.*;
import com.restaurant.api.rest.v1.vo.OrderRequestVO;
import com.restaurant.api.rest.v1.vo.OrderResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final CityRepository cityRepository;

    // TODO(Rever aula 4.30 para adequar como ele implementa esse método)
    @Transactional
    public OrderResponseVO save(OrderRequestVO orderRequestVO) {
        Restaurant restaurant = restaurantRepository.findById(orderRequestVO.getRestaurantId()).orElseThrow(() -> {
            log.error("SAVE: RESTAURANT ID = {} NOT FOUND", orderRequestVO.getRestaurantId());
            return new EntityNotFoundException("The restaurant informed was not found");
        });
        User customer = userRepository.findById(orderRequestVO.getCustomerId()).orElseThrow(() -> {
            log.error("SAVE: CUSTOMER ID = {} NOT FOUND", orderRequestVO.getRestaurantId());
            return new EntityNotFoundException("The customer informed was not found");
        });
        PaymentMethod paymentMethod = paymentMethodRepository.findById(orderRequestVO.getCustomerId()).orElseThrow(() -> {
            log.error("SAVE: PAYMENT METHOD ID = {} NOT FOUND", orderRequestVO.getPaymentMethodId());
            return new EntityNotFoundException("The payment method informed was not found");
        });
        City city = cityRepository.findById(orderRequestVO.getAddressCityId()).orElseThrow(() -> {
            log.error("SAVE: CITY ID = {} NOT FOUND", orderRequestVO.getAddressCityId());
            return new EntityNotFoundException("The city informed was not found");
        });
        Order order = orderRepository.save(new Order(orderRequestVO, restaurant, customer, paymentMethod, city));
        log.info("SAVE: {} CREATED SUCCESSFULLY", order);
        return new OrderResponseVO(order);
    }

    public Page<OrderResponseVO> findAll(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        if (!orders.isEmpty()) {
            log.info("FIND ALL: FOUND {} ORDERS", orders.getTotalElements());
            return new PageImpl<>(orders.stream().map(OrderResponseVO::new).toList(), pageable, orders.getTotalElements());
        } else {
            log.error("FIND ALL: ORDER NOT FOUND");
            throw new EntityNotFoundException("Orders not found");
        }
    }

    public OrderResponseVO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            log.error("FIND BY ID: ORDER ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The order requested was not found");
        });
        log.info("FIND BY ID: {} FOUND SUCCESSFULLY", order);
        return new OrderResponseVO(order);
    }

    @Transactional
    public OrderResponseVO update(Long id, OrderRequestVO orderRequestVO) {
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            log.error("UPDATE: ORDERED ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The order requested was not found");
        });
        Restaurant restaurant = restaurantRepository.findById(orderRequestVO.getRestaurantId()).orElseThrow(() -> {
            log.error("UPDATE: RESTAURANT ID = {} NOT FOUND", orderRequestVO.getRestaurantId());
            return new EntityNotFoundException("The restaurant informed was not found");
        });
        User customer = userRepository.findById(orderRequestVO.getCustomerId()).orElseThrow(() -> {
            log.error("UPDATE: CUSTOMER ID = {} NOT FOUND", orderRequestVO.getRestaurantId());
            return new EntityNotFoundException("The customer informed was not found");
        });
        PaymentMethod paymentMethod = paymentMethodRepository.findById(orderRequestVO.getCustomerId()).orElseThrow(() -> {
            log.error("UPDATE: PAYMENT METHOD ID = {} NOT FOUND", orderRequestVO.getPaymentMethodId());
            return new EntityNotFoundException("The payment method informed was not found");
        });
        City city = cityRepository.findById(orderRequestVO.getAddressCityId()).orElseThrow(() -> {
            log.error("UPDATE: CITY ID = {} NOT FOUND", orderRequestVO.getAddressCityId());
            return new EntityNotFoundException("The city informed was not found");
        });
        BeanUtils.copyProperties(
                orderRequestVO,
                order,
                "id", "creationDate", "restaurant", "customer", "paymentMethod", "address"
        );
        order.setRestaurant(restaurant);
        order.setCustomer(customer);
        order.setPaymentMethod(paymentMethod);
        Address address = new Address(orderRequestVO, city);
        order.setAddress(address);
        order = orderRepository.save(order);
        log.info("UPDATE: {} UPDATED SUCCESSFULLY", order);
        return new OrderResponseVO(order);
    }

    @Transactional
    public void delete(Long id) {
        try {
            orderRepository.deleteById(id);
            orderRepository.flush();
            log.info("DELETE: ORDER ID = {} DELETED SUCCESSFULLY", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("DELETE: THE REQUESTED ORDER ID = {} IS BEING USED BY ONE OR MORE ENTITIES", id);
            throw new EntityInUseException("ORDER = " + id);
        }
    }

}
