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

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private TopicRepository topicRepository;

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
