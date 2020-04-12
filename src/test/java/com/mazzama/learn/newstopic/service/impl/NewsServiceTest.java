package com.mazzama.learn.newstopic.service.impl;

import com.mazzama.learn.newstopic.dto.request.NewsRequest;
import com.mazzama.learn.newstopic.dto.response.NewsResponse;
import com.mazzama.learn.newstopic.dto.response.TopicResponse;
import com.mazzama.learn.newstopic.entity.News;
import com.mazzama.learn.newstopic.entity.Status;
import com.mazzama.learn.newstopic.entity.Topic;
import com.mazzama.learn.newstopic.mapper.NewsMapper;
import com.mazzama.learn.newstopic.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("TEST")
class NewsServiceTest {

    @InjectMocks
    private NewsService newsService;

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private NewsRepository newsRepository;

    private String expectedMessage = "No record exist for given id";
    private List<News> allNews = new ArrayList<>();
    private List<News> filteredNews = new ArrayList<>();
    private List<NewsResponse> allNewsResponses = new ArrayList<>();
    private List<NewsResponse> filteredNewsResponses = new ArrayList<>();
    private News expectedNews1;
    private NewsResponse response;


    @BeforeEach
    void setUp() {
        // Model
        // Builder for parent class's field still experimental feature in Lombok :D
        // https://projectlombok.org/features/experimental/SuperBuilder
        Topic topic1 = Topic.builder().name("Olahraga").description("Topic olahraga").build();
        topic1.setId(1L);
        Topic topic2 = Topic.builder().name("Headline").description("Berita Terbaru").build();
        topic2.setId(2L);

        Set<Topic> topicSet = new HashSet<>();
        topicSet.add(topic1);

        // Expected News
        expectedNews1 = News.builder()
                .title("Pemenang Ballon D'or").description("Pemenang Ballon D'or telah diumumkan")
                .status(Status.PUBLISH).topics(topicSet).build();
        expectedNews1.setId(1L);

        topicSet.add(topic2);
        News expectedNews2 = News.builder()
                .title("Liga 1 Ditunda").description("Oleh PSSI, Liga 1 Ditunda sampai batas yang tidak ditentukan")
                .status(Status.DRAFT).topics(topicSet).build();
        expectedNews2.setId(2L);

        allNews.addAll(Arrays.asList(expectedNews1, expectedNews2));
        filteredNews.add(expectedNews1);

        // TopicResponse DTO
        TopicResponse topicResponse1 = new TopicResponse();
        topicResponse1.setId(topic1.getId());
        topicResponse1.setName(topic1.getName());
        topicResponse1.setDescription(topic1.getDescription());

        Set<TopicResponse> setTopicResponse1 = new HashSet<>();
        setTopicResponse1.add(topicResponse1);

        TopicResponse topicResponse2 = new TopicResponse();
        topicResponse2.setId(topic2.getId());
        topicResponse2.setName(topic2.getName());
        topicResponse2.setDescription(topic2.getDescription());


        // NewResponse DTO
        response = new NewsResponse();
        response.setId(1L);
        response.setDescription("Pemenang Ballon D'or telah diumumkan");
        response.setTitle("Pemenang Ballon D'or");
        response.setStatus(Status.PUBLISH);
        response.setTopics(setTopicResponse1);

        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setId(2L);
        newsResponse.setStatus(Status.DRAFT);

        filteredNewsResponses.add(response);
        allNewsResponses.addAll(Arrays.asList(response, newsResponse));
    }

    @Test
    public void saveNewNews_shouldSuccessAndReturnNewsWithId() {
        // Preparing the data
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setTitle("Pemenang Ballon D'or");
        newsRequest.setDescription("Pemenang Ballon D'or telah diumumkan");

        News request = new News();
        request.setTitle(newsRequest.getTitle());
        request.setDescription(newsRequest.getDescription());

        doReturn(request).when(newsMapper).requestToEntity(newsRequest);
        doReturn(expectedNews1).when(newsRepository).save(request);
        doReturn(response).when(newsMapper).entityToResponse(expectedNews1);

        NewsResponse actualResponse = newsService.save(newsRequest);

        assertNotNull(actualResponse);
        assertEquals(1L, actualResponse.getId());
        assertEquals(newsRequest.getTitle(), actualResponse.getTitle());
        assertEquals(newsRequest.getDescription(), actualResponse.getDescription());
    }

    @Test
    public void whenFindAll_thenReturnNewsList() {
        doReturn(allNews).when(newsRepository).findAll();
        doReturn(allNewsResponses).when(newsMapper).mappingList(allNews);

        List<NewsResponse> actualResult = newsService.findAll();

        assertNotNull(actualResult);
        assertThat(actualResult.size(), greaterThan(0));
    }

    @Test
    public void whenFindAllNewsByStatus_thenReturnNewsListWithCorrectStatus() {
        Status status = Status.PUBLISH;

        doReturn(filteredNews).when(newsRepository).findAllByStatus(status);
        doReturn(filteredNewsResponses).when(newsMapper).mappingList(filteredNews);

        List<NewsResponse> actualResult = newsService.findByStatus("Publish");

        assertNotNull(actualResult);
        assertThat(actualResult.size(), greaterThan(0));
        assertEquals(Status.PUBLISH, actualResult.get(0).getStatus());
    }

    @Test
    public void whenFindAllNewsByStatus_thenThrowAnException() {
        Status status = Status.DELETED;
        String expectedMessage = "News with status: "+ status.getStatus() + " not found.";
        List<News> emptyNews = new ArrayList<>();

        doReturn(emptyNews).when(newsRepository).findAllByStatus(status);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            newsService.findByStatus("Deleted");
        });

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void whenDeleteNewsByIdWithValidId_thenReturnVoid() {
        Long id = 1L;

        doReturn(true).when(newsRepository).existsById(id);

        newsService.deleteById(id);
        verify(newsRepository, times(1)).existsById(eq(id));
        verify(newsRepository, times(1)).deleteById(eq(id));
    }

    @Test
    public void whenDeleteNewsByIdWithInvalidId_shouldThrowsException() {
        Long id = 5L;

        doReturn(false).when(newsRepository).existsById(id);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
           newsService.deleteById(id);
        });

        verify(newsRepository, times(1)).existsById(eq(id));
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void whenFindByIdWithValidId_thenReturnNews() {
        Long id = 1L;

        Optional<News> optionalNews = Optional.of(expectedNews1);

        doReturn(optionalNews).when(newsRepository).findById(id);
        doReturn(response).when(newsMapper).entityToResponse(optionalNews.get());

        NewsResponse actualResult = newsService.findById(id);

        verify(newsRepository, times(1)).findById(eq(id));
        assertNotNull(actualResult);
        assertEquals(actualResult.getId(), optionalNews.get().getId());
    }

    @Test
    public void whenFindByIdWithInvalidId_thenThrowsException() {
        Long id = 5L;
        Optional<News> optionalEmpty = Optional.empty();

        doReturn(optionalEmpty).when(newsRepository).findById(5L);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            newsService.findById(id);
        });

        verify(newsRepository, times(1)).findById(eq(id));
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void findAllByStatusAndTopicsIdWithEmptyStringAndIdNull() {
        Long id = null;
        String status = "";

        doReturn(allNews).when(newsRepository).findAll();
        doReturn(allNewsResponses).when(newsMapper).mappingList(allNews);

        List<NewsResponse> actualResult = newsService.findAllByStatusAndTopicsId(status, id);

        verify(newsRepository, times(1)).findAll();
        assertThat(actualResult.size(), greaterThan(0));
    }

    @Test
    void findAllByStatusAndTopicsIdWithInvalidStringAndIdNull_shouldThrownException() {
        Long id = null;
        String status = "Deleted";

        // Empty News
        List<News> emptyNews = allNews.stream()
                .filter(value -> value.getStatus().toString().equalsIgnoreCase(status))
                .collect(Collectors.toList());

        doReturn(emptyNews).when(newsRepository).findAllByStatus(Status.DELETED);

        assertThrows(EntityNotFoundException.class, () -> {
            newsService.findAllByStatusAndTopicsId(status, id);
        });
    }

    @Test
    void findAllByStatusAndTopicsIdWithValidStringAndIdNull_shouldReturnCorrectNewsResponses() {
        Long id = null;
        String status = "Publish";

        //using Filtered news
        doReturn(filteredNews).when(newsRepository).findAllByStatus(Status.PUBLISH);
        doReturn(filteredNewsResponses).when(newsMapper).mappingList(filteredNews);

        List<NewsResponse> actualResponses = newsService.findAllByStatusAndTopicsId(status, id);

        assertThat(actualResponses, containsInAnyOrder(hasProperty("status", is(Status.PUBLISH))));
    }

    @Test
    void findAllByStatusAndTopicsIdValid_shouldReturnCorrectNewsResponses() {
        Long id = 1L;
        String status = "Publish";

        doReturn(filteredNews).when(newsRepository).findAllByStatusAndTopicsId(Status.PUBLISH, id);
        doReturn(filteredNewsResponses).when(newsMapper).mappingList(filteredNews);

        List<NewsResponse> actualResponses = newsService.findAllByStatusAndTopicsId(status, id);
        assertThat(actualResponses, containsInAnyOrder(hasProperty("status", is(Status.PUBLISH))));

        // Get TopicResponse with actual id
        Set<TopicResponse> actualTopicResponseExample = actualResponses.get(0).getTopics();
        assertThat(actualTopicResponseExample, containsInAnyOrder(hasProperty("id", is(id))));
    }

    @Test
    void findAllByStatusAndIdWithStatusEmptyAndIdNotNull() {
        Long id = 1L;
        String status = "";

        doReturn(filteredNews).when(newsRepository).findAllByTopicsId(id);
        doReturn(filteredNewsResponses).when(newsMapper).mappingList(filteredNews);

        List<NewsResponse> actualResponses = newsService.findAllByStatusAndTopicsId(status, id);
        // Get TopicResponse with actual id
        Set<TopicResponse> actualTopicResponseExample = actualResponses.get(0).getTopics();
        assertThat(actualTopicResponseExample, containsInAnyOrder(hasProperty("id", is(id))));
    }

    @Test
    void updateNewsWithInvalidId_shouldThrowAnException() {
        Long id = 3L;
        // Preparing the data
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setTitle("Pemenang Ballon D'or 2020");
        newsRequest.setDescription("Pemenang Ballon D'or Tahun 2020 telah diumumkan");
        newsRequest.setId(id);

        Optional<News> emptyNews = Optional.empty();

        doReturn(emptyNews).when(newsRepository).findById(id);

        assertThrows(EntityNotFoundException.class, () -> {
            newsService.update(id, newsRequest);
        });
    }

    @Test
    void updateNewsWithValidId_shouldReturnUpdatedField() {
        // Prepare the data
        Long id = 1L;
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setTitle("Jumlah korban korona per 11 April");
        newsRequest.setDescription("Berita terbaru mengenai Corona");
        newsRequest.setId(id);

        News updatedNews = new News();
        updatedNews.setTitle("Jumlah korban korona per 11 April");
        updatedNews.setDescription("Berita terbaru mengenai Corona");
        updatedNews.setId(id);

        News currentNews = new News();
        currentNews.setTitle("Jumlah korban korona per 10 April");
        currentNews.setDescription(newsRequest.getDescription());
        currentNews.setId(id);
        Optional<News> currentNewsOptional = Optional.of(currentNews);

        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setTitle(newsRequest.getTitle());
        newsResponse.setDescription(newsRequest.getDescription());
        newsResponse.setId(id);

        //Stubing
        doReturn(currentNewsOptional).when(newsRepository).findById(id);
        doReturn(updatedNews).when(newsMapper).requestToEntity(newsRequest);
        doReturn(updatedNews).when(newsRepository).save(updatedNews);
        doReturn(newsResponse).when(newsMapper).entityToResponse(updatedNews);

        // Test
        NewsResponse actualResult = newsService.update(id, newsRequest);
        assertEquals(actualResult.getTitle(), newsRequest.getTitle());
        assertEquals(actualResult.getId(), newsRequest.getId());
    }
}