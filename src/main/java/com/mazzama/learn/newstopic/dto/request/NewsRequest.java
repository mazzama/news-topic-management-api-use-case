package com.mazzama.learn.newstopic.dto.request;

import com.mazzama.learn.newstopic.entity.Status;
import lombok.Data;

import java.util.Set;

@Data
public class NewsRequest extends BaseRequest {
    private String title;
    private String description;
    private Status status;
    private Set<TopicRequest> topics;
}
