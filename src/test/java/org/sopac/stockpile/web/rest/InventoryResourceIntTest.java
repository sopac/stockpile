package org.sopac.stockpile.web.rest;

import org.sopac.stockpile.StockpileApp;

import org.sopac.stockpile.domain.Inventory;
import org.sopac.stockpile.repository.InventoryRepository;
import org.sopac.stockpile.service.dto.InventoryDTO;
import org.sopac.stockpile.service.mapper.InventoryMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InventoryResource REST controller.
 *
 * @see InventoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockpileApp.class)
public class InventoryResourceIntTest {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final Double DEFAULT_QUANITY = 1D;
    private static final Double UPDATED_QUANITY = 2D;

    @Inject
    private InventoryRepository inventoryRepository;

    @Inject
    private InventoryMapper inventoryMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restInventoryMockMvc;

    private Inventory inventory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InventoryResource inventoryResource = new InventoryResource();
        ReflectionTestUtils.setField(inventoryResource, "inventoryRepository", inventoryRepository);
        ReflectionTestUtils.setField(inventoryResource, "inventoryMapper", inventoryMapper);
        this.restInventoryMockMvc = MockMvcBuilders.standaloneSetup(inventoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventory createEntity(EntityManager em) {
        Inventory inventory = new Inventory()
                .year(DEFAULT_YEAR)
                .month(DEFAULT_MONTH)
                .quanity(DEFAULT_QUANITY);
        return inventory;
    }

    @Before
    public void initTest() {
        inventory = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventory() throws Exception {
        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();

        // Create the Inventory
        InventoryDTO inventoryDTO = inventoryMapper.inventoryToInventoryDTO(inventory);

        restInventoryMockMvc.perform(post("/api/inventories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
                .andExpect(status().isCreated());

        // Validate the Inventory in the database
        List<Inventory> inventories = inventoryRepository.findAll();
        assertThat(inventories).hasSize(databaseSizeBeforeCreate + 1);
        Inventory testInventory = inventories.get(inventories.size() - 1);
        assertThat(testInventory.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testInventory.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testInventory.getQuanity()).isEqualTo(DEFAULT_QUANITY);
    }

    @Test
    @Transactional
    public void getAllInventories() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventories
        restInventoryMockMvc.perform(get("/api/inventories?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(inventory.getId().intValue())))
                .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
                .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
                .andExpect(jsonPath("$.[*].quanity").value(hasItem(DEFAULT_QUANITY.doubleValue())));
    }

    @Test
    @Transactional
    public void getInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get the inventory
        restInventoryMockMvc.perform(get("/api/inventories/{id}", inventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventory.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.quanity").value(DEFAULT_QUANITY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInventory() throws Exception {
        // Get the inventory
        restInventoryMockMvc.perform(get("/api/inventories/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // Update the inventory
        Inventory updatedInventory = inventoryRepository.findOne(inventory.getId());
        updatedInventory
                .year(UPDATED_YEAR)
                .month(UPDATED_MONTH)
                .quanity(UPDATED_QUANITY);
        InventoryDTO inventoryDTO = inventoryMapper.inventoryToInventoryDTO(updatedInventory);

        restInventoryMockMvc.perform(put("/api/inventories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventoryDTO)))
                .andExpect(status().isOk());

        // Validate the Inventory in the database
        List<Inventory> inventories = inventoryRepository.findAll();
        assertThat(inventories).hasSize(databaseSizeBeforeUpdate);
        Inventory testInventory = inventories.get(inventories.size() - 1);
        assertThat(testInventory.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testInventory.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testInventory.getQuanity()).isEqualTo(UPDATED_QUANITY);
    }

    @Test
    @Transactional
    public void deleteInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);
        int databaseSizeBeforeDelete = inventoryRepository.findAll().size();

        // Get the inventory
        restInventoryMockMvc.perform(delete("/api/inventories/{id}", inventory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Inventory> inventories = inventoryRepository.findAll();
        assertThat(inventories).hasSize(databaseSizeBeforeDelete - 1);
    }
}
