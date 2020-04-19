package org.amsidh.mvc.dom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDom implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer shopId;
    private String shopName;
    private Integer addressId;

}
