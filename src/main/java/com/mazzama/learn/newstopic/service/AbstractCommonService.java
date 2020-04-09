package com.mazzama.learn.newstopic.service;

import com.mazzama.learn.newstopic.dto.request.BaseRequest;
import com.mazzama.learn.newstopic.dto.response.BaseResponse;
import com.mazzama.learn.newstopic.entity.BaseEntity;
import com.mazzama.learn.newstopic.mapper.BaseMapper;
import com.mazzama.learn.newstopic.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCommonService<E extends BaseEntity, R extends BaseRequest, S extends BaseResponse, D extends CommonRepository<E>, M extends BaseMapper<E, R, S>> implements CommonService<R, S> {

    private static final String MESSAGE = "No record exist for given id";

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
    public S update(Long id, R request) {
        Optional<E> current = repository.findById(id);

        if (current.isPresent()) {
            E currentModel = current.get();

            E updatedRequest = mapper.requestToEntity(request);
            updatedRequest.setCreatedDate(currentModel.getCreatedDate());
            updatedRequest.setVersion(currentModel.getVersion() + 1);

            currentModel = repository.save(updatedRequest);
            return mapper.entityToResponse(currentModel);
        } else {
            throw new EntityNotFoundException(MESSAGE);
        }
    }

    @Override
    public void deleteById(Long id) {
        boolean isExist = repository.existsById(id);

        if (isExist) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException(MESSAGE);
        }
    }

    @Override
    public S findById(Long id) {
        Optional<E> model = repository.findById(id);

        if (model.isPresent()) {
            return mapper.entityToResponse(model.get());
        } else {
            throw new EntityNotFoundException(MESSAGE);
        }
    }

    @Override
    public List<S> findAll() {
        List<E> result = repository.findAll();
        return mapper.mappingList(result);
    }
}
