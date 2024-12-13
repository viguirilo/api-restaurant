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

    public CityResponseVO save(CityRequestVO cityRequestVO) {
        Optional<State> stateOptional = stateRepository.findById(cityRequestVO.getStateId());
        if (stateOptional.isPresent()) {
            State state = stateOptional.get();
            City city = cityRepository.save(new City(cityRequestVO, state));
            logger.info(city + " CREATED SUCCESSFULLY");
            return new CityResponseVO(city);
        } else {
            logger.warning("THE STATE INFORMED WASN'T FOUND");
            return null;
        }
    }

    public List<CityResponseVO> findAll() {
        List<City> cities = cityRepository.findAll();
        if (!cities.isEmpty()) {
            logger.info("FOUND " + cities.size() + " CITIES");
            return cities.stream().map(CityResponseVO::new).toList();
        } else {
            logger.warning("CITIES NOT FOUND");
            return null;
        }
    }

    public CityResponseVO findById(Long id) {
        Optional<City> cityOptional = cityRepository.findById(id);
        if (cityOptional.isPresent()) {
            City city = cityOptional.get();
            logger.info(city + " FOUND SUCCESSFULLY");
            return new CityResponseVO(city);
        } else {
            logger.warning("CITY NOT FOUND");
            return null;
        }
    }

    public CityResponseVO update(Long id, CityRequestVO cityRequestVO) {
        Optional<City> cityOptional = cityRepository.findById(id);
        Optional<State> stateOptional = stateRepository.findById(cityRequestVO.getStateId());
        if (cityOptional.isPresent() && stateOptional.isPresent()) {
            City city = cityOptional.get();
            State state = stateOptional.get();
            BeanUtils.copyProperties(cityRequestVO, city, "id");
            city.setState(state);
            city = cityRepository.save(city);
            logger.info(city + " UPDATED SUCCESSFULLY");
            return new CityResponseVO(city);
        } else {
            logger.warning("CAN NOT UPDATE: CITY " + id + " AND/OR STATE " + cityRequestVO.getStateId() + " NOT FOUND");
            return null;
        }
    }

    public CityResponseVO delete(Long id) {
        Optional<City> cityOptional = cityRepository.findById(id);
        if (cityOptional.isPresent()) {
            City city = cityOptional.get();
            cityRepository.delete(city);
            logger.info(city + " DELETED SUCCESSFULLY");
            return new CityResponseVO(city);
        } else {
            logger.warning("CAN NOT DELETE: CITY " + id + " NOT FOUND");
            return null;
        }
    }

}
