package org.amsidh.mvc.dom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDom implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer addressId;
    private String location;
    private Double longitude;
    private Double latitude;
    private Integer shopId;

    public AddressDom(Integer addressId, String location) {
        this.addressId = addressId;
        this.location = location;
    }

}
