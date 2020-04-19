package org.amsidh.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private Integer addressId;
    private String location;
    private Double longitude;
    private Double latitude;

    private Integer shopId;

    public AddressDto(Integer addressId, String location, Integer shopId) {
        this.addressId = addressId;
        this.location = location;
        this.shopId = shopId;
    }

    public AddressDto(Integer addressId, String location) {
        this.addressId = addressId;
        this.location = location;
    }
}