package com.mazzama.learn.newstopic.dto.request;

import lombok.Data;

@Data
public class NewsRequest extends BaseRequest {
    private String title;
    private String description;
}
