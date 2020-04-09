package com.mazzama.learn.newstopic;

import com.mazzama.learn.newstopic.entity.News;
import com.mazzama.learn.newstopic.entity.Status;
import com.mazzama.learn.newstopic.entity.Topic;
import com.mazzama.learn.newstopic.repository.NewsRepository;
import com.mazzama.learn.newstopic.repository.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@Slf4j
public class NewsTopicApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsTopicApplication.class, args);
    }

}
