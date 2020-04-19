package org.amsidh.mvc.service.impl;

import lombok.AllArgsConstructor;
import org.amsidh.mvc.dao.ShopDao;
import org.amsidh.mvc.dto.ShopDto;
import org.amsidh.mvc.service.ShopService;
import org.amsidh.mvc.util.ShopUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.amsidh.mvc.util.ShopUtility.getAllShopDto;
import static org.amsidh.mvc.util.ShopUtility.getShopDom;

/**
 * @author amsidhlokhande
 */
@AllArgsConstructor
@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    private final ShopDao shopDao;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveShopDetails(ShopDto shopDto) {
        shopDao.createShop(getShopDom(shopDto));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateShopDetails(ShopDto shopDto) {
        shopDao.updateShop(getShopDom(shopDto));
    }

    @Override
    public ShopDto getShopDetails(Integer shopId) {
        return ShopUtility.getShopDto(shopDao.getShopDomByShopId(shopId));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteShopDetails(Integer shopId) {
        shopDao.deleteShopByShopId(shopId);
    }

    @Override
    public List<ShopDto> getAllShops() {
        return getAllShopDto(shopDao.getAllShops());
    }
}
