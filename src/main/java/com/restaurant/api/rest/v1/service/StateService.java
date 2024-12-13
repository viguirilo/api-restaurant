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
            return null;
        }
    }

    public StateResponseVO findById(Long id) {
        Optional<State> stateOptional = stateRepository.findById(id);
        if (stateOptional.isPresent()) {
            State state = stateOptional.get();
            logger.info(state + " FOUND SUCCESSFULLY");
            return new StateResponseVO(state);
        } else {
            logger.warning("STATE NOT FOUND");
            return null;
        }
    }

    public StateResponseVO update(Long id, StateRequestVO stateRequestVO) {
        Optional<State> stateOptional = stateRepository.findById(id);
        if (stateOptional.isPresent()) {
            State state = stateOptional.get();
            BeanUtils.copyProperties(stateRequestVO, state, "id");
            state = stateRepository.save(state);
            logger.info(state + " UPDATED SUCCESSFULLY");
            return new StateResponseVO(stateRepository.save(state));
        } else {
            logger.warning("CAN NOT UPDATE: STATE " + id + " NOT FOUND");
            return null;
        }
    }

    public StateResponseVO delete(Long id) {
        Optional<State> stateOptional = stateRepository.findById(id);
        if (stateOptional.isPresent()) {
            State state = stateOptional.get();
            stateRepository.delete(state);
            logger.info(state + " DELETED SUCCESSFULLY");
            return new StateResponseVO(state);
        } else {
            logger.warning("CAN NOT DELETE: STATE " + id + " NOT FOUND");
            return null;
        }
    }

}
