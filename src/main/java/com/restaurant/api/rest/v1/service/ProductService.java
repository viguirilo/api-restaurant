package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.Product;
import com.restaurant.api.rest.v1.entity.Restaurant;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.ProductRepository;
import com.restaurant.api.rest.v1.repository.RestaurantRepository;
import com.restaurant.api.rest.v1.vo.ProductRequestVO;
import com.restaurant.api.rest.v1.vo.ProductResponseVO;
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
public class ProductService {

    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public ProductResponseVO save(ProductRequestVO productRequestVO) {
        Restaurant restaurant = restaurantRepository.findById(productRequestVO.getRestaurantId()).orElseThrow(() -> {
            log.error("SAVE: RESTAURANT ID = {} NOT FOUND", productRequestVO.getRestaurantId());
            return new EntityNotFoundException("The restaurant informed wasn't found");
        });
        Product product = productRepository.save(new Product(productRequestVO, restaurant));
        log.info("SAVE: {} CREATED SUCCESSFULLY", product);
        return new ProductResponseVO(product);
    }

    public Page<ProductResponseVO> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        if (!products.isEmpty()) {
            log.info("FIND ALL: FOUND {} PRODUCTS", products.getTotalElements());
            return new PageImpl<>(products.stream().map(ProductResponseVO::new).toList(), pageable, products.getTotalElements());
        } else {
            log.error("FIND ALL: PRODUCTS NOT FOUND");
            throw new EntityNotFoundException("Products not found");
        }
    }

    public ProductResponseVO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.error("FIND BY ID: PRODUCT ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The product requested was not found");
        });
        log.info("FIND BY ID: {} FOUND SUCCESSFULLY", product);
        return new ProductResponseVO(product);
    }

    @Transactional
    public ProductResponseVO update(Long id, ProductRequestVO productRequestVO) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.error("UPDATE: PRODUCT ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The product requested was not found");
        });
        Restaurant restaurant = restaurantRepository.findById(productRequestVO.getRestaurantId()).orElseThrow(() -> {
            log.error("UPDATE: RESTAURANT {} NOT FOUND", productRequestVO.getRestaurantId());
            return new EntityNotFoundException("The restaurant informed was not found");
        });
        BeanUtils.copyProperties(productRequestVO, product, "id", "restaurant");
        product.setRestaurant(restaurant);
        product = productRepository.save(product);
        log.info("UPDATE: {} UPDATED SUCCESSFULLY", product);
        return new ProductResponseVO(product);
    }

    @Transactional
    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
            productRepository.flush();
            log.info("DELETE: PRODUCT ID = {} DELETED SUCCESSFULLY", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("DELETE: THE REQUESTED PRODUCT ID = {} IS BEING USED BY ONE OR MORE ENTITIES", id);
            throw new EntityInUseException("PRODUCT = " + id);
        }
    }

}
