package org.amsidh.mvc.controller;

import org.amsidh.mvc.ShopSpringApplication;
import org.amsidh.mvc.dto.AddressDto;
import org.amsidh.mvc.service.impl.AddressServiceImpl;
import org.json.JSONObject;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {AddressController.class})
@ContextConfiguration(classes = {ShopSpringApplication.class})
public class AddressControllerTest {

    public static final String BASE_ADDRESS_URL = "/addresses";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressServiceImpl addressService;

    @Test
    public void testGetAllAddresses() throws Exception {

        RequestBuilder requestBuilder = get(BASE_ADDRESS_URL).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        List<AddressDto> addresses = new ArrayList<>();
        addresses.add(new AddressDto(1, "Pune1", 1));
        addresses.add(new AddressDto(2, "Pune2", 2));
        addresses.add(new AddressDto(3, "Pune3", 3));

        when(addressService.getAllAddresses()).thenReturn(addresses);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = "[{addressId:1,location:\"Pune1\",shopId:1},{addressId:2,location:\"Pune2\",shopId:2},{addressId:3,location:\"Pune3\",shopId:3}]";

        assertEquals(expectedJson, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetAllAddressesWithNoAddresses() throws Exception {

        RequestBuilder requestBuilder = get(BASE_ADDRESS_URL).contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        List<AddressDto> addresses = new ArrayList<>();
        when(addressService.getAllAddresses()).thenReturn(addresses);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertEquals("[]", result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testSaveAddress() throws Exception {
        AddressDto address = new AddressDto(1, "Pune");
        JSONObject addressJson = new JSONObject();
        addressJson.put("address", address);
        RequestBuilder requestBuilder = post(BASE_ADDRESS_URL)
                .contentType(APPLICATION_JSON).content(addressJson.toString())
                .accept(APPLICATION_JSON);

        doNothing().when(addressService).saveAddress(any(AddressDto.class));

        List<AddressDto> addresses = new ArrayList<>();
        addresses.add(address);
        when(addressService.getAllAddresses()).thenReturn(addresses);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = "[{addressId:1,location:\"Pune\"}]";

        assertEquals(expectedJson, result.getResponse().getContentAsString(), false);

    }
}
