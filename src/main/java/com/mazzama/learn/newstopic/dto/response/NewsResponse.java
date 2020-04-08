package com.mazzama.learn.newstopic.dto.response;

import com.mazzama.learn.newstopic.entity.Status;
import lombok.Data;

import java.util.Set;

@Data
public class NewsResponse extends BaseResponse {
    private String title;
    private String description;
    private Status status;
    private Set<TopicResponse> topics;
}
