package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.Order;
import com.restaurant.api.rest.v1.entity.OrderedItem;
import com.restaurant.api.rest.v1.entity.Product;
import com.restaurant.api.rest.v1.exception.BadRequestException;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.OrderRepository;
import com.restaurant.api.rest.v1.repository.OrderedItemRepository;
import com.restaurant.api.rest.v1.repository.ProductRepository;
import com.restaurant.api.rest.v1.vo.OrderedItemRequestVO;
import com.restaurant.api.rest.v1.vo.OrderedItemResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class OrderedItemService {

    private final OrderedItemRepository orderedItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final Logger logger = Logger.getLogger(OrderedItemService.class.getName());

    // TODO(Rever aula 4.30 para adequar como ele implementa esse método)
    @Transactional
    public OrderedItemResponseVO save(OrderedItemRequestVO orderedItemRequestVO) {
        Order order = orderRepository.findById(orderedItemRequestVO.getOrderId()).orElseThrow(() -> {
            logger.warning("ORDER ID = " + orderedItemRequestVO.getOrderId() + " WAS NOT FOUND");
            return new BadRequestException("The order informed was not found");
        });
        Product product = productRepository.findById(orderedItemRequestVO.getProductId()).orElseThrow(() -> {
            logger.warning("PRODUCT ID = " + orderedItemRequestVO.getProductId() + " WAS NOT FOUND");
            return new BadRequestException("The product informed was not found");
        });
        OrderedItem orderedItem = orderedItemRepository.save(new OrderedItem(orderedItemRequestVO, order, product));
        logger.info(orderedItem + " CREATED SUCCESSFULLY");
        return new OrderedItemResponseVO(orderedItem);
    }

    public List<OrderedItemResponseVO> findAll() {
        List<OrderedItem> orderedItems = orderedItemRepository.findAll();
        if (!orderedItems.isEmpty()) {
            logger.info("FOUND " + orderedItems.size() + " ORDERED ITEMS");
            return orderedItems.stream().map(OrderedItemResponseVO::new).toList();
        } else {
            logger.warning("ORDERED ITEMS NOT FOUND");
            throw new EntityNotFoundException("Ordered items not found");
        }
    }

    public OrderedItemResponseVO findById(Long id) {
        OrderedItem orderedItem = orderedItemRepository.findById(id).orElseThrow(() -> {
            logger.warning("ORDERED ITEM ID = " + id + " NOT FOUND");
            return new EntityNotFoundException("The ordered item requested was not found");
        });
        logger.info(orderedItem + " FOUND SUCCESSFULLY");
        return new OrderedItemResponseVO(orderedItem);
    }

    @Transactional
    public OrderedItemResponseVO update(Long id, OrderedItemRequestVO orderedItemRequestVO) {
        OrderedItem orderedItem = orderedItemRepository.findById(id).orElseThrow(() -> {
            logger.warning("ORDERED ITEM ID = " + id + " NOT FOUND");
            return new EntityNotFoundException("The ordered item requested was not found");
        });
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            logger.warning("CAN NOT UPDATE ORDERED ITEM: ORDER " + orderedItemRequestVO.getOrderId() + " NOT FOUND");
            return new BadRequestException("The order requested was not found");
        });
        Product product = productRepository.findById(orderedItemRequestVO.getProductId()).orElseThrow(() -> {
            logger.warning("CAN NOT UPDATE ORDERED ITEM: PRODUCT " + orderedItemRequestVO.getProductId() + " NOT FOUND");
            return new BadRequestException("The product informed was not found");
        });
        BeanUtils.copyProperties(
                orderedItemRequestVO,
                orderedItem,
                "id", "order", "product"
        );
        orderedItem.setOrder(order);
        orderedItem.setProduct(product);
        orderedItem = orderedItemRepository.save(orderedItem);
        logger.info(orderedItem + " UPDATED SUCCESSFULLY");
        return new OrderedItemResponseVO(orderedItem);
    }

    @Transactional
    public void delete(Long id) {
        try {
            orderedItemRepository.delete(Objects.requireNonNull(orderedItemRepository.findById(id).orElse(null)));
            logger.info("ORDERED ITEM ID = " + id + " DELETED SUCCESSFULLY");
        } catch (NullPointerException ex) {
            logger.warning("CAN NOT DELETE: ORDERED ITEM " + id + " NOT FOUND");
            throw new EntityNotFoundException("The ordered item requested was not found");
        } catch (DataIntegrityViolationException ex) {
            logger.warning("THE REQUESTED ENTITY (ORDERED ITEM ID = " + id + ") IS BEING USED BY ONE OR MORE ENTITIES");
            throw new EntityInUseException("ORDERED ITEM = " + id);
        }
    }

}
