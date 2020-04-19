/**
 *
 */
package org.amsidh.mvc.dao;

import org.amsidh.mvc.dom.ShopDom;

import java.util.List;

/**
 * @author amsidhlokhande
 *
 */
public interface ShopDao {

    void createShop(ShopDom shopDom);

    void updateShop(ShopDom shopDom);

    ShopDom getShopDomByShopId(Integer shopId);

    void deleteShopByShopId(Integer shopId);

    List<ShopDom> getAllShops();
}
