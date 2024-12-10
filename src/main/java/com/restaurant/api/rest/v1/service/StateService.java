package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.State;
import com.restaurant.api.rest.v1.repository.StateRepository;
import com.restaurant.api.rest.v1.vo.StateRequestVO;
import com.restaurant.api.rest.v1.vo.StateResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository stateRepository;
    private final Logger logger = Logger.getLogger(StateService.class.getName());

    public State save(StateRequestVO stepRequestVO) {
        logger.info("Creating a new State");
        return stateRepository.save(new State(stepRequestVO));
    }

    public List<StateResponseVO> findAll() {
        logger.info("Returning States, if exists");
        return stateRepository.findAll().stream().map(StateResponseVO::new).toList();
    }

    public StateResponseVO findById(Long id) {
        logger.info("Returning State id = " + id + ", if exists");
        Optional<State> stateOptional = stateRepository.findById(id);
        return stateOptional.map(StateResponseVO::new).orElse(null);
    }

    public State update(Long id, StateRequestVO stateRequestVO) {
        Optional<State> stateOptional = stateRepository.findById(id);
        if (stateOptional.isPresent()) {
            State state = stateOptional.get();
            BeanUtils.copyProperties(stateRequestVO, state, "id");
            logger.info("Updating State id = " + id);
            return stateRepository.save(state);
        } else {
            logger.info("Couldn't update State id = " + id + " because it doesn't exists");
            return null;
        }
    }

    public State delete(Long id) {
        Optional<State> stateOptional = stateRepository.findById(id);
        if (stateOptional.isPresent()) {
            State state = stateOptional.get();
            stateRepository.delete(state);
            logger.info("Deleting State id = " + id);
            return state;
        } else {
            logger.info("Couldn't delete State id = " + id + " because it doesn't exists");
            return null;
        }
    }

}
