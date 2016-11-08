package org.sopac.stockpile.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.sopac.stockpile.domain.Agency;

import org.sopac.stockpile.repository.AgencyRepository;
import org.sopac.stockpile.web.rest.util.HeaderUtil;
import org.sopac.stockpile.service.dto.AgencyDTO;
import org.sopac.stockpile.service.mapper.AgencyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Agency.
 */
@RestController
@RequestMapping("/api")
public class AgencyResource {

    private final Logger log = LoggerFactory.getLogger(AgencyResource.class);
        
    @Inject
    private AgencyRepository agencyRepository;

    @Inject
    private AgencyMapper agencyMapper;

    /**
     * POST  /agencies : Create a new agency.
     *
     * @param agencyDTO the agencyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agencyDTO, or with status 400 (Bad Request) if the agency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agencies")
    @Timed
    public ResponseEntity<AgencyDTO> createAgency(@Valid @RequestBody AgencyDTO agencyDTO) throws URISyntaxException {
        log.debug("REST request to save Agency : {}", agencyDTO);
        if (agencyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("agency", "idexists", "A new agency cannot already have an ID")).body(null);
        }
        Agency agency = agencyMapper.agencyDTOToAgency(agencyDTO);
        agency = agencyRepository.save(agency);
        AgencyDTO result = agencyMapper.agencyToAgencyDTO(agency);
        return ResponseEntity.created(new URI("/api/agencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("agency", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agencies : Updates an existing agency.
     *
     * @param agencyDTO the agencyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agencyDTO,
     * or with status 400 (Bad Request) if the agencyDTO is not valid,
     * or with status 500 (Internal Server Error) if the agencyDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agencies")
    @Timed
    public ResponseEntity<AgencyDTO> updateAgency(@Valid @RequestBody AgencyDTO agencyDTO) throws URISyntaxException {
        log.debug("REST request to update Agency : {}", agencyDTO);
        if (agencyDTO.getId() == null) {
            return createAgency(agencyDTO);
        }
        Agency agency = agencyMapper.agencyDTOToAgency(agencyDTO);
        agency = agencyRepository.save(agency);
        AgencyDTO result = agencyMapper.agencyToAgencyDTO(agency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("agency", agencyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agencies : get all the agencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of agencies in body
     */
    @GetMapping("/agencies")
    @Timed
    public List<AgencyDTO> getAllAgencies() {
        log.debug("REST request to get all Agencies");
        List<Agency> agencies = agencyRepository.findAll();
        return agencyMapper.agenciesToAgencyDTOs(agencies);
    }

    /**
     * GET  /agencies/:id : get the "id" agency.
     *
     * @param id the id of the agencyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agencyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/agencies/{id}")
    @Timed
    public ResponseEntity<AgencyDTO> getAgency(@PathVariable Long id) {
        log.debug("REST request to get Agency : {}", id);
        Agency agency = agencyRepository.findOne(id);
        AgencyDTO agencyDTO = agencyMapper.agencyToAgencyDTO(agency);
        return Optional.ofNullable(agencyDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /agencies/:id : delete the "id" agency.
     *
     * @param id the id of the agencyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agencies/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgency(@PathVariable Long id) {
        log.debug("REST request to delete Agency : {}", id);
        agencyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("agency", id.toString())).build();
    }

}
