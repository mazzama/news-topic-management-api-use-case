package com.mazzama.learn.newstopic.mapper;

import com.mazzama.learn.newstopic.dto.request.NewsRequest;
import com.mazzama.learn.newstopic.dto.response.NewsResponse;
import com.mazzama.learn.newstopic.entity.News;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = TopicMapper.class
)
public interface NewsMapper extends BaseMapper<News, NewsRequest, NewsResponse> {
}
