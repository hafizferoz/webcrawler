package com.mavericks.webcrawler.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Feroz Hafiz
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebCrawlRequest {

    @NotNull
    @NotBlank
    private List<String> urls;
    private Integer breakPoint;
    private String searchStr;
}
