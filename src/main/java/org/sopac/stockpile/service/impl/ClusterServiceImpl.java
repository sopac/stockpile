package org.sopac.stockpile.service.impl;

import org.sopac.stockpile.service.ClusterService;
import org.sopac.stockpile.domain.Cluster;
import org.sopac.stockpile.repository.ClusterRepository;
import org.sopac.stockpile.service.dto.ClusterDTO;
import org.sopac.stockpile.service.mapper.ClusterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Cluster.
 */
@Service
@Transactional
public class ClusterServiceImpl implements ClusterService{

    private final Logger log = LoggerFactory.getLogger(ClusterServiceImpl.class);
    
    @Inject
    private ClusterRepository clusterRepository;

    @Inject
    private ClusterMapper clusterMapper;

    /**
     * Save a cluster.
     *
     * @param clusterDTO the entity to save
     * @return the persisted entity
     */
    public ClusterDTO save(ClusterDTO clusterDTO) {
        log.debug("Request to save Cluster : {}", clusterDTO);
        Cluster cluster = clusterMapper.clusterDTOToCluster(clusterDTO);
        cluster = clusterRepository.save(cluster);
        ClusterDTO result = clusterMapper.clusterToClusterDTO(cluster);
        return result;
    }

    /**
     *  Get all the clusters.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ClusterDTO> findAll() {
        log.debug("Request to get all Clusters");
        List<ClusterDTO> result = clusterRepository.findAll().stream()
            .map(clusterMapper::clusterToClusterDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one cluster by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ClusterDTO findOne(Long id) {
        log.debug("Request to get Cluster : {}", id);
        Cluster cluster = clusterRepository.findOne(id);
        ClusterDTO clusterDTO = clusterMapper.clusterToClusterDTO(cluster);
        return clusterDTO;
    }

    /**
     *  Delete the  cluster by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cluster : {}", id);
        clusterRepository.delete(id);
    }
}
