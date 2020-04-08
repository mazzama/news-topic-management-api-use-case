package com.mazzama.learn.newstopic.service.impl;

import com.mazzama.learn.newstopic.dto.request.NewsRequest;
import com.mazzama.learn.newstopic.dto.response.NewsResponse;
import com.mazzama.learn.newstopic.entity.News;
import com.mazzama.learn.newstopic.entity.Status;
import com.mazzama.learn.newstopic.mapper.NewsMapper;
import com.mazzama.learn.newstopic.repository.NewsRepository;
import com.mazzama.learn.newstopic.service.AbstractCommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NewsService extends AbstractCommonService<News, NewsRequest, NewsResponse, NewsRepository, NewsMapper> {

    public NewsService(NewsRepository repository, NewsMapper newsMapper) {
        super(repository, newsMapper);
    }

    public List<NewsResponse> findByStatus(String status) {
        Status statusEnum = Status.fromString(status);
        List<News> result = this.repository.findAllByStatus(statusEnum);
        return mapper.mappingList(result);
    }
}
