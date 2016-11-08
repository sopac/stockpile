package org.sopac.stockpile.service;

import org.sopac.stockpile.service.dto.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Item.
 */
public interface ItemService {

    /**
     * Save a item.
     *
     * @param itemDTO the entity to save
     * @return the persisted entity
     */
    ItemDTO save(ItemDTO itemDTO);

    /**
     *  Get all the items.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ItemDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" item.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ItemDTO findOne(Long id);

    /**
     *  Delete the "id" item.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
