package org.sopac.stockpile.repository;

import org.sopac.stockpile.domain.Agency;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Agency entity.
 */
@SuppressWarnings("unused")
public interface AgencyRepository extends JpaRepository<Agency,Long> {

}
