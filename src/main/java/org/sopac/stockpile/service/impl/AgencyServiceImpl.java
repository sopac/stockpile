package org.sopac.stockpile.service.impl;

import org.sopac.stockpile.service.AgencyService;
import org.sopac.stockpile.domain.Agency;
import org.sopac.stockpile.repository.AgencyRepository;
import org.sopac.stockpile.service.dto.AgencyDTO;
import org.sopac.stockpile.service.mapper.AgencyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Agency.
 */
@Service
@Transactional
public class AgencyServiceImpl implements AgencyService{

    private final Logger log = LoggerFactory.getLogger(AgencyServiceImpl.class);
    
    @Inject
    private AgencyRepository agencyRepository;

    @Inject
    private AgencyMapper agencyMapper;

    /**
     * Save a agency.
     *
     * @param agencyDTO the entity to save
     * @return the persisted entity
     */
    public AgencyDTO save(AgencyDTO agencyDTO) {
        log.debug("Request to save Agency : {}", agencyDTO);
        Agency agency = agencyMapper.agencyDTOToAgency(agencyDTO);
        agency = agencyRepository.save(agency);
        AgencyDTO result = agencyMapper.agencyToAgencyDTO(agency);
        return result;
    }

    /**
     *  Get all the agencies.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AgencyDTO> findAll() {
        log.debug("Request to get all Agencies");
        List<AgencyDTO> result = agencyRepository.findAll().stream()
            .map(agencyMapper::agencyToAgencyDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one agency by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AgencyDTO findOne(Long id) {
        log.debug("Request to get Agency : {}", id);
        Agency agency = agencyRepository.findOne(id);
        AgencyDTO agencyDTO = agencyMapper.agencyToAgencyDTO(agency);
        return agencyDTO;
    }

    /**
     *  Delete the  agency by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Agency : {}", id);
        agencyRepository.delete(id);
    }
}
