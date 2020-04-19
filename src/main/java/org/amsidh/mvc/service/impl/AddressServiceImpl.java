package org.amsidh.mvc.service.impl;

import lombok.AllArgsConstructor;
import org.amsidh.mvc.dao.AddressDao;
import org.amsidh.mvc.dom.AddressDom;
import org.amsidh.mvc.dto.AddressDto;
import org.amsidh.mvc.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.round;
import static org.amsidh.mvc.util.ShopUtility.geoCodingForAddress;
import static org.amsidh.mvc.util.ShopUtility.getAddressDom;
import static org.amsidh.mvc.util.ShopUtility.getAddressDto;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    @Override
    public void saveAddress(AddressDto addressDto) {
        addressDao.createAddress(getAddressDom(addressDto));
    }

    @Override
    public void updateAddress(AddressDto addressDto) {
        addressDao.updateAddress(getAddressDom(addressDto));

    }

    @Override
    public AddressDto getAddress(Integer addressId) {

        return getAddressDto(addressDao.getAddressDomByAddressId(addressId));
    }

    @Override
    public void deleteAddress(Integer addressId) {
        addressDao.deleteAddressByAddressId(addressId);

    }

    @Override
    public List<AddressDto> getAllAddresses() {

        List<AddressDto> addressDtos = new ArrayList();
        List<AddressDom> addressDoms = addressDao.getAllAddresss();
        if (null != addressDoms && !addressDoms.isEmpty()) {
            for (AddressDom addressDom : addressDoms) {
                addressDtos.add(getAddressDto(addressDom));
            }
        }

        return addressDtos;
    }

    @Override
    public List<AddressDto> getAddressesByPostCode(Integer postCode) {

        List<AddressDto> addressDtos = new ArrayList();
        List<AddressDom> addressDoms = addressDao.getAllAddresss();
        if (null != addressDoms && !addressDoms.isEmpty()) {
            Map<String, Double> lngAndLat = geoCodingForAddress(postCode.toString());
            for (AddressDom addressDom : addressDoms) {
                if (null != addressDom.getLatitude() && null != addressDom.getLongitude()) {
                    if (round(addressDom.getLongitude()) == round(lngAndLat.get("lng")) && round(addressDom.getLatitude()) == round(lngAndLat.get("lat"))) {
                        addressDtos.add(getAddressDto(addressDom));
                    }
                }
            }
        }

        return addressDtos;
    }
}
