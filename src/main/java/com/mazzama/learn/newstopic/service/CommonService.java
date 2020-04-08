package com.mazzama.learn.newstopic.service;

import com.mazzama.learn.newstopic.dto.request.BaseRequest;
import com.mazzama.learn.newstopic.dto.response.BaseResponse;

import java.util.List;
import java.util.Optional;

public interface CommonService<R extends BaseRequest, S extends BaseResponse> {
    S save(R entity);
    Optional<S> findById(Long id);
    List<S> findAll();
}
