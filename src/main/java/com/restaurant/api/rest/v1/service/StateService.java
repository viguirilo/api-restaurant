package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.State;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.StateRepository;
import com.restaurant.api.rest.v1.vo.StateRequestVO;
import com.restaurant.api.rest.v1.vo.StateResponseVO;
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
public class StateService {

    private final StateRepository stateRepository;

    @Transactional
    public StateResponseVO save(StateRequestVO stepRequestVO) {
        State state = stateRepository.save(new State(stepRequestVO));
        log.info("SAVE: {} CREATED SUCCESSFULLY", state);
        return new StateResponseVO(state);
    }

    public Page<StateResponseVO> findAll(Pageable pageable) {
        Page<State> states = stateRepository.findAll(pageable);
        if (!states.isEmpty()) {
            log.info("FIND ALL: FOUND {} STATES", states.getTotalElements());
            return new PageImpl<>(states.stream().map(StateResponseVO::new).toList(), pageable, states.getTotalElements());
        } else {
            log.error("FIND ALL: STATES NOT FOUND");
            throw new EntityNotFoundException("States not found");
        }
    }

    public StateResponseVO findById(Long id) {
        State state = stateRepository.findById(id).orElseThrow(() -> {
            log.error("FIND BY ID: STATE NOT FOUND");
            return new EntityNotFoundException("The state requested was not found");
        });
        log.info("FIND BY ID: {} FOUND SUCCESSFULLY", state);
        return new StateResponseVO(state);
    }

    @Transactional
    public StateResponseVO update(Long id, StateRequestVO stateRequestVO) {
        State state = stateRepository.findById(id).orElseThrow(() -> {
            log.error("UPDATE: STATE ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The state requested was not found");
        });
        BeanUtils.copyProperties(stateRequestVO, state, "id");
        state = stateRepository.save(state);
        log.info("UPDATE: {} UPDATED SUCCESSFULLY", state);
        return new StateResponseVO(stateRepository.save(state));
    }

    @Transactional
    public void delete(Long id) {
        try {
            stateRepository.deleteById(id);
            stateRepository.flush();
            log.info("DELETE: STATE ID = {} DELETED SUCCESSFULLY", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("DELETE: THE REQUESTED STATE ID = {} IS BEING USED BY ONE OR MORE ENTITIES", id);
            throw new EntityInUseException("STATE = " + id);
        }
    }

}
