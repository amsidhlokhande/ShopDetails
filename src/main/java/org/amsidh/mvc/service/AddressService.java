package org.amsidh.mvc.service;

import org.amsidh.mvc.dto.AddressDto;

import java.util.List;

public interface AddressService {

    void saveAddress(AddressDto addressDto);

    void updateAddress(AddressDto addressDto);

    AddressDto getAddress(Integer addressId);

    void deleteAddress(Integer addressId);

    List<AddressDto> getAllAddresses();


    List<AddressDto> getAddressesByPostCode(Integer postCode);

}
