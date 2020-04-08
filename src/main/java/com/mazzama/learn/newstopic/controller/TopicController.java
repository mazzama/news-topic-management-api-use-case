package com.mazzama.learn.newstopic.controller;

import com.mazzama.learn.newstopic.entity.Topic;
import com.mazzama.learn.newstopic.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/topic")
public class TopicController {

    private TopicRepository topicRepository;

    @Autowired
    public TopicController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @GetMapping
    public List<Topic> getAll() {
        return topicRepository.findAll();
    }
}
