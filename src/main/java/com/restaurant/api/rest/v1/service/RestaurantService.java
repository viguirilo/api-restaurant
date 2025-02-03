package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.City;
import com.restaurant.api.rest.v1.entity.Kitchen;
import com.restaurant.api.rest.v1.entity.Restaurant;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.CityRepository;
import com.restaurant.api.rest.v1.repository.KitchenRepository;
import com.restaurant.api.rest.v1.repository.RestaurantRepository;
import com.restaurant.api.rest.v1.vo.RestaurantRequestVO;
import com.restaurant.api.rest.v1.vo.RestaurantResponseVO;
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
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final KitchenRepository kitchenRepository;
    private final CityRepository cityRepository;

    // TODO(Rever aula 4.30 para adequar como ele implementa esse método)
    @Transactional
    public RestaurantResponseVO save(RestaurantRequestVO restaurantRequestVO) {
        Kitchen kitchen = kitchenRepository.findById(restaurantRequestVO.getKitchenId()).orElseThrow(() -> {
            log.error("SAVE: KITCHEN ID = {} NOT FOUND", restaurantRequestVO.getKitchenId());
            return new EntityNotFoundException("The kitchen informed was not found");
        });
        City city = cityRepository.findById(restaurantRequestVO.getAddressCityId()).orElseThrow(() -> {
            log.error("SAVE: CITY ID = {} NOT FOUND", restaurantRequestVO.getAddressCityId());
            return new EntityNotFoundException("The city informed was not found");
        });
        Restaurant restaurant = restaurantRepository.save(new Restaurant(restaurantRequestVO, kitchen, city));
        log.info("SAVE: {} CREATED SUCCESSFULLY", restaurant);
        return new RestaurantResponseVO(restaurant);
    }

    public Page<RestaurantResponseVO> findAll(Pageable pageable) {
        Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);
        if (!restaurants.isEmpty()) {
            log.info("FIND ALL: FOUND {} RESTAURANTS", restaurants.getTotalElements());
            return new PageImpl<>(restaurants.stream().map(RestaurantResponseVO::new).toList(), pageable, restaurants.getTotalElements());
        } else {
            log.error("FIND ALL: RESTAURANTS NOT FOUND");
            throw new EntityNotFoundException("Restaurants not found");
        }
    }

    public RestaurantResponseVO findById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> {
            log.error("FIND BY ID: RESTAURANT ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The restaurant requested was not found");
        });
        log.info("FIND BY ID: {} FOUND SUCCESSFULLY", restaurant);
        return new RestaurantResponseVO(restaurant);
    }

    //    TODO(ver como fica o BeanUtils.copyProperties em relação a atualização das entidades internas como kitchen, city, products)
    //    TODO(implementar a parte de atualização de endereço)
    @Transactional
    public RestaurantResponseVO update(Long id, RestaurantRequestVO restaurantRequestVO) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> {
            log.error("UPDATE: RESTAURANT ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The restaurant requested was not found");
        });
        Kitchen kitchen = kitchenRepository.findById(restaurantRequestVO.kitchenId).orElseThrow(() -> {
            log.error("UPDATE: CAN NOT UPDATE RESTAURANT: KITCHEN {} NOT FOUND", restaurantRequestVO.getKitchenId());
            return new EntityNotFoundException("The kitchen informed was not found");
        });
        BeanUtils.copyProperties(
                restaurantRequestVO,
                restaurant,
                "id", "address", "paymentMethods", "creationDate"
        );
        restaurant.setKitchen(kitchen);
        restaurant = restaurantRepository.save(restaurant);
        log.info("UPDATE: {} UPDATED SUCCESSFULLY", restaurant);
        return new RestaurantResponseVO(restaurant);
    }

    @Transactional
    public void delete(Long id) {
        try {
            restaurantRepository.deleteById(id);
            restaurantRepository.flush();
            log.info("DELETE: RESTAURANT ID = {} DELETED SUCCESSFULLY", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("DELETE: THE REQUESTED RESTAURANT ID = {} IS BEING USED BY ONE OR MORE ENTITIES", id);
            throw new EntityInUseException("RESTAURANT = " + id);
        }
    }

}
