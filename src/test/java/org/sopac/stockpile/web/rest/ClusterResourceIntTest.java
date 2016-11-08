package org.sopac.stockpile.web.rest;

import org.sopac.stockpile.StockpileApp;

import org.sopac.stockpile.domain.Cluster;
import org.sopac.stockpile.repository.ClusterRepository;
import org.sopac.stockpile.service.dto.ClusterDTO;
import org.sopac.stockpile.service.mapper.ClusterMapper;

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
 * Test class for the ClusterResource REST controller.
 *
 * @see ClusterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockpileApp.class)
public class ClusterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private ClusterRepository clusterRepository;

    @Inject
    private ClusterMapper clusterMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restClusterMockMvc;

    private Cluster cluster;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClusterResource clusterResource = new ClusterResource();
        ReflectionTestUtils.setField(clusterResource, "clusterRepository", clusterRepository);
        ReflectionTestUtils.setField(clusterResource, "clusterMapper", clusterMapper);
        this.restClusterMockMvc = MockMvcBuilders.standaloneSetup(clusterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cluster createEntity(EntityManager em) {
        Cluster cluster = new Cluster()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return cluster;
    }

    @Before
    public void initTest() {
        cluster = createEntity(em);
    }

    @Test
    @Transactional
    public void createCluster() throws Exception {
        int databaseSizeBeforeCreate = clusterRepository.findAll().size();

        // Create the Cluster
        ClusterDTO clusterDTO = clusterMapper.clusterToClusterDTO(cluster);

        restClusterMockMvc.perform(post("/api/clusters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clusterDTO)))
                .andExpect(status().isCreated());

        // Validate the Cluster in the database
        List<Cluster> clusters = clusterRepository.findAll();
        assertThat(clusters).hasSize(databaseSizeBeforeCreate + 1);
        Cluster testCluster = clusters.get(clusters.size() - 1);
        assertThat(testCluster.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCluster.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clusterRepository.findAll().size();
        // set the field null
        cluster.setName(null);

        // Create the Cluster, which fails.
        ClusterDTO clusterDTO = clusterMapper.clusterToClusterDTO(cluster);

        restClusterMockMvc.perform(post("/api/clusters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clusterDTO)))
                .andExpect(status().isBadRequest());

        List<Cluster> clusters = clusterRepository.findAll();
        assertThat(clusters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClusters() throws Exception {
        // Initialize the database
        clusterRepository.saveAndFlush(cluster);

        // Get all the clusters
        restClusterMockMvc.perform(get("/api/clusters?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cluster.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCluster() throws Exception {
        // Initialize the database
        clusterRepository.saveAndFlush(cluster);

        // Get the cluster
        restClusterMockMvc.perform(get("/api/clusters/{id}", cluster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cluster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCluster() throws Exception {
        // Get the cluster
        restClusterMockMvc.perform(get("/api/clusters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCluster() throws Exception {
        // Initialize the database
        clusterRepository.saveAndFlush(cluster);
        int databaseSizeBeforeUpdate = clusterRepository.findAll().size();

        // Update the cluster
        Cluster updatedCluster = clusterRepository.findOne(cluster.getId());
        updatedCluster
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        ClusterDTO clusterDTO = clusterMapper.clusterToClusterDTO(updatedCluster);

        restClusterMockMvc.perform(put("/api/clusters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clusterDTO)))
                .andExpect(status().isOk());

        // Validate the Cluster in the database
        List<Cluster> clusters = clusterRepository.findAll();
        assertThat(clusters).hasSize(databaseSizeBeforeUpdate);
        Cluster testCluster = clusters.get(clusters.size() - 1);
        assertThat(testCluster.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCluster.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteCluster() throws Exception {
        // Initialize the database
        clusterRepository.saveAndFlush(cluster);
        int databaseSizeBeforeDelete = clusterRepository.findAll().size();

        // Get the cluster
        restClusterMockMvc.perform(delete("/api/clusters/{id}", cluster.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cluster> clusters = clusterRepository.findAll();
        assertThat(clusters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
