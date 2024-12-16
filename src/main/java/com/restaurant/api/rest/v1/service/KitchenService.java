package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.Kitchen;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.KitchenRepository;
import com.restaurant.api.rest.v1.vo.KitchenRequestVO;
import com.restaurant.api.rest.v1.vo.KitchenResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void delete(Long id) {
        Kitchen kitchen = kitchenRepository.findById(id).orElseThrow(() -> {
            logger.warning("CAN NOT DELETE: KITCHEN " + id + " NOT FOUND");
            return new EntityNotFoundException("The kitchen requested was not found");
        });
        kitchenRepository.delete(kitchen);
        logger.info(kitchen + " DELETED SUCCESSFULLY");
    }

}
