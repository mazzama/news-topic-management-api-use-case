package com.mazzama.learn.newstopic.repository;

import com.mazzama.learn.newstopic.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonRepository<E extends BaseEntity> extends JpaRepository<E, Long> {
}
