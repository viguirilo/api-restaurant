package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.City;
import com.restaurant.api.rest.v1.entity.Kitchen;
import com.restaurant.api.rest.v1.entity.Restaurant;
import com.restaurant.api.rest.v1.repository.CityRepository;
import com.restaurant.api.rest.v1.repository.KitchenRepository;
import com.restaurant.api.rest.v1.repository.RestaurantRepository;
import com.restaurant.api.rest.v1.vo.RestaurantRequestVO;
import com.restaurant.api.rest.v1.vo.RestaurantResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final KitchenRepository kitchenRepository;
    private final CityRepository cityRepository;
    private final Logger logger = Logger.getLogger(RestaurantService.class.getName());

    // TODO(Rever aula 4.30 para adequar como ele implementa esse método)
    public RestaurantResponseVO save(RestaurantRequestVO restaurantRequestVO) {
        Optional<Kitchen> kitchenOptional = kitchenRepository.findById(restaurantRequestVO.getKitchenId());
        Optional<City> cityOptional = cityRepository.findById(restaurantRequestVO.getAddressCityId());
        if (kitchenOptional.isPresent() && cityOptional.isPresent()) {
            Kitchen kitchen = kitchenOptional.get();
            City city = cityOptional.get();
            logger.info("Creating a new Restaurant");
            return new RestaurantResponseVO(restaurantRepository.save(new Restaurant(restaurantRequestVO, kitchen, city)));
        } else {
            logger.info("The kitchenId or/and addressCityId informed weren't found");
            return null;
        }
    }

    public List<RestaurantResponseVO> findAll() {
        logger.info("Returning Restaurants, if exists");
        return restaurantRepository.findAll().stream().map(RestaurantResponseVO::new).toList();
    }

    public RestaurantResponseVO findById(Long id) {
        logger.info("Returning Restaurant id = " + id + ", if exists");
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        return restaurantOptional.map(RestaurantResponseVO::new).orElse(null);
    }

    public RestaurantResponseVO update(Long id, RestaurantRequestVO restaurantRequestVO) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            BeanUtils.copyProperties(restaurantRequestVO, restaurant, "id");
            logger.info("Updating Restaurant id = " + id);
            return new RestaurantResponseVO(restaurantRepository.save(restaurant));
        } else {
            logger.info("Couldn't update Restaurant id = " + id + " because it doesn't exists");
            return null;
        }
    }

    public RestaurantResponseVO delete(Long id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            logger.info("Deleting Restaurant id = " + id);
            restaurantRepository.delete(restaurant);
            return new RestaurantResponseVO(restaurant);
        } else {
            logger.info("Couldn't delete Restaurant id = " + id + " because it doesn't exists");
            return null;
        }
    }

}
