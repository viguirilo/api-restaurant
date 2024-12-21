package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.Product;
import com.restaurant.api.rest.v1.entity.Restaurant;
import com.restaurant.api.rest.v1.exception.BadRequestException;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.ProductRepository;
import com.restaurant.api.rest.v1.repository.RestaurantRepository;
import com.restaurant.api.rest.v1.vo.ProductRequestVO;
import com.restaurant.api.rest.v1.vo.ProductResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;
    private final Logger logger = Logger.getLogger(ProductService.class.getName());

    public ProductResponseVO save(ProductRequestVO productRequestVO) {
        Restaurant restaurant = restaurantRepository.findById(productRequestVO.getRestaurantId()).orElseThrow(() -> {
            logger.warning("RESTAURANT ID = " + productRequestVO.getRestaurantId() + " WAS NOT FOUND");
            return new BadRequestException("The restaurant informed wasn't found");
        });
        Product product = productRepository.save(new Product(productRequestVO, restaurant));
        logger.info(product + " CREATED SUCCESSFULLY");
        return new ProductResponseVO(product);
    }

    public List<ProductResponseVO> findAll() {
        List<Product> products = productRepository.findAll();
        if (!products.isEmpty()) {
            logger.info("FOUND " + products.size() + " PRODUCTS");
            return products.stream().map(ProductResponseVO::new).toList();
        } else {
            logger.warning("PRODUCTS NOT FOUND");
            throw new EntityNotFoundException("Products not found");
        }
    }

    public ProductResponseVO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            logger.warning("PRODUCT ID = " + id + " NOT FOUND");
            return new EntityNotFoundException("The product requested was not found");
        });
        logger.info(product + " FOUND SUCCESSFULLY");
        return new ProductResponseVO(product);
    }

    public ProductResponseVO update(Long id, ProductRequestVO productRequestVO) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            logger.warning("CAN NOT UPDATE: PRODUCT " + id + " NOT FOUND");
            return new EntityNotFoundException("The product requested was not found");
        });
        Restaurant restaurant = restaurantRepository.findById(productRequestVO.getRestaurantId()).orElseThrow(() -> {
            logger.warning("CAN NOT UPDATE: RESTAURANT " + productRequestVO.getRestaurantId() + " NOT FOUND");
            return new BadRequestException("The restaurant informed was not found");
        });
        BeanUtils.copyProperties(productRequestVO, product, "id", "restaurant");
        product.setRestaurant(restaurant);
        product = productRepository.save(product);
        logger.info(product + " UPDATED SUCCESSFULLY");
        return new ProductResponseVO(product);
    }

    public void delete(Long id) {
        try {
            productRepository.delete(Objects.requireNonNull(productRepository.findById(id).orElse(null)));
            logger.info("PRODUCT ID = " + id + " DELETED SUCCESSFULLY");
        } catch (NullPointerException ex) {
            logger.warning("CAN NOT DELETE: PRODUCT " + id + " NOT FOUND");
            throw new EntityNotFoundException("The product requested was not found");
        } catch (DataIntegrityViolationException ex) {
            logger.warning("THE REQUESTED ENTITY (PRODUCT ID = " + id + ") IS BEING USED BY ONE OR MORE ENTITIES");
            throw new EntityInUseException("PRODUCT = " + id);
        }
    }

}
