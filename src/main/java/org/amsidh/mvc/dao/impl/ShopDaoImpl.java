package org.amsidh.mvc.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.amsidh.mvc.dao.ShopDao;
import org.amsidh.mvc.dom.ShopDom;
import org.amsidh.mvc.dto.ShopDto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllBytes;
import static org.amsidh.mvc.util.ShopUtility.getShopDom;

/**
 * @author amsidhlokhande
 */
@Repository
@Slf4j
public class ShopDaoImpl implements ShopDao {


    private static final List<ShopDom> shops = new ArrayList();

    static {
        try {
            Resource resource = new ClassPathResource("META-INF/json-data/shop-json.json");
            byte[] jsonData = readAllBytes(resource.getFile().toPath());
            ObjectMapper objectMapper = new ObjectMapper();

            List<ShopDto> list = objectMapper.readValue(jsonData, new TypeReference<List<ShopDto>>() {
            });

            for (ShopDto shopDto : list) {
                shops.add(getShopDom(shopDto));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public ShopDaoImpl() {
        log.info("Loading ShopDaoImpl!!!!");
    }

    @Override
    public void createShop(ShopDom shopDom) {
        shops.add(shopDom);
    }

    @Override
    public void updateShop(ShopDom shopDom) {
        for (ShopDom shop : shops) {
            if (shop.getShopId().equals(shopDom.getShopId())) {
                shop.setShopName(shopDom.getShopName());
            }
        }
    }

    @Override
    public ShopDom getShopDomByShopId(Integer shopId) {
        ShopDom shopDom = null;

        for (ShopDom shop : shops) {
            if (shopId.equals(shop.getShopId())) {
                shopDom = shop;
            }
        }
        return shopDom;

    }

    @Override
    public void deleteShopByShopId(Integer shopId) {
        for (ShopDom shopDom : shops) {
            if (shopId.equals(shopDom.getShopId())) {
                shops.remove(shopDom);
            }
        }

    }

    @Override
    public List<ShopDom> getAllShops() {
        return shops;
    }

}
