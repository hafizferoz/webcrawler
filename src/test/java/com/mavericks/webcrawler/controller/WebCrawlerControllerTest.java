package com.mavericks.webcrawler.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mavericks.webcrawler.model.WebCrawlRequest;
import com.mavericks.webcrawler.service.WebCrawlerService;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = WebCrawlerController.class)
class WebCrawlerControllerTest {


    @MockBean
    private WebCrawlerService crawlerService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

    }

    @Test
    void whenScanWithValidInput_thenReturnSuccessfully() throws Exception {

        String[] urls = {"https://www.google.com/"};
        List<String> urlList = (List)Arrays.asList(urls);

        ObjectMapper objectMapper=new ObjectMapper();

        WebCrawlRequest scanRequest= WebCrawlRequest.builder()
                .urls(urlList)
                .breakPoint(100)
                .searchStr("search")
                .build();


        System.out.println(objectMapper.writeValueAsString(scanRequest));

        mockMvc.perform(post("/webcrawler/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scanRequest))
                )
                .andExpect(status().isOk());
    }

    @Test
    void whenScanWithInValidInput_thenThrowException() throws Exception {

        ObjectMapper objectMapper=new ObjectMapper();

        WebCrawlRequest scanRequest= WebCrawlRequest.builder()
                .urls(new ArrayList())
                .breakPoint(100)
                .searchStr("test")
                .build();


        System.out.println(objectMapper.writeValueAsString(scanRequest));

        mockMvc.perform(post("/webcrawler/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scanRequest))
                )
                .andExpect(status().is4xxClientError());
    }
}