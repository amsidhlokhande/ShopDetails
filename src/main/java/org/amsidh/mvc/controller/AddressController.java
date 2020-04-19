package org.amsidh.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.amsidh.mvc.dto.AddressDto;
import org.amsidh.mvc.service.AddressService;
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
public class AddressController {


    @Autowired
    private AddressService addressService;

    public AddressController() {
        log.info("Loading AddressController!!!!");

    }

    @GetMapping(value = "/addresses", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Collection<EntityModel<AddressDto>> getAllAddresses() {
        Collection<AddressDto> addresses = addressService.getAllAddresses();

        Collection<EntityModel<AddressDto>> resources = new ArrayList<>();
        for (AddressDto addressDto : addresses) {
            if (null != addressDto)
                resources.add(getAddressDtoResource(addressDto));
        }

        return resources;
    }

    private EntityModel<AddressDto> getAddressDtoResource(AddressDto addressDto) {
        EntityModel<AddressDto> resource = new EntityModel<>(addressDto);
        // Link to Shop
        resource.add(linkTo(methodOn(AddressController.class).getAddress(addressDto.getAddressId())).withSelfRel());
        resource.add(linkTo(methodOn(ShopController.class).getShop(addressDto.getShopId())).withRel("Shop"));
        return resource;
    }

    @GetMapping(value = "/addresses/{addressId}", produces = APPLICATION_JSON_VALUE)
    public EntityModel<AddressDto> getAddress(@PathVariable(value = "addressId") Integer addressId) {

        AddressDto addressDto = addressService.getAddress(addressId);
        if (null != addressDto) {
            EntityModel<AddressDto> addressDtoResource = getAddressDtoResource(addressDto);
            addressDtoResource.add(linkTo(methodOn(AddressController.class).getAllAddresses()).withRel("Addresses"));
            return addressDtoResource;
        } else {
            return null;
        }
    }


    @GetMapping(value = "/addresses/pincode/{postCode}", produces = APPLICATION_JSON_VALUE)
    public Collection<EntityModel<AddressDto>> getShopsNearToPostCode(@PathVariable(value = "postCode") Integer postCode) {

        Collection<AddressDto> addresses = addressService.getAddressesByPostCode(postCode);
        Collection<EntityModel<AddressDto>> resources = new ArrayList<>();
        for (AddressDto address : addresses) {
            if (null != address) {
                EntityModel<AddressDto> addressDtoResource = getAddressDtoResource(address);
                addressDtoResource.add(linkTo(methodOn(AddressController.class).getAllAddresses()).withRel("Addresses"));
                resources.add(addressDtoResource);
            }
        }

        return resources;
    }


    @PostMapping(value = "/addresses", produces = APPLICATION_JSON_VALUE)
    public Collection<EntityModel<AddressDto>> saveAddress(@RequestBody AddressDto addressDto) {

        addressService.saveAddress(addressDto);

        Collection<AddressDto> addresses = addressService.getAllAddresses();
        Collection<EntityModel<AddressDto>> resources = new ArrayList<>();
        for (AddressDto address : addresses) {
            if (null != address)
                resources.add(getAddressDtoResource(address));
        }

        return resources;
    }
}
