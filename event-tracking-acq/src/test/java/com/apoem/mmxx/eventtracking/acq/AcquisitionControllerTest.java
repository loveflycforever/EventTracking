//package com.apoem.mmxx.eventtracking.acq;
//
//import com.google.gson.Gson;
//import com.apoem.mmxx.eventtracking.interfaces.dto.acq.BusinessAcquisitionRequestDto;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//@RunWith(SpringRunner.class)
//@WebAppConfiguration
//@SpringBootTest
//@Slf4j
//public class AcquisitionControllerTest {
//    @Autowired
//    protected AcquisitionController AcquisitionController;
//
//    private MockMvc mockMvc;        //SpringMVC提供的Controller测试类
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(AcquisitionController).build();
//    }
//
//    @Test
//    public void testG() throws Exception {
//        String url = "/app/acquisition";
//
//        BusinessAcquisitionRequestDto appAcquisitionRequestDto = new BusinessAcquisitionRequestDto();
//        MvcResult result = mockMvc
//                .perform(MockMvcRequestBuilders.post(url)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(new Gson().toJson(appAcquisitionRequestDto)))
//                .andDo(print())
////                .andExpect(status().isOk())
//                .andReturn();
//
//        log.info(result.getResponse().getContentAsString());
//    }
//
//    @Test
//    public void name() {
//        String a = "20200301";
//        String b = "20200401";
//
//        int i = a.hashCode();
//        int e = b.hashCode();
//
//        System.out.println(i);
//        System.out.println(e);
//
//        Map<String, String> stringStringMap = new HashMap<>();
//        stringStringMap.put("s", "dsdasds");
//
//        Map<String, String> stringStringMap2 = new HashMap<>();
//        stringStringMap2.put("s", "dsdasds");
//        String s2 = new Gson().toJson(stringStringMap2);
//
//        stringStringMap.put("s2", s2);
//        String s = new Gson().toJson(stringStringMap);
//        System.out.println(s);
//    }
//}