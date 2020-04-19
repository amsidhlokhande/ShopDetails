package org.amsidh.mvc.dao;

import org.amsidh.mvc.dom.AddressDom;

import java.util.List;

public interface AddressDao {

    void createAddress(AddressDom addressDom);

    void updateAddress(AddressDom addressDom);

    AddressDom getAddressDomByAddressId(Integer addressId);

    void deleteAddressByAddressId(Integer addressId);

    List<AddressDom> getAllAddresss();
}
