package org.sopac.stockpile.service;

import org.sopac.stockpile.service.dto.AgencyDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Agency.
 */
public interface AgencyService {

    /**
     * Save a agency.
     *
     * @param agencyDTO the entity to save
     * @return the persisted entity
     */
    AgencyDTO save(AgencyDTO agencyDTO);

    /**
     *  Get all the agencies.
     *  
     *  @return the list of entities
     */
    List<AgencyDTO> findAll();

    /**
     *  Get the "id" agency.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AgencyDTO findOne(Long id);

    /**
     *  Delete the "id" agency.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
