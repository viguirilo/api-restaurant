package com.restaurant.api.rest.v1.repository;

import com.restaurant.api.rest.v1.entity.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {
// TODO(Rever aula 5.9 para ver como implementar queries personaalizadas usabdo JPQL)
// TODO(Rever aula 5.12 para ver como implementar queries dinâmicas usando JPQL)
}
