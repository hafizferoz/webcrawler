package com.mavericks.webcrawler.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mavericks.webcrawler.model.WebCrawlResponse;
import com.mavericks.webcrawler.model.WebCrawlRequest;
import com.mavericks.webcrawler.service.WebCrawlerService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Feroz Hafiz
 *
 */
@Slf4j
@RequestMapping("/webcrawler")
@RestController
public class WebCrawlerController {


    private final WebCrawlerService webCrawlerService;

    @Autowired
	public WebCrawlerController(WebCrawlerService webCrawlerService) {
        this.webCrawlerService = webCrawlerService;
    }

    @PostMapping("/scan")
    public ResponseEntity<WebCrawlResponse> scan(@RequestBody WebCrawlRequest scanRequest) throws IOException {
        log.info("Searching for: {}", scanRequest.getUrls());
        List<String> list = webCrawlerService.scan(scanRequest.getUrls(), scanRequest.getSearchStr(), scanRequest.getBreakPoint());

        return ResponseEntity.ok(WebCrawlResponse.builder()
                .message("Successfully Searched!")
                .dataResult(list)
                .code(200)
                .build());
    }

}
