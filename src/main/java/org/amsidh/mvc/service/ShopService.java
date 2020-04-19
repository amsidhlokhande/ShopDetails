package org.amsidh.mvc.service;

import org.amsidh.mvc.dto.ShopDto;

import java.util.List;

public interface ShopService {

    void saveShopDetails(ShopDto shop);

    void updateShopDetails(ShopDto shop);

    ShopDto getShopDetails(Integer shopId);

    void deleteShopDetails(Integer shopId);

    List<ShopDto> getAllShops();

}
