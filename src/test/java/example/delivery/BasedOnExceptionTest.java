package example.delivery;

import declarativex.Try;

import example.AppLog;
import example.NewsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static example.Assertions.assertResults;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class BasedOnExceptionTest {



    public List<String> newsWith(String topic) throws Exception {


        return Try.from(()->newsService.downloadFromNyTimes(topic))
                .on(IllegalArgumentException.class,()->newsService.downloadFromHerald(topic))
                .on(IllegalAccessException.class,()->newsService.downloadFromSun(topic))
                .get();
//
//        List<String> results = null;
//
//        try {
//            results =newsService.downloadFromNyTimes(topic);
//        }catch (IllegalArgumentException e){
//            try {
//                results =newsService.downloadFromHerald(topic);
//            }catch (Exception e1){
//
//            }
//        }catch (IllegalAccessException e){
//            try {
//                results =newsService.downloadFromSun(topic);
//            }catch (Exception e1){
//
//            }
//        }
//        System.out.println(results.toString());
//        return  results;

    }


    @Spy
    NewsService newsService;

    private static final AppLog log = new AppLog(BasedOnExceptionTest.class);

    //Based On Error
    @Test
    public void onIllegalArgExceptionShouldReturnHeraldData() throws Exception {

        String topic="search";
       doThrow(new IllegalArgumentException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);

        List<String> results= newsWith(topic);
        assertResults(results, "Herald Xsearch,Herald Ysearch");

    }
    @Test
    public void onIllegalAccessExceptionShouldFetchSunData() throws Exception {

        String topic="search";
        doThrow(new IllegalAccessException("Invalid Topic")).when(newsService).downloadFromNyTimes(topic);

        List<String> results= newsWith(topic);

        assertResults(results, "Sun Xsearch,Sun Ysearch");
    }





}
