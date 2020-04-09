package com.mazzama.learn.newstopic.controller;

import com.mazzama.learn.newstopic.dto.request.BaseRequest;
import com.mazzama.learn.newstopic.dto.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CommonController<R extends BaseRequest, S extends BaseResponse>{

    @PostMapping
    ResponseEntity<S> save(@RequestBody R request);

    @GetMapping
    ResponseEntity<List<S>> findAll();

    @PutMapping("/{id}")
    ResponseEntity<S> update(@PathVariable("id") Long id, @RequestBody R requestPayload);

    @DeleteMapping("/{id}")
    ResponseEntity deleteById(@PathVariable("id") Long id);

    @GetMapping("/{id}")
    ResponseEntity<S> findById(@PathVariable("id") Long id);
}
