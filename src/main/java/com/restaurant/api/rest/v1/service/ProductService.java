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

    public ProductResponseVO save(ProductRequestVO productRequestVO) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(productRequestVO.getRestaurantId());
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            Product product = productRepository.save(new Product(productRequestVO, restaurant));
            logger.info(product + " CREATED SUCCESSFULLY");
            return new ProductResponseVO(product);
        } else {
            logger.warning("THE RESTAURANT INFORMED WASN'T FOUND");
            return null;
        }
    }

    public List<ProductResponseVO> findAll() {
        List<Product> products = productRepository.findAll();
        if (!products.isEmpty()) {
            logger.info("FOUND " + products.size() + " CITIES");
            return products.stream().map(ProductResponseVO::new).toList();
        } else {
            logger.warning("PRODUCTS NOT FOUND");
            return null;
        }
    }

    public ProductResponseVO findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            logger.info(product + " FOUND SUCCESSFULLY");
            return new ProductResponseVO(product);
        } else {
            logger.warning("PRODUCT NOT FOUND");
            return null;
        }
    }

    public ProductResponseVO update(Long id, ProductRequestVO productRequestVO) {
        Optional<Product> productOptional = productRepository.findById(id);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(productRequestVO.getRestaurantId());
        if (productOptional.isPresent() && restaurantOptional.isPresent()) {
            Product product = productOptional.get();
            Restaurant restaurant = restaurantOptional.get();
            BeanUtils.copyProperties(productRequestVO, product, "id", "restaurant");
            product.setRestaurant(restaurant);
            product = productRepository.save(product);
            logger.info(product + " UPDATED SUCCESSFULLY");
            return new ProductResponseVO(product);
        } else {
            logger.warning("CAN NOT UPDATE: PRODUCT " + id + " AND/OR RESTAURANT " + productRequestVO.getRestaurantId() + " NOT FOUND");
            return null;
        }
    }

    public ProductResponseVO delete(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            productRepository.delete(product);
            logger.info(product + " DELETED SUCCESSFULLY");
            return new ProductResponseVO(product);
        } else {
            logger.warning("CAN NOT DELETE: PRODUCT " + id + " NOT FOUND");
            return null;
        }
    }

}
