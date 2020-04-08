package com.mazzama.learn.newstopic.dto.response;

import lombok.Data;

@Data
public class TopicResponse extends BaseResponse {
    private String name;
    private String description;
}
