package org.sopac.stockpile.service;

import org.sopac.stockpile.service.dto.ClusterDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Cluster.
 */
public interface ClusterService {

    /**
     * Save a cluster.
     *
     * @param clusterDTO the entity to save
     * @return the persisted entity
     */
    ClusterDTO save(ClusterDTO clusterDTO);

    /**
     *  Get all the clusters.
     *  
     *  @return the list of entities
     */
    List<ClusterDTO> findAll();

    /**
     *  Get the "id" cluster.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClusterDTO findOne(Long id);

    /**
     *  Delete the "id" cluster.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
