package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.State;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.StateRepository;
import com.restaurant.api.rest.v1.vo.StateRequestVO;
import com.restaurant.api.rest.v1.vo.StateResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository stateRepository;
    private final Logger logger = Logger.getLogger(StateService.class.getName());

    @Transactional
    public StateResponseVO save(StateRequestVO stepRequestVO) {
        State state = stateRepository.save(new State(stepRequestVO));
        logger.info(state + " CREATED SUCCESSFULLY");
        return new StateResponseVO(state);
    }

    public List<StateResponseVO> findAll() {
        List<State> states = stateRepository.findAll();
        if (!states.isEmpty()) {
            logger.info("FOUND " + states.size() + " STATES");
            return states.stream().map(StateResponseVO::new).toList();
        } else {
            logger.warning("STATES NOT FOUND");
            throw new EntityNotFoundException("States not found");
        }
    }

    public StateResponseVO findById(Long id) {
        State state = stateRepository.findById(id).orElseThrow(() -> {
            logger.warning("STATE NOT FOUND");
            return new EntityNotFoundException("The state requested was not found");
        });
        logger.info(state + " FOUND SUCCESSFULLY");
        return new StateResponseVO(state);
    }

    @Transactional
    public StateResponseVO update(Long id, StateRequestVO stateRequestVO) {
        State state = stateRepository.findById(id).orElseThrow(() -> {
            logger.warning("CAN NOT UPDATE: STATE " + id + " NOT FOUND");
            return new EntityNotFoundException("The state requested was not found");
        });
        BeanUtils.copyProperties(stateRequestVO, state, "id");
        state = stateRepository.save(state);
        logger.info(state + " UPDATED SUCCESSFULLY");
        return new StateResponseVO(stateRepository.save(state));
    }

    @Transactional
    public void delete(Long id) {
        try {
            stateRepository.deleteById(id);
            stateRepository.flush();
            logger.info("STATE ID = " + id + " DELETED SUCCESSFULLY");
        } catch (DataIntegrityViolationException ex) {
            logger.warning("THE REQUESTED ENTITY (STATE ID = " + id + ") IS BEING USED BY ONE OR MORE ENTITIES");
            throw new EntityInUseException("STATE = " + id);
        }
    }

}
