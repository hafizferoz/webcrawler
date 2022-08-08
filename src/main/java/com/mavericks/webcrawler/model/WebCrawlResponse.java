package com.mavericks.webcrawler.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * @author Feroz Hafiz
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebCrawlResponse {
    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object reason;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object dataResult;
    private int code;
}
