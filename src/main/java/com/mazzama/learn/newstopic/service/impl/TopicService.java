package com.mazzama.learn.newstopic.service.impl;

import com.mazzama.learn.newstopic.dto.request.TopicRequest;
import com.mazzama.learn.newstopic.dto.response.TopicResponse;
import com.mazzama.learn.newstopic.entity.Topic;
import com.mazzama.learn.newstopic.mapper.TopicMapper;
import com.mazzama.learn.newstopic.repository.TopicRepository;
import com.mazzama.learn.newstopic.service.AbstractCommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TopicService extends AbstractCommonService<Topic, TopicRequest, TopicResponse, TopicRepository, TopicMapper> {

    public TopicService(TopicRepository repository, TopicMapper mapper) {
        super(repository, mapper);
    }
}
