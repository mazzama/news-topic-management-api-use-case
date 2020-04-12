package com.mazzama.learn.newstopic.integration;

import com.mazzama.learn.newstopic.NewsTopicApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest(classes = NewsTopicApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewsIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllNews() {
        ResponseEntity<List> responses = restTemplate.getForEntity("http://localhost:" + port + "/api/news", List.class);
        assert(responses.getStatusCode().is2xxSuccessful());
        assertThat(responses.getBody().size(), greaterThan(0));
    }
}
