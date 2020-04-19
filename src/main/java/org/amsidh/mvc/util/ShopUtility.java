/**
 *
 */
package org.amsidh.mvc.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.amsidh.mvc.dom.AddressDom;
import org.amsidh.mvc.dom.ShopDom;
import org.amsidh.mvc.dto.AddressDto;
import org.amsidh.mvc.dto.ShopDto;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.net.URLEncoder.encode;

/**
 * @author amsidhlokhande
 *
 */
@Slf4j
public class ShopUtility {

    private ShopUtility() {
    }

    public static ShopDto getShopDto(ShopDom shopDom) {

        return null == shopDom ? null : new ShopDto(shopDom.getShopId(), shopDom.getShopName(), shopDom.getAddressId());

    }

    public static AddressDto getAddressDto(AddressDom addressDom) {
        if (null != addressDom) {
            AddressDto addressDto = new AddressDto(addressDom.getAddressId(), addressDom.getLocation());

            if (null != addressDom.getLongitude() && null != addressDom.getLatitude()) {
                addressDto.setLongitude(addressDom.getLongitude());
                addressDto.setLatitude(addressDom.getLatitude());
            } else {
                Map<String, Double> geoCodingForAddress = geoCodingForAddress(addressDom.getLocation());
                addressDto.setLongitude(geoCodingForAddress.get("lng"));
                addressDto.setLatitude(geoCodingForAddress.get("lat"));
            }

            if (null != addressDom.getShopId()) {
                addressDto.setShopId(addressDom.getShopId());
            }

            return addressDto;
        } else {
            return null;
        }
    }

    public static ShopDom getShopDom(ShopDto shopDto) {

        return shopDto == null ? null : new ShopDom(shopDto.getShopId(), shopDto.getShopName(), shopDto.getAddressId());
    }

    public static AddressDom getAddressDom(AddressDto addressDto) {

        AddressDom addressDom = new AddressDom(addressDto.getAddressId(), addressDto.getLocation());

        if (null != addressDto.getLongitude() || null != addressDto.getLatitude()) {
            addressDom.setLongitude(addressDto.getLongitude());
            addressDom.setLatitude(addressDto.getLatitude());
        } else {
            Map<String, Double> geoCodingForAddress = geoCodingForAddress(addressDto.getLocation());
            addressDom.setLongitude(geoCodingForAddress.get("lng"));
            addressDom.setLatitude(geoCodingForAddress.get("lat"));
        }

        if (null != addressDto.getShopId()) {
            addressDom.setShopId(addressDto.getShopId());
        }

        return addressDom;
    }

    public static List<ShopDto> getAllShopDto(List<ShopDom> shopDoms) {
        List<ShopDto> shopDtos = new ArrayList();

        for (ShopDom shopDom : shopDoms) {
            shopDtos.add(new ShopDto(shopDom.getShopId(), shopDom.getShopName(), shopDom.getAddressId()));
        }
        return shopDtos;

    }

    public static List<ShopDom> getAllShopDoms(List<ShopDto> shopDtos) {

        List<ShopDom> shopDoms = new ArrayList();
        for (ShopDto shopDto : shopDtos) {
            shopDoms.add(new ShopDom(shopDto.getShopId(), shopDto.getShopName(), shopDto.getAddressId()));
        }
        return shopDoms;
    }

    public static final Map<String, Double> geoCodingForAddress(String address) {
        Map<String, Double> map = new HashMap<>();

        if (address != null) {

            try {
                URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?address='"
                                  + encode(address, "UTF-8") + "'&sensor=false");

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode node = objectMapper.readValue(url, JsonNode.class);
                JsonNode location = node.get("results").get(0).get("geometry").get("location");
                map.put("lng", location.get("lng").asDouble());
                map.put("lat", location.get("lat").asDouble());
            } catch (Exception ex) {

                log.error(
                        "Internet Connection unavailable or Geographical Information is not found for given pin code"
                        + ex.getLocalizedMessage());
            }

        }
        return map;

    }
}
