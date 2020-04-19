package org.amsidh.mvc.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.amsidh.mvc.dao.AddressDao;
import org.amsidh.mvc.dom.AddressDom;
import org.amsidh.mvc.dto.AddressDto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllBytes;
import static org.amsidh.mvc.util.ShopUtility.getAddressDom;

@Repository
@Slf4j
public class AddressDaoImpl implements AddressDao {

    private static final List<AddressDom> addresses = new ArrayList();

    static {
        try {
            Resource resource = new ClassPathResource("META-INF/json-data/address-json.json");
            byte[] jsonData = readAllBytes(resource.getFile().toPath());
            ObjectMapper objectMapper = new ObjectMapper();

            List<AddressDto> list = objectMapper.readValue(jsonData, new TypeReference<List<AddressDto>>() {
            });

            for (AddressDto addressDto : list) {
                addresses.add(getAddressDom(addressDto));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void createAddress(AddressDom addressDom) {
        addresses.add(addressDom);
    }

    @Override
    public void updateAddress(AddressDom addressDom) {
        for (AddressDom address : addresses) {
            if (addressDom.getAddressId().equals(address.getAddressId())) {
                address.setLocation(addressDom.getLocation());
                address.setLongitude(addressDom.getLongitude());
                address.setLatitude(addressDom.getLatitude());
                address.setShopId(addressDom.getShopId());
            }
        }
    }

    @Override
    public AddressDom getAddressDomByAddressId(Integer addressId) {
        AddressDom addressDom = null;
        for (AddressDom address : addresses) {
            if (addressId.equals(address.getAddressId())) {
                addressDom = address;
            }
        }
        return addressDom;
    }

    @Override
    public void deleteAddressByAddressId(Integer addressId) {
        for (AddressDom address : addresses) {
            if (addressId.equals(address.getAddressId())) {
                addresses.remove(address);
            }
        }
    }

    @Override
    public List<AddressDom> getAllAddresss() {
        return addresses;
    }

}
