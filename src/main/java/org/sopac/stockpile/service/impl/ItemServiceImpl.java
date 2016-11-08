package org.sopac.stockpile.service.impl;

import org.sopac.stockpile.service.ItemService;
import org.sopac.stockpile.domain.Item;
import org.sopac.stockpile.repository.ItemRepository;
import org.sopac.stockpile.service.dto.ItemDTO;
import org.sopac.stockpile.service.mapper.ItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Item.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService{

    private final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);
    
    @Inject
    private ItemRepository itemRepository;

    @Inject
    private ItemMapper itemMapper;

    /**
     * Save a item.
     *
     * @param itemDTO the entity to save
     * @return the persisted entity
     */
    public ItemDTO save(ItemDTO itemDTO) {
        log.debug("Request to save Item : {}", itemDTO);
        Item item = itemMapper.itemDTOToItem(itemDTO);
        item = itemRepository.save(item);
        ItemDTO result = itemMapper.itemToItemDTO(item);
        return result;
    }

    /**
     *  Get all the items.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Items");
        Page<Item> result = itemRepository.findAll(pageable);
        return result.map(item -> itemMapper.itemToItemDTO(item));
    }

    /**
     *  Get one item by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ItemDTO findOne(Long id) {
        log.debug("Request to get Item : {}", id);
        Item item = itemRepository.findOne(id);
        ItemDTO itemDTO = itemMapper.itemToItemDTO(item);
        return itemDTO;
    }

    /**
     *  Delete the  item by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Item : {}", id);
        itemRepository.delete(id);
    }
}
