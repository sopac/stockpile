package org.sopac.stockpile.web.rest;

import org.sopac.stockpile.StockpileApp;

import org.sopac.stockpile.domain.Agency;
import org.sopac.stockpile.repository.AgencyRepository;
import org.sopac.stockpile.service.dto.AgencyDTO;
import org.sopac.stockpile.service.mapper.AgencyMapper;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AgencyResource REST controller.
 *
 * @see AgencyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockpileApp.class)
public class AgencyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_ORGANISATION_TYPE = "AAAAA";
    private static final String UPDATED_ORGANISATION_TYPE = "BBBBB";

    private static final String DEFAULT_CONTACT = "AAAAA";
    private static final String UPDATED_CONTACT = "BBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    @Inject
    private AgencyRepository agencyRepository;

    @Inject
    private AgencyMapper agencyMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAgencyMockMvc;

    private Agency agency;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AgencyResource agencyResource = new AgencyResource();
        ReflectionTestUtils.setField(agencyResource, "agencyRepository", agencyRepository);
        ReflectionTestUtils.setField(agencyResource, "agencyMapper", agencyMapper);
        this.restAgencyMockMvc = MockMvcBuilders.standaloneSetup(agencyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agency createEntity(EntityManager em) {
        Agency agency = new Agency()
                .name(DEFAULT_NAME)
                .organisationType(DEFAULT_ORGANISATION_TYPE)
                .contact(DEFAULT_CONTACT)
                .logo(DEFAULT_LOGO)
                .logoContentType(DEFAULT_LOGO_CONTENT_TYPE);
        return agency;
    }

    @Before
    public void initTest() {
        agency = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgency() throws Exception {
        int databaseSizeBeforeCreate = agencyRepository.findAll().size();

        // Create the Agency
        AgencyDTO agencyDTO = agencyMapper.agencyToAgencyDTO(agency);

        restAgencyMockMvc.perform(post("/api/agencies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
                .andExpect(status().isCreated());

        // Validate the Agency in the database
        List<Agency> agencies = agencyRepository.findAll();
        assertThat(agencies).hasSize(databaseSizeBeforeCreate + 1);
        Agency testAgency = agencies.get(agencies.size() - 1);
        assertThat(testAgency.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAgency.getOrganisationType()).isEqualTo(DEFAULT_ORGANISATION_TYPE);
        assertThat(testAgency.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testAgency.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testAgency.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = agencyRepository.findAll().size();
        // set the field null
        agency.setName(null);

        // Create the Agency, which fails.
        AgencyDTO agencyDTO = agencyMapper.agencyToAgencyDTO(agency);

        restAgencyMockMvc.perform(post("/api/agencies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
                .andExpect(status().isBadRequest());

        List<Agency> agencies = agencyRepository.findAll();
        assertThat(agencies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgencies() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencies
        restAgencyMockMvc.perform(get("/api/agencies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(agency.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].organisationType").value(hasItem(DEFAULT_ORGANISATION_TYPE.toString())))
                .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
                .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }

    @Test
    @Transactional
    public void getAgency() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get the agency
        restAgencyMockMvc.perform(get("/api/agencies/{id}", agency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agency.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.organisationType").value(DEFAULT_ORGANISATION_TYPE.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)));
    }

    @Test
    @Transactional
    public void getNonExistingAgency() throws Exception {
        // Get the agency
        restAgencyMockMvc.perform(get("/api/agencies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgency() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);
        int databaseSizeBeforeUpdate = agencyRepository.findAll().size();

        // Update the agency
        Agency updatedAgency = agencyRepository.findOne(agency.getId());
        updatedAgency
                .name(UPDATED_NAME)
                .organisationType(UPDATED_ORGANISATION_TYPE)
                .contact(UPDATED_CONTACT)
                .logo(UPDATED_LOGO)
                .logoContentType(UPDATED_LOGO_CONTENT_TYPE);
        AgencyDTO agencyDTO = agencyMapper.agencyToAgencyDTO(updatedAgency);

        restAgencyMockMvc.perform(put("/api/agencies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
                .andExpect(status().isOk());

        // Validate the Agency in the database
        List<Agency> agencies = agencyRepository.findAll();
        assertThat(agencies).hasSize(databaseSizeBeforeUpdate);
        Agency testAgency = agencies.get(agencies.size() - 1);
        assertThat(testAgency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAgency.getOrganisationType()).isEqualTo(UPDATED_ORGANISATION_TYPE);
        assertThat(testAgency.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testAgency.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testAgency.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteAgency() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);
        int databaseSizeBeforeDelete = agencyRepository.findAll().size();

        // Get the agency
        restAgencyMockMvc.perform(delete("/api/agencies/{id}", agency.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Agency> agencies = agencyRepository.findAll();
        assertThat(agencies).hasSize(databaseSizeBeforeDelete - 1);
    }
}
