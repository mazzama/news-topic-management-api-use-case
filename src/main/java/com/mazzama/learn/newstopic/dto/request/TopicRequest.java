package com.mazzama.learn.newstopic.dto.request;

import lombok.Data;

@Data
public class TopicRequest extends BaseRequest {
    private String name;
    private String description;
}
