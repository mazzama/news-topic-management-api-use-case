package com.mazzama.learn.newstopic.service;

import com.mazzama.learn.newstopic.dto.request.BaseRequest;
import com.mazzama.learn.newstopic.dto.response.BaseResponse;
import com.mazzama.learn.newstopic.entity.BaseEntity;
import com.mazzama.learn.newstopic.mapper.BaseMapper;
import com.mazzama.learn.newstopic.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public abstract class AbstractCommonService<E extends BaseEntity, R extends BaseRequest, S extends BaseResponse, D extends CommonRepository<E>, M extends BaseMapper<E, R, S>> implements CommonService<R, S> {

    protected final D repository;
    protected final M mapper;

    @Autowired
    public AbstractCommonService(D repository, M mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public S save(R request) {
        E model = mapper.requestToEntity(request);
        model = repository.save(model);
        return mapper.entityToResponse(model);
    }

    @Override
    public Optional<S> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<S> findAll() {
        List<E> result = repository.findAll();
        return mapper.mappingList(result);
    }
}
