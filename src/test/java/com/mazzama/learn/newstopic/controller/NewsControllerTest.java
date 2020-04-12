package com.mazzama.learn.newstopic.controller;

import com.mazzama.learn.newstopic.dto.request.NewsRequest;
import com.mazzama.learn.newstopic.dto.response.NewsResponse;
import com.mazzama.learn.newstopic.entity.Status;
import com.mazzama.learn.newstopic.service.impl.NewsService;
import com.mazzama.learn.newstopic.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = NewsController.class)
@ActiveProfiles("TEST")
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    private List<NewsResponse> filteredNewsResponses = new ArrayList<>();
    private List<NewsResponse> allNewsResponses = new ArrayList<>();

    @BeforeEach
    void setUp() {
        NewsResponse newsResponse1 = new NewsResponse();
        newsResponse1.setId(1L);
        newsResponse1.setTitle("Berita 1");
        newsResponse1.setDescription("Deskripsi Berita");
        newsResponse1.setStatus(Status.DRAFT);

        NewsResponse newsResponse2 = new NewsResponse();
        newsResponse2.setId(2L);
        newsResponse2.setStatus(Status.DRAFT);

        NewsResponse newsResponse3 = new NewsResponse();
        newsResponse3.setId(3L);
        newsResponse3.setStatus(Status.PUBLISH);

        filteredNewsResponses.addAll(Arrays.asList(newsResponse1, newsResponse2));
        allNewsResponses.addAll(Arrays.asList(newsResponse1, newsResponse2, newsResponse3));
    }

    @Test
    void findAllByStatus_thenReturnNewsListWithCorrectStatus() throws Exception {
        String status = "Draft";

        doReturn(filteredNewsResponses).when(newsService).findByStatus(status);
        mockMvc.perform(get("/api/news")
                .param("status", status)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].status", containsInAnyOrder("DRAFT", "DRAFT")));
    }

    @Test
    void findAll_thenReturnNewsList() throws Exception {
        doReturn(allNewsResponses).when(newsService).findAll();
        mockMvc.perform(get("/api/news")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0)) ));
    }

    @Test
    void saveNews_thenShouldReturnNewsWithId() throws Exception {
        NewsRequest request = new NewsRequest();
        request.setTitle("Berita 1");
        request.setDescription("Deskripsi Berita");

        NewsResponse response = allNewsResponses.get(0);

        doReturn(response).when(newsService).save(request);

        mockMvc.perform(post("/api/news")
                .content(JsonUtil.toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void deleteNews_shouldReturnOk() throws Exception {
        Long id = 1L;

        doNothing().when(newsService).deleteById(id);

        mockMvc.perform(delete("/api/news/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findNewsById_shouldReturnCorrectResponse() throws Exception {
        Long id = 1L;
        NewsResponse response = allNewsResponses.get(0);

        doReturn(response).when(newsService).findById(id);

        mockMvc.perform(get("/api/news/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())));
    }

    @Test
    void updateNews() throws Exception {
        Long id = 1L;
        NewsRequest request = new NewsRequest();
        request.setId(id);
        request.setTitle("Berita 1");
        request.setDescription("Deskripsi Berita");

        NewsResponse response = allNewsResponses.get(0);

        doReturn(response).when(newsService).update(id, request);

        mockMvc.perform(put("/api/news/{id}", id)
                .content(JsonUtil.toJson(response))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findNewsByTopicAndStatus() throws Exception {
        String status = "Draft";

        doReturn(filteredNewsResponses).when(newsService).findAllByStatusAndTopicsId(status, null);
        mockMvc.perform(get("/api/news")
                .param("status", status)
                .param("topic", "")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].status", containsInAnyOrder("DRAFT", "DRAFT")));
    }
}