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

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final KitchenRepository kitchenRepository;
    private final CityRepository cityRepository;

    //    Rever aula 4.30 para adequar como ele implementa esse método
    public Restaurant save(RestaurantRequestVO restaurantRequestVO) {
        Optional<Kitchen> kitchenOptional = kitchenRepository.findById(restaurantRequestVO.getKitchenId());
        Optional<City> cityOptional = cityRepository.findById(restaurantRequestVO.getAddressCityId());
        if (kitchenOptional.isPresent() && cityOptional.isPresent()) {
            Kitchen kitchen = kitchenOptional.get();
            City city = cityOptional.get();
            return restaurantRepository.save(new Restaurant(restaurantRequestVO, kitchen, city));
        } else return null;
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public RestaurantResponseVO findById(Long id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        return restaurantOptional.map(RestaurantResponseVO::new).orElse(null);
    }

    public Restaurant update(Long id, RestaurantRequestVO restaurantRequestVO) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            BeanUtils.copyProperties(restaurantRequestVO, restaurant, "id");
            return restaurantRepository.save(restaurant);
        } else return null;
    }

    public Restaurant delete(Long id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            restaurantRepository.delete(restaurant);
            return restaurant;
        } else return null;
    }

}
