package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.City;
import com.restaurant.api.rest.v1.entity.State;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.CityRepository;
import com.restaurant.api.rest.v1.repository.StateRepository;
import com.restaurant.api.rest.v1.vo.CityRequestVO;
import com.restaurant.api.rest.v1.vo.CityResponseVO;
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
public class CityService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    @Transactional
    public CityResponseVO save(CityRequestVO cityRequestVO) {
        State state = stateRepository.findById(cityRequestVO.getStateId()).orElseThrow(() -> {
            log.error("SAVE: STATE ID = {} NOT FOUND", cityRequestVO.getStateId());
            return new EntityNotFoundException("The state informed was not found");
        });
        City city = cityRepository.save(new City(cityRequestVO, state));
        log.info("SAVE: {} CREATED SUCCESSFULLY", city);
        return new CityResponseVO(city);
    }

    public Page<CityResponseVO> findAll(Pageable pageable) {
        Page<City> cities = cityRepository.findAll(pageable);
        if (!cities.isEmpty()) {
            log.info("FIND ALL: FOUND {} CITIES", cities.getTotalElements());
            return new PageImpl<>(cities.stream().map(CityResponseVO::new).toList(), pageable, cities.getTotalElements());
        } else {
            log.error("FIND ALL: CITIES NOT FOUND");
            throw new EntityNotFoundException("Cities not found");
        }
    }

    public CityResponseVO findById(Long id) {
        City city = cityRepository.findById(id).orElseThrow(() -> {
            log.error("FIND BY ID: CITY ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The city requested was not found");
        });
        log.info("FIND BY ID: {} FOUND SUCCESSFULLY", city);
        return new CityResponseVO(city);
    }

    @Transactional
    public CityResponseVO update(Long id, CityRequestVO cityRequestVO) {
        City city = cityRepository.findById(id).orElseThrow(() -> {
            log.error("UPDATE: CITY ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The city requested was not found");
        });
        State state = stateRepository.findById(cityRequestVO.getStateId()).orElseThrow(() -> {
            log.warn("UPDATE: STATE ID = {} NOT FOUND", cityRequestVO.getStateId());
            return new EntityNotFoundException("The state informed was not found");
        });
        BeanUtils.copyProperties(cityRequestVO, city, "id");
        city.setState(state);
        city = cityRepository.save(city);
        log.info("UPDATE: {} UPDATED SUCCESSFULLY", city);
        return new CityResponseVO(city);
    }

    @Transactional
    public void delete(Long id) {
        try {
            cityRepository.deleteById(id);
            cityRepository.flush();
            log.info("DELETE: CITY ID = {} DELETED SUCCESSFULLY", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("DELETE: THE REQUESTED CITY ID = {} IS BEING USED BY ONE OR MORE ENTITIES", id);
            throw new EntityInUseException("CITY = " + id);
        }
    }

}
