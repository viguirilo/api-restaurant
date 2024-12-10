package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.City;
import com.restaurant.api.rest.v1.entity.State;
import com.restaurant.api.rest.v1.repository.CityRepository;
import com.restaurant.api.rest.v1.repository.StateRepository;
import com.restaurant.api.rest.v1.vo.CityRequestVO;
import com.restaurant.api.rest.v1.vo.CityResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final Logger logger = Logger.getLogger(CityService.class.getName());

    public City save(CityRequestVO cityRequestVO) {
        Optional<State> stateOptional = stateRepository.findById(cityRequestVO.getStateId());
        if (stateOptional.isPresent()) {
            State state = stateOptional.get();
            logger.info("Creating a new City");
            return cityRepository.save(new City(cityRequestVO, state));
        } else {
            logger.info("The stateId informed wasn't found");
            return null;
        }
    }

    public List<CityResponseVO> findAll() {
        logger.info("Returning Cities, if exists");
        return cityRepository.findAll().stream().map(CityResponseVO::new).toList();
    }

    public CityResponseVO findById(Long id) {
        logger.info("Returning City id = " + id + ", if exists");
        Optional<City> cityOptional = cityRepository.findById(id);
        return cityOptional.map(CityResponseVO::new).orElse(null);
    }

    // TODO(revisar como o estado é atualizado)
    public City update(Long id, CityRequestVO cityRequestVO) {
        Optional<City> cityOptional = cityRepository.findById(id);
        if (cityOptional.isPresent()) {
            City city = cityOptional.get();
            BeanUtils.copyProperties(cityRequestVO, city, "id");
            logger.info("Updating City id = " + id);
            return cityRepository.save(city);
        } else {
            logger.info("Couldn't update City id = " + id + " because it doesn't exists");
            return null;
        }
    }

    public City delete(Long id) {
        Optional<City> cityOptional = cityRepository.findById(id);
        if (cityOptional.isPresent()) {
            City city = cityOptional.get();
            cityRepository.delete(city);
            logger.info("Deleting City id = " + id);
            return city;
        } else {
            logger.info("Couldn't delete City id = " + id + " because it doesn't exists");
            return null;
        }
    }

}
