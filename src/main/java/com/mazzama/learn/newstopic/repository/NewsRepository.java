package com.mazzama.learn.newstopic.repository;

import com.mazzama.learn.newstopic.entity.News;
import com.mazzama.learn.newstopic.entity.Status;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends CommonRepository<News> {
    List<News> findAllByStatus(Status status);

    List<News> findAllByTopicsId(Long id);

    List<News> findAllByStatusAndTopicsId(Status status, Long id);
}
