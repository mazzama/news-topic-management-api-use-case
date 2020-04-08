package com.mazzama.learn.newstopic.mapper;

import com.mazzama.learn.newstopic.dto.request.BaseRequest;
import com.mazzama.learn.newstopic.dto.response.BaseResponse;
import com.mazzama.learn.newstopic.entity.BaseEntity;

import java.util.List;

public interface BaseMapper<E extends BaseEntity, R extends BaseRequest, S extends BaseResponse> {
    E requestToEntity(R request);
    S entityToResponse(E entity);
    E responseToEntity(S response);
    List<S> mappingList(List<E> entityList);
}
