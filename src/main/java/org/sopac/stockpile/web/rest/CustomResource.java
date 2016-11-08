package org.sopac.stockpile.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopac.stockpile.domain.Item;
import org.sopac.stockpile.repository.ItemRepository;
import org.sopac.stockpile.service.dto.ItemDTO;
import org.sopac.stockpile.service.mapper.ItemMapper;
import org.sopac.stockpile.web.rest.util.HeaderUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Item.
 */
@RestController
@RequestMapping("/custom")
public class CustomResource {

    private final Logger log = LoggerFactory.getLogger(CustomResource.class);

    @Inject
    private ItemRepository itemRepository;

    @Inject
    private ItemMapper itemMapper;


    @GetMapping("/")
    @ResponseBody
    @Timed
    public String index() {
        return itemRepository.findOne(9l).getName() + " ";
    }

}
