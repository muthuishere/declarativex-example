package example.delivery;

import declarativex.Try;
import example.NewsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static example.Assertions.assertResults;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class CombiningExceptionMethodTest {

    private List<String> getResult(String topic, List<String> results) {


        return Try.any(newsService::downloadFromNyTimes,newsService::downloadFromHerald,newsService::downloadFromSun)
                .with(topic)
                .get();
//
//
//        return Try.from(()->newsService.downloadFromNyTimes(topic))
//                .or(()->newsService.downloadFromHerald(topic))
//                .or(()->newsService.downloadFromSun(topic))
//                .get();

//
//        try {
//            results =newsService.downloadFromNyTimes(topic);
//
//        }catch (Exception e){
//            try {
//                results =newsService.downloadFromHerald(topic);
//            }catch (Exception e1){
//                try {
//                    results =newsService.downloadFromSun(topic);
//                }catch (Exception ignored){
//                }
//            }
//        }
//        return results;
    }



    @Spy
    NewsService newsService;

    @Test
    public void testTryImp() throws Exception {

        String topic="search";
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromHerald(topic);

        List<String> results = null;

        results = getResult(topic, results);

        System.out.println(results.toString());
        assertResults(results, "Sun Xsearch,Sun Ysearch");


    }


}
