package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.Product;
import com.restaurant.api.rest.v1.entity.Restaurant;
import com.restaurant.api.rest.v1.repository.ProductRepository;
import com.restaurant.api.rest.v1.repository.RestaurantRepository;
import com.restaurant.api.rest.v1.vo.ProductRequestVO;
import com.restaurant.api.rest.v1.vo.ProductResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;
    private final Logger logger = Logger.getLogger(ProductService.class.getName());

    public Product save(ProductRequestVO productRequestVO) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(productRequestVO.getRestaurantId());
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            logger.info("Creating a new Product");
            return productRepository.save(new Product(productRequestVO, restaurant));
        } else {
            logger.info("The restaurantId informed wasn't found");
            return null;
        }
    }

    public List<ProductResponseVO> findAll() {
        logger.info("Returning Products, if exists");
        return productRepository.findAll().stream().map(ProductResponseVO::new).toList();
    }

    public ProductResponseVO findById(Long id) {
        logger.info("Returning Product id = " + id + ", if exists");
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.map(ProductResponseVO::new).orElse(null);
    }

    public Product update(Long id, ProductRequestVO productRequestVO) {
        Optional<Product> productOptional = productRepository.findById(id);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(productRequestVO.getRestaurantId());
        if (productOptional.isPresent() && restaurantOptional.isPresent()) {
            Product product = productOptional.get();
            Restaurant restaurant = restaurantOptional.get();
            BeanUtils.copyProperties(productRequestVO, product, "id", "restaurant");
            product.setRestaurant(restaurant);
            logger.info("Updating Product id = " + id);
            return productRepository.save(product);
        } else {
            logger.info("Couldn't update Product id = " + id + " because it doesn't exists");
            return null;
        }
    }

    public Product delete(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            productRepository.delete(product);
            logger.info("Deleting Product id = " + id);
            return product;
        } else {
            logger.info("Couldn't delete Product id = " + id + " because it doesn't exists");
            return null;
        }
    }

}
