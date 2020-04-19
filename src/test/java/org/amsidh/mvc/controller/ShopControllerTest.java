/**
 *
 */
package org.amsidh.mvc.controller;

import org.amsidh.mvc.ShopSpringApplication;
import org.amsidh.mvc.dto.ShopDto;
import org.amsidh.mvc.service.impl.ShopServiceImpl;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author amsidhlokhande
 *
 */

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {ShopController.class})
@ContextConfiguration(classes = {ShopSpringApplication.class})
public class ShopControllerTest {
    public static final String SHOPS_URL = "/shops";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShopServiceImpl shopService;

    @Test
    public void testgetAllShops() throws Exception {
        final List<ShopDto> shops = new ArrayList<>();
        shops.add(new ShopDto(1, "ShopName1", 1));
        shops.add(new ShopDto(2, "ShopName2", 2));
        shops.add(new ShopDto(3, "ShopName3", 3));
        when(shopService.getAllShops()).thenReturn(shops);

        RequestBuilder requestBuilder = get(SHOPS_URL).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expectedJson = "[{shopId:1,shopName:\"ShopName1\",addressId:1},{shopId:2,shopName:\"ShopName2\",addressId:2},{shopId:3,shopName:\"ShopName3\",addressId:3}]";

        assertEquals(expectedJson, result.getResponse().getContentAsString(), false);

    }

    @Test
    public void testgetAllShopsWithNoShop() throws Exception {
        final List<ShopDto> shops = new ArrayList<>();
        when(shopService.getAllShops()).thenReturn(shops);

        RequestBuilder requestBuilder = get(SHOPS_URL).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertEquals("[]", result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetShopDtoResource() throws Exception {

        RequestBuilder requestBuilder = get(SHOPS_URL + "/{shopId}", new Object[]{1})
                .contentType(APPLICATION_JSON).accept(APPLICATION_JSON);

        final ShopDto shop = new ShopDto(1, "shopName1");
        when(shopService.getShopDetails(anyInt())).thenReturn(shop);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expectedJson = "{shopId:1,shopName:\"shopName1\"}";

        assertEquals(expectedJson, result.getResponse().getContentAsString(), false);

    }

    @Test
    public void testGetShopDtoResourceWitheNullShop() throws Exception {

        RequestBuilder requestBuilder = get(SHOPS_URL + "/{shopId}", new Object[]{1})
                .contentType(APPLICATION_JSON).accept(APPLICATION_JSON);

        when(shopService.getShopDetails(anyInt())).thenReturn(null);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals("", result.getResponse().getContentAsString());
    }

    @Test
    public void testSaveShop() throws Exception {
        ShopDto shop = new ShopDto(1, "ShopName1", 1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("shop", shop);

        RequestBuilder requestBuilder = post(SHOPS_URL).contentType(APPLICATION_JSON)
                .content(shop.toString()).accept(APPLICATION_JSON);

        doNothing().when(shopService).saveShopDetails(any(ShopDto.class));
        List<ShopDto> shops = new ArrayList<>();
        shops.add(shop);
        when(shopService.getAllShops()).thenReturn(shops);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expectedJson = "[{shopId:1,shopName:\"ShopName1\",addressId:1}]";
        //JSONAssert.assertEquals(expectedJson, result.getResponse().getContentAsString(), false);

    }
}
