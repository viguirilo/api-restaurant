package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.Kitchen;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.KitchenRepository;
import com.restaurant.api.rest.v1.vo.KitchenRequestVO;
import com.restaurant.api.rest.v1.vo.KitchenResponseVO;
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
public class KitchenService {

    private final KitchenRepository kitchenRepository;

    @Transactional
    public KitchenResponseVO save(KitchenRequestVO kitchenRequestVO) {
        Kitchen kitchen = kitchenRepository.save(new Kitchen(kitchenRequestVO));
        log.info("SAVE: {} CREATED SUCCESSFULLY", kitchen);
        return new KitchenResponseVO(kitchen);
    }

    public Page<KitchenResponseVO> findAll(Pageable pageable) {
        Page<Kitchen> kitchens = kitchenRepository.findAll(pageable);
        if (!kitchens.isEmpty()) {
            log.info("FIND ALL: FOUND {} KITCHENS", kitchens.getTotalElements());
            return new PageImpl<>(kitchens.stream().map(KitchenResponseVO::new).toList(), pageable, kitchens.getTotalElements());
        } else {
            log.error("FIND ALL: KITCHENS NOT FOUND");
            throw new EntityNotFoundException("Kitchens not found");
        }
    }

    public KitchenResponseVO findById(Long id) {
        Kitchen kitchen = kitchenRepository.findById(id).orElseThrow(() -> {
            log.error("FIND BY ID: KITCHEN ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The kitchen requested was not found");
        });
        log.info("FIND BY ID: {} FOUND SUCCESSFULLY", kitchen);
        return new KitchenResponseVO(kitchen);
    }

    @Transactional
    public KitchenResponseVO update(Long id, KitchenRequestVO kitchenRequestVO) {
        Kitchen kitchen = kitchenRepository.findById(id).orElseThrow(() -> {
            log.error(": KITCHEN ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The kitchen requested was not found");
        });
        BeanUtils.copyProperties(kitchenRequestVO, kitchen, "id");
        kitchen = kitchenRepository.save(kitchen);
        log.info("UPDATE: {} UPDATED SUCCESSFULLY", kitchen);
        return new KitchenResponseVO(kitchen);
    }

    @Transactional
    public void delete(Long id) {
        try {
            kitchenRepository.deleteById(id);
            kitchenRepository.flush();
            log.info("DELETE: KITCHEN ID = {} DELETED SUCCESSFULLY", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("DELETE: THE REQUESTED KITCHEN ID = {} IS BEING USED BY ONE OR MORE ENTITIES", id);
            throw new EntityInUseException("KITCHEN = " + id);
        }
    }

}
