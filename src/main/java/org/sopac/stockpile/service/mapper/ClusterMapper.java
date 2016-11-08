package org.sopac.stockpile.service.mapper;

import org.sopac.stockpile.domain.*;
import org.sopac.stockpile.service.dto.ClusterDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Cluster and its DTO ClusterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClusterMapper {

    ClusterDTO clusterToClusterDTO(Cluster cluster);

    List<ClusterDTO> clustersToClusterDTOs(List<Cluster> clusters);

    @Mapping(target = "clusters", ignore = true)
    Cluster clusterDTOToCluster(ClusterDTO clusterDTO);

    List<Cluster> clusterDTOsToClusters(List<ClusterDTO> clusterDTOs);
}
