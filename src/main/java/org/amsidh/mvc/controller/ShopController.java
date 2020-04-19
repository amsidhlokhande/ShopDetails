package org.amsidh.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.amsidh.mvc.dto.ShopDto;
import org.amsidh.mvc.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class ShopController {

    @Autowired
    private ShopService shopService;

    public ShopController() {
        log.info("Loading ShopController!!!!");

    }

    @GetMapping(value = "/shops", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Collection<EntityModel<ShopDto>> getAllShops() {
        Collection<ShopDto> shops = shopService.getAllShops();

        Collection<EntityModel<ShopDto>> resources = new ArrayList<>();
        for (ShopDto shopDto : shops) {
            if (null != shopDto)
                resources.add(getShopDtoResource(shopDto));
        }

        return resources;
    }

    private EntityModel<ShopDto> getShopDtoResource(ShopDto shopDto) {
        EntityModel<ShopDto> resource = new EntityModel<>(shopDto);
        // Link to Shop
        resource.add(linkTo(methodOn(ShopController.class).getShop(shopDto.getShopId())).withSelfRel());
        resource.add(linkTo(methodOn(AddressController.class).getAddress(shopDto.getAddressId())).withRel("AddressDetail"));
        return resource;
    }

    @GetMapping(value = "/shops/{shopId}", produces = APPLICATION_JSON_VALUE)
    public EntityModel<ShopDto> getShop(@PathVariable(value = "shopId") Integer shopId) {

        ShopDto shopDto = shopService.getShopDetails(shopId);
        if (null != shopDto) {
            EntityModel<ShopDto> shopDtoResource = getShopDtoResource(shopDto);
            shopDtoResource.add(linkTo(methodOn(ShopController.class).getAllShops()).withRel("AllShops"));
            return shopDtoResource;
        } else {
            return null;
        }
    }


    @PostMapping(value = "/shops", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Collection<EntityModel<ShopDto>> saveShop(@RequestBody ShopDto shopDto) {
        shopService.saveShopDetails(shopDto);
        Collection<ShopDto> shops = shopService.getAllShops();

        Collection<EntityModel<ShopDto>> resources = new ArrayList<>();
        for (ShopDto shop : shops) {
            if (null != shop)
                resources.add(getShopDtoResource(shop));
        }

        return resources;
    }

}

