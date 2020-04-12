package com.mazzama.learn.newstopic.service.impl;

import com.mazzama.learn.newstopic.dto.request.NewsRequest;
import com.mazzama.learn.newstopic.dto.response.NewsResponse;
import com.mazzama.learn.newstopic.entity.News;
import com.mazzama.learn.newstopic.entity.Status;
import com.mazzama.learn.newstopic.mapper.NewsMapper;
import com.mazzama.learn.newstopic.repository.NewsRepository;
import com.mazzama.learn.newstopic.service.AbstractCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
@Transactional
public class NewsService extends AbstractCommonService<News, NewsRequest, NewsResponse, NewsRepository, NewsMapper> {

    public NewsService(NewsRepository repository, NewsMapper newsMapper) {
        super(repository, newsMapper);
    }

    public List<NewsResponse> findByStatus(String status) {
        Status statusEnum = Status.fromString(status);

        List<News> result = this.repository.findAllByStatus(statusEnum);

        if (result.isEmpty()) {
            throw new EntityNotFoundException("News with status: "+ status + " not found.");
        }
        return mapper.mappingList(result);
    }

    public List<NewsResponse> findAllByStatusAndTopicsId(String status, Long id) {
        List<News> result;

        log.info("Status : {}", status.isEmpty());

        if (!status.isEmpty() && (id != null)) {
            Status statusEnum = Status.fromString(status);
            result = this.repository.findAllByStatusAndTopicsId(statusEnum, id);
        } else if (status.isEmpty() && (id != null)) {
            result = this.repository.findAllByTopicsId(id);
        } else if (!status.isEmpty() && (id == null)) {
            Status statusEnum = Status.fromString(status);
            result = this.repository.findAllByStatus(statusEnum);
        } else {
            result = this.repository.findAll();
        }

        if (result.isEmpty()) {
            throw new EntityNotFoundException("News with status: "+ status + " not found.");
        }
        return mapper.mappingList(result);
    }
}
