package example;

import declarativex.Try;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static example.Assertions.assertResults;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class NewsServiceTest {
    @Spy
    NewsService newsService;

    @Test
    public void testTryImp() throws Exception {

        String topic="search";
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromHerald(topic);

        List<String> results = null;

        results = downloadFromNews(topic, results);
        System.out.println(results.toString());
        assertResults(results, "Sun Xsearch,Sun Ysearch");


    }

    private List<String> downloadFromNews(String topic, List<String> results) {

        try {
            results =newsService.downloadFromNyTimes(topic);
        }catch (Exception e){

            try {
                results =newsService.downloadFromHerald(topic);
            }catch (Exception e1){
                try {
                    results =newsService.downloadFromSun(topic);
                }catch (Exception e2){


                }
            }
        }
        return results;
    }


    @Test
    public void testTryDec1() throws Exception {

        String topic="search";
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromHerald(topic);

        List<String> results = null;

        results= Try.from(()->newsService.downloadFromNyTimes(topic))
                .or(()->newsService.downloadFromHerald(topic))
                .or(()->newsService.downloadFromSun(topic))
                .get();
        System.out.println(results.toString());
        assertResults(results, "Sun Xsearch,Sun Ysearch");

    }
    @Test
    public void testTryDec3() throws Exception {

        String topic="search";
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromHerald(topic);

        List<String> results = null;

        results= Try.any(newsService::downloadFromNyTimes,
                newsService::downloadFromHerald,
                newsService::downloadFromSun)
                .with(topic)
                .get();
        System.out.println(results.toString());
        assertResults(results, "Sun Xsearch,Sun Ysearch");

    }

}
