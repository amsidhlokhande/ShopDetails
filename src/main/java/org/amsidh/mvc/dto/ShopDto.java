package org.amsidh.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDto {
    private Integer shopId;
    private String shopName;
    private Integer addressId;

    public ShopDto(Integer shopId, String shopName) {
        this.shopId = shopId;
        this.shopName = shopName;
    }
}
