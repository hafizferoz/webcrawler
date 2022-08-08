package com.mavericks.webcrawler.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

import com.mavericks.webcrawler.tools.SearchUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Feroz Hafiz
 *
 */
@Slf4j(topic = "WebCrawler")
@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

	private final SearchUtils scanUtils;
	private Pattern pattern;

	@Value("${default-pattern}")
	private String defaultPattern;

	@Autowired
	public WebCrawlerServiceImpl(SearchUtils scanUtils) {
		this.scanUtils = scanUtils;
	}

	@PostConstruct
	private void init() {
		// create default regex patter to crawl urls
		log.info("default-pattern:{}", defaultPattern);
		pattern = Pattern.compile(defaultPattern);
	}

	/**
	 *
	 */
	@Override
	public List<String> scan(List<String> rootURLs, String searchStr, Integer breakpoint) throws IOException {

		// urls to be searched
		Queue<String> urlQueue = new LinkedList<>();
		// already searched urls
		List<String> visitedURLs = new ArrayList<>();

		List<String> searchResultUrls = new ArrayList<>();
		rootURLs.stream().forEach(rootURL -> {
			UriComponents uriComponents = scanUtils.analyze(rootURL);
			log.info("host: {}", uriComponents.getHost());

			// initialize the queue with digest list url
			urlQueue.add(rootURL);
			visitedURLs.add(rootURL);

		});

		Matcher urlMatcher;
		Matcher searchMatcher;

		while (!urlQueue.isEmpty()) {
			URL url = new URL(urlQueue.remove());
			String htmlContent = scanUtils.getHtmlContent(url);
			// using Jsoup to parse the HTML document
			String content = Jsoup.parse(htmlContent).text();
			log.info("html content: {}", content);
			searchMatcher = Pattern.compile(Pattern.quote(searchStr), Pattern.CASE_INSENSITIVE).matcher(content);
			if (searchMatcher.find()) {
				searchResultUrls.add(url.toString());
			}
			urlMatcher = pattern.matcher(htmlContent);
			// case we needed to include all results
			breakpoint = getBreakpoint(urlQueue, visitedURLs, urlMatcher, breakpoint);
			if (breakpoint == 0)
				break;
		}
		log.info("searching results: {}", visitedURLs.size());
		log.info("total search results found: {}", searchResultUrls.size());
		return searchResultUrls;
	}

	private int getBreakpoint(Queue<String> urlQueue, List<String> visitedURLs, Matcher urlMatcher, int breakpoint) {
		while (urlMatcher.find()) {
			String currentURL = urlMatcher.group();

			if (!visitedURLs.contains(currentURL)) {
				visitedURLs.add(currentURL);
				urlQueue.add(currentURL);
			}
			// exit the loop if it reaches the breakpoint.
			if (breakpoint == 0) {
				break;
			}
			breakpoint--;
		}
		return breakpoint;
	}
}
