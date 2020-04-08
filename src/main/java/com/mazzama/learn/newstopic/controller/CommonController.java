package com.mazzama.learn.newstopic.controller;

import com.mazzama.learn.newstopic.dto.request.BaseRequest;
import com.mazzama.learn.newstopic.dto.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CommonController<R extends BaseRequest, S extends BaseResponse>{

    @PostMapping
    ResponseEntity<S> save(@RequestBody R request);

    @GetMapping
    ResponseEntity<List<S>> findAll();
}
