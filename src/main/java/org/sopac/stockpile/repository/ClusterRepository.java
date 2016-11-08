package org.sopac.stockpile.repository;

import org.sopac.stockpile.domain.Cluster;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cluster entity.
 */
@SuppressWarnings("unused")
public interface ClusterRepository extends JpaRepository<Cluster,Long> {

}
