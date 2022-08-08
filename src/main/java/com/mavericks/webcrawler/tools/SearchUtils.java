package com.mavericks.webcrawler.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.mavericks.webcrawler.error.CrawlerException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Feroz Hafiz
 *
 */
@Slf4j
@Component
public class SearchUtils {

    public UriComponents analyze(String url) {
        //building uri
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(URI.create(url));
        return builder.buildAndExpand();
    }

    public String getHtmlContent(URL url){
        // reading the webpage
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            // Read the whole HTML document
            return bufferedReader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new CrawlerException(String.format("Error connecting to host: %s", url),e);
        }
    }
}
