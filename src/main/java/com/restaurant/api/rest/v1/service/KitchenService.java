package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.Kitchen;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.KitchenRepository;
import com.restaurant.api.rest.v1.vo.KitchenRequestVO;
import com.restaurant.api.rest.v1.vo.KitchenResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class KitchenService {

    private final KitchenRepository kitchenRepository;
    private final Logger logger = Logger.getLogger(KitchenService.class.getName());

    @Transactional
    public KitchenResponseVO save(KitchenRequestVO kitchenRequestVO) {
        Kitchen kitchen = kitchenRepository.save(new Kitchen(kitchenRequestVO));
        logger.info(kitchen + " CREATED SUCCESSFULLY");
        return new KitchenResponseVO(kitchen);
    }

    public Page<KitchenResponseVO> findAll(Pageable pageable) {
        Page<Kitchen> kitchens = kitchenRepository.findAll(pageable);
        if (!kitchens.isEmpty()) {
            logger.info("FOUND " + kitchens.getTotalElements() + " KITCHENS");
            return new PageImpl<>(kitchens.stream().map(KitchenResponseVO::new).toList(), pageable, kitchens.getTotalElements());
        } else {
            logger.warning("KITCHENS NOT FOUND");
            throw new EntityNotFoundException("Kitchens not found");
        }
    }

    public KitchenResponseVO findById(Long id) {
        Kitchen kitchen = kitchenRepository.findById(id).orElseThrow(() -> {
            logger.warning("KITCHEN NOT FOUND");
            return new EntityNotFoundException("The kitchen requested was not found");
        });
        logger.info(kitchen + " FOUND SUCCESSFULLY");
        return new KitchenResponseVO(kitchen);
    }

    @Transactional
    public KitchenResponseVO update(Long id, KitchenRequestVO kitchenRequestVO) {
        Kitchen kitchen = kitchenRepository.findById(id).orElseThrow(() -> {
            logger.warning("CAN NOT UPDATE: KITCHEN " + id + " NOT FOUND");
            return new EntityNotFoundException("The kitchen requested was not found");
        });
        BeanUtils.copyProperties(kitchenRequestVO, kitchen, "id");
        kitchen = kitchenRepository.save(kitchen);
        logger.info(kitchen + " UPDATED SUCCESSFULLY");
        return new KitchenResponseVO(kitchen);
    }

    @Transactional
    public void delete(Long id) {
        try {
            kitchenRepository.deleteById(id);
            kitchenRepository.flush();
            logger.info("KITCHEN ID = " + id + " DELETED SUCCESSFULLY");
        } catch (DataIntegrityViolationException ex) {
            logger.warning("THE REQUESTED ENTITY (KITCHEN ID = " + id + ") IS BEING USED BY ONE OR MORE ENTITIES");
            throw new EntityInUseException("KITCHEN = " + id);
        }
    }

}
