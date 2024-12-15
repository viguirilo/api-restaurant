package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.City;
import com.restaurant.api.rest.v1.entity.State;
import com.restaurant.api.rest.v1.exception.BadRequestException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.CityRepository;
import com.restaurant.api.rest.v1.repository.StateRepository;
import com.restaurant.api.rest.v1.vo.CityRequestVO;
import com.restaurant.api.rest.v1.vo.CityResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final Logger logger = Logger.getLogger(CityService.class.getName());

    public CityResponseVO save(CityRequestVO cityRequestVO) {
        State state = stateRepository.findById(cityRequestVO.getStateId()).orElseThrow(() -> {
            logger.warning("STATE ID = " + cityRequestVO.getStateId() + " WAS NOT FOUND");
            return new BadRequestException("The state informed was not found");
        });
        City city = cityRepository.save(new City(cityRequestVO, state));
        logger.info(city + " CREATED SUCCESSFULLY");
        return new CityResponseVO(city);
    }

    public List<CityResponseVO> findAll() {
        List<City> cities = cityRepository.findAll();
        if (!cities.isEmpty()) {
            logger.info("FOUND " + cities.size() + " CITIES");
            return cities.stream().map(CityResponseVO::new).toList();
        } else {
            logger.warning("CITIES NOT FOUND");
            throw new EntityNotFoundException("Cities not found");
        }
    }

    public CityResponseVO findById(Long id) {
        City city = cityRepository.findById(id).orElseThrow(() -> {
            logger.warning("CITY ID = " + id + " NOT FOUND");
            return new EntityNotFoundException("The city requested was not found");
        });
        logger.info(city + " FOUND SUCCESSFULLY");
        return new CityResponseVO(city);
    }

    public CityResponseVO update(Long id, CityRequestVO cityRequestVO) {
        City city = cityRepository.findById(id).orElseThrow(() -> {
            logger.warning("CAN NOT UPDATE: CITY " + id + " NOT FOUND");
            return new EntityNotFoundException("The city requested was not found");
        });
        State state = stateRepository.findById(cityRequestVO.getStateId()).orElseThrow(() -> {
            logger.warning("CAN NOT UPDATE: STATE " + cityRequestVO.getStateId() + " NOT FOUND");
            return new BadRequestException("The state informed was not found");
        });
        BeanUtils.copyProperties(cityRequestVO, city, "id");
        city.setState(state);
        city = cityRepository.save(city);
        logger.info(city + " UPDATED SUCCESSFULLY");
        return new CityResponseVO(city);
    }

    public void delete(Long id) {
        City city = cityRepository.findById(id).orElseThrow(() -> {
            logger.warning("CAN NOT DELETE: CITY " + id + " NOT FOUND");
            return new EntityNotFoundException("The city requested was not found");
        });
        cityRepository.delete(city);
        logger.info(city + " DELETED SUCCESSFULLY");
    }

}
