package com.mazzama.learn.newstopic.mapper;

import com.mazzama.learn.newstopic.dto.request.TopicRequest;
import com.mazzama.learn.newstopic.dto.response.TopicResponse;
import com.mazzama.learn.newstopic.entity.Topic;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TopicMapper extends BaseMapper<Topic, TopicRequest, TopicResponse> {
}
