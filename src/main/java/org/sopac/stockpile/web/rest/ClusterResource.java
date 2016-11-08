package org.sopac.stockpile.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.sopac.stockpile.domain.Cluster;

import org.sopac.stockpile.repository.ClusterRepository;
import org.sopac.stockpile.web.rest.util.HeaderUtil;
import org.sopac.stockpile.service.dto.ClusterDTO;
import org.sopac.stockpile.service.mapper.ClusterMapper;
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
 * REST controller for managing Cluster.
 */
@RestController
@RequestMapping("/api")
public class ClusterResource {

    private final Logger log = LoggerFactory.getLogger(ClusterResource.class);
        
    @Inject
    private ClusterRepository clusterRepository;

    @Inject
    private ClusterMapper clusterMapper;

    /**
     * POST  /clusters : Create a new cluster.
     *
     * @param clusterDTO the clusterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clusterDTO, or with status 400 (Bad Request) if the cluster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clusters")
    @Timed
    public ResponseEntity<ClusterDTO> createCluster(@Valid @RequestBody ClusterDTO clusterDTO) throws URISyntaxException {
        log.debug("REST request to save Cluster : {}", clusterDTO);
        if (clusterDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cluster", "idexists", "A new cluster cannot already have an ID")).body(null);
        }
        Cluster cluster = clusterMapper.clusterDTOToCluster(clusterDTO);
        cluster = clusterRepository.save(cluster);
        ClusterDTO result = clusterMapper.clusterToClusterDTO(cluster);
        return ResponseEntity.created(new URI("/api/clusters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cluster", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clusters : Updates an existing cluster.
     *
     * @param clusterDTO the clusterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clusterDTO,
     * or with status 400 (Bad Request) if the clusterDTO is not valid,
     * or with status 500 (Internal Server Error) if the clusterDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clusters")
    @Timed
    public ResponseEntity<ClusterDTO> updateCluster(@Valid @RequestBody ClusterDTO clusterDTO) throws URISyntaxException {
        log.debug("REST request to update Cluster : {}", clusterDTO);
        if (clusterDTO.getId() == null) {
            return createCluster(clusterDTO);
        }
        Cluster cluster = clusterMapper.clusterDTOToCluster(clusterDTO);
        cluster = clusterRepository.save(cluster);
        ClusterDTO result = clusterMapper.clusterToClusterDTO(cluster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cluster", clusterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clusters : get all the clusters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clusters in body
     */
    @GetMapping("/clusters")
    @Timed
    public List<ClusterDTO> getAllClusters() {
        log.debug("REST request to get all Clusters");
        List<Cluster> clusters = clusterRepository.findAll();
        return clusterMapper.clustersToClusterDTOs(clusters);
    }

    /**
     * GET  /clusters/:id : get the "id" cluster.
     *
     * @param id the id of the clusterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clusterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clusters/{id}")
    @Timed
    public ResponseEntity<ClusterDTO> getCluster(@PathVariable Long id) {
        log.debug("REST request to get Cluster : {}", id);
        Cluster cluster = clusterRepository.findOne(id);
        ClusterDTO clusterDTO = clusterMapper.clusterToClusterDTO(cluster);
        return Optional.ofNullable(clusterDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /clusters/:id : delete the "id" cluster.
     *
     * @param id the id of the clusterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clusters/{id}")
    @Timed
    public ResponseEntity<Void> deleteCluster(@PathVariable Long id) {
        log.debug("REST request to delete Cluster : {}", id);
        clusterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cluster", id.toString())).build();
    }

}
