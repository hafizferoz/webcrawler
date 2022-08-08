package com.mavericks.webcrawler.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.mavericks.webcrawler.tools.SearchUtils;

@TestPropertySource(properties = {
        "default-pattern=\\\\b((https|http?|ftp|file)://[-a-zA-Z\\\\d+&@#/%?=~_|!:,.;]*[-a-zA-Z\\\\d+&@#/%=~_|])",
})
@ExtendWith(SpringExtension.class)
class WebCrawlerServiceTest {


    @Value("${default-pattern}")
    String defaultPattern;
    private WebCrawlerService service;

    @MockBean
    private SearchUtils scanUtils;

    @BeforeEach
    void setUp() {

        service=new WebCrawlerServiceImpl(scanUtils);
        ReflectionTestUtils.setField(service,"pattern", Pattern.compile(defaultPattern));
    }

    @Test
    void whenScanWithValidInput_thenReturnSuccessfully() throws IOException, URISyntaxException {
        URL url = new URL("https://www.google.com/");

        UriComponents uriComponents= UriComponentsBuilder.fromUri(url.toURI()).build();

        //clearly there are 2 links here
        String html = "<!doctype html><html lang='en'><head><meta charset='utf-8'><title>Test Webpage</title></head><body><!-- your content here... --><a href='https://www.w3schools.com'>Visit W3Schools.com!</a></body></html>";


        when(scanUtils.getHtmlContent(any(URL.class))).thenReturn(html);

        when(scanUtils.analyze(anyString())).thenReturn(uriComponents);

        System.out.println(service);
        List<String> urlList = new ArrayList<>();
        urlList.add(url.toString());
        List<String> result = service.scan(urlList, "search", 10);

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}