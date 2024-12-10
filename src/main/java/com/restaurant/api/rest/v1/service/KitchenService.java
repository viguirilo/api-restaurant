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

    public KitchenResponseVO save(KitchenRequestVO kitchenRequestVO) {
        Kitchen kitchen = kitchenRepository.save(new Kitchen(kitchenRequestVO));
        logger.info(kitchen + " CREATED SUCCESSFULLY");
        return new KitchenResponseVO(kitchen);
    }

    public List<KitchenResponseVO> findAll() {
        List<Kitchen> kitchens = kitchenRepository.findAll();
        if (!kitchens.isEmpty()) {
            logger.info("FOUND " + kitchens.size() + " KITCHENS");
            return kitchens.stream().map(KitchenResponseVO::new).toList();
        } else {
            logger.warning("KITCHENS NOT FOUND");
            return null;
        }
    }

    public KitchenResponseVO findById(Long id) {
        Optional<Kitchen> kitchenOptional = kitchenRepository.findById(id);
        if (kitchenOptional.isPresent()) {
            Kitchen kitchen = kitchenOptional.get();
            logger.info(kitchen + " FOUND SUCCESSFULLY");
            return new KitchenResponseVO(kitchen);
        } else {
            logger.warning("KITCHEN NOT FOUND");
            return null;
        }
    }

    public KitchenResponseVO update(Long id, KitchenRequestVO kitchenRequestVO) {
        Optional<Kitchen> kitchenOptional = kitchenRepository.findById(id);
        if (kitchenOptional.isPresent()) {
            Kitchen kitchen = kitchenOptional.get();
            BeanUtils.copyProperties(kitchenRequestVO, kitchen, "id");
            kitchen = kitchenRepository.save(kitchen);
            logger.info(kitchen + " UPDATED SUCCESSFULLY");
            return new KitchenResponseVO(kitchen);
        } else {
            logger.warning("CAN NOT UPDATE: KITCHEN " + id + " NOT FOUND");
            return null;
        }
    }

    public KitchenResponseVO delete(Long id) {
        Optional<Kitchen> kitchenOptional = kitchenRepository.findById(id);
        if (kitchenOptional.isPresent()) {
            Kitchen kitchen = kitchenOptional.get();
            kitchenRepository.delete(kitchen);
            logger.info(kitchen + " DELETED SUCCESSFULLY");
            return new KitchenResponseVO(kitchen);
        } else {
            logger.warning("CAN NOT DELETE: KITCHEN " + id + " NOT FOUND");
            return null;
        }
    }

}
