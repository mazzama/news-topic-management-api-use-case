package com.mazzama.learn.newstopic.config;

import com.mazzama.learn.newstopic.entity.News;
import com.mazzama.learn.newstopic.entity.Status;
import com.mazzama.learn.newstopic.entity.Topic;
import com.mazzama.learn.newstopic.repository.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@Profile("DEV")
@Slf4j
public class DataPopulator {

    @Autowired
    private NewsRepository newsRepository;

    @PostConstruct
    public void populateData() {
        News news = new News();
        news.setTitle("Berita Satu");
        news.setDescription("This is the description of a news");
        News result = newsRepository.save(news);
        log.info("ID: {}:", result.getId().toString());
        log.info("Saving the news");

        Topic topic = new Topic();
        topic.setName("Olahraga");
        topic.setDescription("Topic olahraga terkini");

        Topic updatedTopic = Topic.builder()
                .name("Headline")
                .description("Berita Terbaru")
                .build();
        Set<Topic> topicSet = new HashSet<>();
        topicSet.addAll(Arrays.asList(topic, updatedTopic));
        log.info("Saving the topic");

        News updated = News.builder()
                .title("Liga 1 Ditunda")
                .description("Oleh PSSI, Liga 1 Ditunda sampai batas yang tidak ditentukan")
                .status(Status.DRAFT)
                .topics(topicSet)
                .build();

        newsRepository.save(updated);

        log.info("Saving news and topic");
    }
}
