package org.sopac.stockpile.service.mapper;

import org.sopac.stockpile.domain.*;
import org.sopac.stockpile.service.dto.AgencyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Agency and its DTO AgencyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgencyMapper {

    AgencyDTO agencyToAgencyDTO(Agency agency);

    List<AgencyDTO> agenciesToAgencyDTOs(List<Agency> agencies);

    @Mapping(target = "agencies", ignore = true)
    Agency agencyDTOToAgency(AgencyDTO agencyDTO);

    List<Agency> agencyDTOsToAgencies(List<AgencyDTO> agencyDTOs);
}
