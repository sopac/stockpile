package org.sopac.stockpile.service.mapper;

import org.sopac.stockpile.domain.*;
import org.sopac.stockpile.service.dto.InventoryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Inventory and its DTO InventoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InventoryMapper {

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.name", target = "itemName")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.countryName", target = "countryCountryName")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.location", target = "locationLocation")
    @Mapping(source = "agency.id", target = "agencyId")
    @Mapping(source = "agency.name", target = "agencyName")
    @Mapping(source = "cluster.id", target = "clusterId")
    @Mapping(source = "cluster.name", target = "clusterName")
    InventoryDTO inventoryToInventoryDTO(Inventory inventory);

    List<InventoryDTO> inventoriesToInventoryDTOs(List<Inventory> inventories);

    @Mapping(source = "itemId", target = "item")
    @Mapping(source = "countryId", target = "country")
    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "agencyId", target = "agency")
    @Mapping(source = "clusterId", target = "cluster")
    Inventory inventoryDTOToInventory(InventoryDTO inventoryDTO);

    List<Inventory> inventoryDTOsToInventories(List<InventoryDTO> inventoryDTOs);

    default Item itemFromId(Long id) {
        if (id == null) {
            return null;
        }
        Item item = new Item();
        item.setId(id);
        return item;
    }

    default Country countryFromId(Long id) {
        if (id == null) {
            return null;
        }
        Country country = new Country();
        country.setId(id);
        return country;
    }

    default Location locationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }

    default Agency agencyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Agency agency = new Agency();
        agency.setId(id);
        return agency;
    }

    default Cluster clusterFromId(Long id) {
        if (id == null) {
            return null;
        }
        Cluster cluster = new Cluster();
        cluster.setId(id);
        return cluster;
    }
}
