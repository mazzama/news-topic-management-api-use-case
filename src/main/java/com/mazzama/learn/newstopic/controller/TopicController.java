package com.mazzama.learn.newstopic.controller;

import com.mazzama.learn.newstopic.dto.request.TopicRequest;
import com.mazzama.learn.newstopic.dto.response.TopicResponse;
import com.mazzama.learn.newstopic.service.impl.TopicService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/topic")
public class TopicController extends AbstractController<TopicRequest, TopicResponse, TopicService> {

    public TopicController(TopicService service) {
        super(service);
    }
}
