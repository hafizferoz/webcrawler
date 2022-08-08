package com.mavericks.webcrawler.service;

import java.io.IOException;
import java.util.List;

/**
 * @author Feroz Hafiz
 *
 */
public interface WebCrawlerService {

    List<String> scan(List<String> rootUrls, String searchStr, Integer breakPoint) throws IOException;
}
