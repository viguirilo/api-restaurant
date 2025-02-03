package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.Order;
import com.restaurant.api.rest.v1.entity.OrderedItem;
import com.restaurant.api.rest.v1.entity.Product;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.OrderRepository;
import com.restaurant.api.rest.v1.repository.OrderedItemRepository;
import com.restaurant.api.rest.v1.repository.ProductRepository;
import com.restaurant.api.rest.v1.vo.OrderedItemRequestVO;
import com.restaurant.api.rest.v1.vo.OrderedItemResponseVO;
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
public class OrderedItemService {

    private final OrderedItemRepository orderedItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // TODO(Rever aula 4.30 para adequar como ele implementa esse método)
    @Transactional
    public OrderedItemResponseVO save(OrderedItemRequestVO orderedItemRequestVO) {
        Order order = orderRepository.findById(orderedItemRequestVO.getOrderId()).orElseThrow(() -> {
            log.error("SAVE: ORDER ID = {} NOT FOUND", orderedItemRequestVO.getOrderId());
            return new EntityNotFoundException("The order informed was not found");
        });
        Product product = productRepository.findById(orderedItemRequestVO.getProductId()).orElseThrow(() -> {
            log.error("SAVE: PRODUCT ID = {} NOT FOUND", orderedItemRequestVO.getProductId());
            return new EntityNotFoundException("The product informed was not found");
        });
        OrderedItem orderedItem = orderedItemRepository.save(new OrderedItem(orderedItemRequestVO, order, product));
        log.info("SAVE: {} CREATED SUCCESSFULLY", orderedItem);
        return new OrderedItemResponseVO(orderedItem);
    }

    public Page<OrderedItemResponseVO> findAll(Pageable pageable) {
        Page<OrderedItem> orderedItems = orderedItemRepository.findAll(pageable);
        if (!orderedItems.isEmpty()) {
            log.info("FIND ALL: FOUND {} ORDERED ITEMS", orderedItems.getTotalElements());
            return new PageImpl<>(orderedItems.stream().map(OrderedItemResponseVO::new).toList(), pageable, orderedItems.getTotalElements());
        } else {
            log.error("FIND ALL: ORDERED ITEMS NOT FOUND");
            throw new EntityNotFoundException("Ordered items not found");
        }
    }

    public OrderedItemResponseVO findById(Long id) {
        OrderedItem orderedItem = orderedItemRepository.findById(id).orElseThrow(() -> {
            log.error("FIND BY ID: ORDERED ITEM ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The ordered item requested was not found");
        });
        log.info("FIND BY ID: {} FOUND SUCCESSFULLY", orderedItem);
        return new OrderedItemResponseVO(orderedItem);
    }

    @Transactional
    public OrderedItemResponseVO update(Long id, OrderedItemRequestVO orderedItemRequestVO) {
        OrderedItem orderedItem = orderedItemRepository.findById(id).orElseThrow(() -> {
            log.error("UPDATE: ORDERED ITEM ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The ordered item requested was not found");
        });
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            log.error("UPDATE: ORDER ID = {} NOT FOUND", orderedItemRequestVO.getOrderId());
            return new EntityNotFoundException("The order requested was not found");
        });
        Product product = productRepository.findById(orderedItemRequestVO.getProductId()).orElseThrow(() -> {
            log.error("UPDATE: PRODUCT ID = {} NOT FOUND", orderedItemRequestVO.getProductId());
            return new EntityNotFoundException("The product informed was not found");
        });
        BeanUtils.copyProperties(
                orderedItemRequestVO,
                orderedItem,
                "id", "order", "product"
        );
        orderedItem.setOrder(order);
        orderedItem.setProduct(product);
        orderedItem = orderedItemRepository.save(orderedItem);
        log.info("UPDATE: {} UPDATED SUCCESSFULLY", orderedItem);
        return new OrderedItemResponseVO(orderedItem);
    }

    @Transactional
    public void delete(Long id) {
        try {
            orderedItemRepository.deleteById(id);
            orderedItemRepository.flush();
            log.info("DELETE: ORDERED ITEM ID = {} DELETED SUCCESSFULLY", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("DELETE: THE REQUESTED ORDERED ITEM ID = {} IS BEING USED BY ONE OR MORE ENTITIES", id);
            throw new EntityInUseException("ORDERED ITEM = " + id);
        }
    }

}
