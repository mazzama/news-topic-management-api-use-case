package com.mazzama.learn.newstopic.controller;

import com.mazzama.learn.newstopic.dto.request.NewsRequest;
import com.mazzama.learn.newstopic.dto.response.NewsResponse;
import com.mazzama.learn.newstopic.service.impl.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController extends AbstractController<NewsRequest, NewsResponse, NewsService>{

    public NewsController(NewsService service) {
        super(service);
    }

    @GetMapping(params = "status")
    public ResponseEntity<List<NewsResponse>> findAllByStatus(@RequestParam(value = "status", required = false) String status) {
        List<NewsResponse> responses = service.findByStatus(status);
        return ResponseEntity.ok(responses);
    }

    @GetMapping(params = {"topic", "status"})
    public ResponseEntity<List<NewsResponse>> findNewsByTopicAndStatus(@RequestParam(value = "topic", required = false, defaultValue = "") Long id, @RequestParam(value = "status", required = false, defaultValue = "") String status) {
        List<NewsResponse> responses = service.findAllByStatusAndTopicsId(status, id);
        return ResponseEntity.ok(responses);
    }
}