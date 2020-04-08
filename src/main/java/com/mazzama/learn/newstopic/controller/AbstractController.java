package com.mazzama.learn.newstopic.controller;

import com.mazzama.learn.newstopic.dto.request.BaseRequest;
import com.mazzama.learn.newstopic.dto.response.BaseResponse;
import com.mazzama.learn.newstopic.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class AbstractController<R extends BaseRequest, S extends BaseResponse, B extends CommonService<R, S>> implements CommonController<R, S> {

    protected final B service;

    @Autowired
    public AbstractController(B service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<S> save(R request) {
        return ResponseEntity.ok(service.save(request));
    }

    @Override
    public ResponseEntity<List<S>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
