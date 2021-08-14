package example;




import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class NewsService {
    private static final Logger log = Logger.getLogger(NewsService.class.getName());


    public List<String> downloadCacheData(String topic) {
        List<String> news = new ArrayList<>();
        news.add("Cache X" + topic);
        news.add("Cache Y" + topic);
        return news;
    }

    public  List<String> downloadFromHerald(String topic) throws Exception {
        List<String> news = new ArrayList<>();
        news.add("Herald X" + topic);
        news.add("Herald Y" + topic);
        return news;
    }

    public  List<String> downloadFromSun(String topic) throws Exception {
        List<String> news = new ArrayList<>();
        news.add("Sun X" + topic);
        news.add("Sun Y" + topic);
        return news;
    }

    public  List<String> downloadFromIndianTimes(String topic, Integer serverLocation) throws Exception {
        List<String> news = new ArrayList<>();
        news.add("IndianTimes X" + topic);
        news.add("IndianTimes Y" + topic);
        return news;
    }

    public   List<String> downloadFromNyTimes(String topic) throws Exception {
        List<String> news = new ArrayList<>();
        news.add("NY X" + topic);
        news.add("NY Y" + topic);
        return news;
    }

}
