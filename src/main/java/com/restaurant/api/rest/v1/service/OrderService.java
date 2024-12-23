package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.*;
import com.restaurant.api.rest.v1.exception.BadRequestException;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.*;
import com.restaurant.api.rest.v1.vo.OrderRequestVO;
import com.restaurant.api.rest.v1.vo.OrderResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final CityRepository cityRepository;
    private final Logger logger = Logger.getLogger(OrderService.class.getName());

    // TODO(Rever aula 4.30 para adequar como ele implementa esse método)
    public OrderResponseVO save(OrderRequestVO orderRequestVO) {
        Restaurant restaurant = restaurantRepository.findById(orderRequestVO.getRestaurantId()).orElseThrow(() -> {
            logger.warning("RESTAURANT ID = " + orderRequestVO.getRestaurantId() + " WAS NOT FOUND");
            return new BadRequestException("The restaurant informed was not found");
        });
        User customer = userRepository.findById(orderRequestVO.getCustomerId()).orElseThrow(() -> {
            logger.warning("CUSTOMER ID = " + orderRequestVO.getRestaurantId() + " WAS NOT FOUND");
            return new BadRequestException("The customer informed was not found");
        });
        PaymentMethod paymentMethod = paymentMethodRepository.findById(orderRequestVO.getCustomerId()).orElseThrow(() -> {
            logger.warning("PAYMENT METHOD ID = " + orderRequestVO.getPaymentMethodId() + " WAS NOT FOUND");
            return new BadRequestException("The payment method informed was not found");
        });
        City city = cityRepository.findById(orderRequestVO.getAddressCityId()).orElseThrow(() -> {
            logger.warning("CITY ID = " + orderRequestVO.getAddressCityId() + " WAS NOT FOUND");
            return new BadRequestException("The city informed was not found");
        });
        Order order = orderRepository.save(new Order(orderRequestVO, restaurant, customer, paymentMethod, city));
        logger.info(order + " CREATED SUCCESSFULLY");
        return new OrderResponseVO(order);
    }

    public List<OrderResponseVO> findAll() {
        List<Order> orders = orderRepository.findAll();
        if (!orders.isEmpty()) {
            logger.info("FOUND " + orders.size() + " ORDERS");
            return orders.stream().map(OrderResponseVO::new).toList();
        } else {
            logger.warning("ORDER NOT FOUND");
            throw new EntityNotFoundException("Orders not found");
        }
    }

    public OrderResponseVO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            logger.warning("ORDER ID = " + id + " NOT FOUND");
            return new EntityNotFoundException("The order requested was not found");
        });
        logger.info(order + " FOUND SUCCESSFULLY");
        return new OrderResponseVO(order);
    }

    public OrderResponseVO update(Long id, OrderRequestVO orderRequestVO) {
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            logger.warning("ORDERED ID = " + id + " NOT FOUND");
            return new EntityNotFoundException("The ordered requested was not found");
        });
        Restaurant restaurant = restaurantRepository.findById(orderRequestVO.getRestaurantId()).orElseThrow(() -> {
            logger.warning("RESTAURANT ID = " + orderRequestVO.getRestaurantId() + " WAS NOT FOUND");
            return new BadRequestException("The restaurant informed was not found");
        });
        User customer = userRepository.findById(orderRequestVO.getCustomerId()).orElseThrow(() -> {
            logger.warning("CUSTOMER ID = " + orderRequestVO.getRestaurantId() + " WAS NOT FOUND");
            return new BadRequestException("The customer informed was not found");
        });
        PaymentMethod paymentMethod = paymentMethodRepository.findById(orderRequestVO.getCustomerId()).orElseThrow(() -> {
            logger.warning("PAYMENT METHOD ID = " + orderRequestVO.getPaymentMethodId() + " WAS NOT FOUND");
            return new BadRequestException("The payment method informed was not found");
        });
        City city = cityRepository.findById(orderRequestVO.getAddressCityId()).orElseThrow(() -> {
            logger.warning("CITY ID = " + orderRequestVO.getAddressCityId() + " WAS NOT FOUND");
            return new BadRequestException("The city informed was not found");
        });
        BeanUtils.copyProperties(
                orderRequestVO,
                order,
                "id", "restaurant", "customer", "paymentMethod", "address"
        );
        order.setRestaurant(restaurant);
        order.setCustomer(customer);
        order.setPaymentMethod(paymentMethod);
        Address address = new Address(orderRequestVO, city);
        order.setAddress(address);
        order = orderRepository.save(order);
        logger.info(order + " UPDATED SUCCESSFULLY");
        return new OrderResponseVO(order);
    }

    public void delete(Long id) {
        try {
            orderRepository.delete(Objects.requireNonNull(orderRepository.findById(id).orElse(null)));
            logger.info("ORDER ID = " + id + " DELETED SUCCESSFULLY");
        } catch (NullPointerException ex) {
            logger.warning("CAN NOT DELETE: ORDER " + id + " NOT FOUND");
            throw new EntityNotFoundException("The order requested was not found");
        } catch (DataIntegrityViolationException ex) {
            logger.warning("THE REQUESTED ENTITY (ORDER ID = " + id + ") IS BEING USED BY ONE OR MORE ENTITIES");
            throw new EntityInUseException("ORDER = " + id);
        }
    }

}
