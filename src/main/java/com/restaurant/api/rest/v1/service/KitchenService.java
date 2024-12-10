package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.Kitchen;
import com.restaurant.api.rest.v1.repository.KitchenRepository;
import com.restaurant.api.rest.v1.vo.KitchenRequestVO;
import com.restaurant.api.rest.v1.vo.KitchenResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class KitchenService {

    private final KitchenRepository kitchenRepository;
    private final Logger logger = Logger.getLogger(KitchenService.class.getName());

    public Kitchen save(KitchenRequestVO kitchenRequestVO) {
        logger.info("Creating a new Kitchen");
        return kitchenRepository.save(new Kitchen(kitchenRequestVO));
    }

    public List<KitchenResponseVO> findAll() {
        logger.info("Returning Kitchens, if exists");
        return kitchenRepository.findAll().stream().map(KitchenResponseVO::new).toList();
    }

    public KitchenResponseVO findById(Long id) {
        logger.info("Returning Kitchen id = " + id + ", if exists");
        Optional<Kitchen> kitchenOptional = kitchenRepository.findById(id);
        return kitchenOptional.map(KitchenResponseVO::new).orElse(null);
    }

    public Kitchen update(Long id, KitchenRequestVO kitchenRequestVO) {
        Optional<Kitchen> kitchenOptional = kitchenRepository.findById(id);
        if (kitchenOptional.isPresent()) {
            Kitchen kitchen = kitchenOptional.get();
            BeanUtils.copyProperties(kitchenRequestVO, kitchen, "id");
            logger.info("Updating Kitchen id = " + id);
            return kitchenRepository.save(kitchen);
        } else {
            logger.info("Couldn't update Kitchen id = " + id + " because it doesn't exists");
            return null;
        }
    }

    public Kitchen delete(Long id) {
        Optional<Kitchen> kitchenOptional = kitchenRepository.findById(id);
        if (kitchenOptional.isPresent()) {
            Kitchen kitchen = kitchenOptional.get();
            kitchenRepository.delete(kitchen);
            logger.info("Deleting Kitchen id = " + id);
            return kitchen;
        } else {
            logger.info("Couldn't delete City id = " + id + " because it doesn't exists");
            return null;
        }
    }

}
