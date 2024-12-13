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
            Restaurant restaurant = restaurantRepository.save(new Restaurant(restaurantRequestVO, kitchen, city));
            logger.info(restaurant + " CREATED SUCCESSFULLY");
            return new RestaurantResponseVO(restaurant);
        } else {
            logger.warning("THE KITCHEN OR/AND CITY INFORMED WEREN'T FOUND");
            return null;
        }
    }

    public List<RestaurantResponseVO> findAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (!restaurants.isEmpty()) {
            logger.info("FOUND " + restaurants.size() + " CITIES");
            return restaurants.stream().map(RestaurantResponseVO::new).toList();
        } else {
            logger.warning("RESTAURANTS NOT FOUND");
            return null;
        }
    }

    public RestaurantResponseVO findById(Long id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            logger.info(restaurant + " FOUND SUCCESSFULLY");
            return new RestaurantResponseVO(restaurant);
        } else {
            logger.warning("RESTAURANT NOT FOUND");
            return null;
        }
    }

    public RestaurantResponseVO update(Long id, RestaurantRequestVO restaurantRequestVO) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            BeanUtils.copyProperties(
                    restaurantRequestVO,
                    restaurant,
                    "id", "address", "paymentMethods", "creationDate"
            );
            restaurant = restaurantRepository.save(restaurant);
            logger.info(restaurant + " UPDATED SUCCESSFULLY");
            return new RestaurantResponseVO(restaurant);
        } else {
            logger.warning("CAN NOT UPDATE: RESTAURANT " + id + " NOT FOUND");
            return null;
        }
    }

    public RestaurantResponseVO delete(Long id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            logger.info("Deleting Restaurant id = " + id);
            restaurantRepository.delete(restaurant);
            logger.info(restaurant + " DELETED SUCCESSFULLY");
            return new RestaurantResponseVO(restaurant);
        } else {
            logger.warning("CAN NOT DELETE: RESTAURANT " + id + " NOT FOUND");
            return null;
        }
    }

}
