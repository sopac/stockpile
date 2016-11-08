package org.sopac.stockpile.repository;

import org.sopac.stockpile.domain.Inventory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Inventory entity.
 */
@SuppressWarnings("unused")
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

}
