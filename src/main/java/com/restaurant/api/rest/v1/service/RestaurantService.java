package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.City;
import com.restaurant.api.rest.v1.entity.Kitchen;
import com.restaurant.api.rest.v1.entity.Restaurant;
import com.restaurant.api.rest.v1.exception.BadRequestException;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.CityRepository;
import com.restaurant.api.rest.v1.repository.KitchenRepository;
import com.restaurant.api.rest.v1.repository.RestaurantRepository;
import com.restaurant.api.rest.v1.vo.RestaurantRequestVO;
import com.restaurant.api.rest.v1.vo.RestaurantResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final KitchenRepository kitchenRepository;
    private final CityRepository cityRepository;
    private final Logger logger = Logger.getLogger(RestaurantService.class.getName());

    // TODO(Rever aula 4.30 para adequar como ele implementa esse método)
    @Transactional
    public RestaurantResponseVO save(RestaurantRequestVO restaurantRequestVO) {
        Kitchen kitchen = kitchenRepository.findById(restaurantRequestVO.getKitchenId()).orElseThrow(() -> {
            logger.warning("KITCHEN ID = " + restaurantRequestVO.getKitchenId() + " WAS NOT FOUND");
            return new BadRequestException("The kitchen informed was not found");
        });
        City city = cityRepository.findById(restaurantRequestVO.getAddressCityId()).orElseThrow(() -> {
            logger.warning("CITY ID = " + restaurantRequestVO.getAddressCityId() + " WAS NOT FOUND");
            return new BadRequestException("The city informed was not found");
        });
        Restaurant restaurant = restaurantRepository.save(new Restaurant(restaurantRequestVO, kitchen, city));
        logger.info(restaurant + " CREATED SUCCESSFULLY");
        return new RestaurantResponseVO(restaurant);
    }

    public Page<RestaurantResponseVO> findAll(Pageable pageable) {
        Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);
        if (!restaurants.isEmpty()) {
            logger.info("FOUND " + restaurants.getTotalElements() + " RESTAURANTS");
            return new PageImpl<>(restaurants.stream().map(RestaurantResponseVO::new).toList(), pageable, restaurants.getTotalElements());
        } else {
            logger.warning("RESTAURANTS NOT FOUND");
            throw new EntityNotFoundException("Restaurants not found");
        }
    }

    public RestaurantResponseVO findById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> {
            logger.warning("RESTAURANT ID = " + id + " NOT FOUND");
            return new EntityNotFoundException("The restaurant requested was not found");
        });
        logger.info(restaurant + " FOUND SUCCESSFULLY");
        return new RestaurantResponseVO(restaurant);
    }

    //    TODO(ver como fica o BeanUtils.copyProperties em relação a atualização das entidades internas como kitchen, city, products)
    //    TODO(implementar a parte de atualização de endereço)
    @Transactional
    public RestaurantResponseVO update(Long id, RestaurantRequestVO restaurantRequestVO) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> {
            logger.warning("RESTAURANT ID = " + id + " NOT FOUND");
            return new EntityNotFoundException("The restaurant requested was not found");
        });
        Kitchen kitchen = kitchenRepository.findById(restaurantRequestVO.kitchenId).orElseThrow(() -> {
            logger.warning("CAN NOT UPDATE RESTAURANT: KITCHEN " + restaurantRequestVO.getKitchenId() + " NOT FOUND");
            return new BadRequestException("The kitchen informed was not found");
        });
        BeanUtils.copyProperties(
                restaurantRequestVO,
                restaurant,
                "id", "address", "paymentMethods", "creationDate"
        );
        restaurant.setKitchen(kitchen);
        restaurant = restaurantRepository.save(restaurant);
        logger.info(restaurant + " UPDATED SUCCESSFULLY");
        return new RestaurantResponseVO(restaurant);
    }

    @Transactional
    public void delete(Long id) {
        try {
            restaurantRepository.deleteById(id);
            restaurantRepository.flush();
            logger.info("RESTAURANT ID = " + id + " DELETED SUCCESSFULLY");
        } catch (DataIntegrityViolationException ex) {
            logger.warning("THE REQUESTED ENTITY (RESTAURANT ID = " + id + ") IS BEING USED BY ONE OR MORE ENTITIES");
            throw new EntityInUseException("RESTAURANT = " + id);
        }
    }

}
