package com.leothosthoren.mynews;

import android.support.test.runner.AndroidJUnit4;

import com.leothosthoren.mynews.model.Utils.NewYorkTimeStream;
import com.leothosthoren.mynews.model.apis.articles.MostPopular;
import com.leothosthoren.mynews.model.apis.articles.SearchArticle;
import com.leothosthoren.mynews.model.apis.articles.TopStories;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApiInstrumentedTest {
    @Test
    public void fetchMostPopularApi() throws Exception {

        Observable<MostPopular> mostPopularObservable = NewYorkTimeStream.streamFetchMostPopular();

        TestObserver<MostPopular> testObserver = new TestObserver<>();

        mostPopularObservable.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();


        MostPopular mostPopularFetched = testObserver.values().get(0);

        assertThat("MostPopular", mostPopularFetched.getResults().get(0).getMedia().get(0).getMediaMetadata().get(0).getFormat().equals("Standard Thumbnail"));
    }

    @Test
    public void fetchTopStoriesApi() throws Exception {

        Observable<TopStories> mostPopularObservable = NewYorkTimeStream.streamFetchTopStories("world");

        TestObserver<TopStories> testObserver = new TestObserver<>();

        mostPopularObservable.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        TopStories mostPopularFetched = testObserver.values().get(0);

        assertThat("TopStories", mostPopularFetched.getResults().get(0).getItemType().equals("Article"));
    }

    @Test
    public void fetchSearchArticle() throws Exception {

        Observable<SearchArticle> searchArticleObservable =
                NewYorkTimeStream.streamFetchSearchArticle("Us", "news_desk:(Foreign)", "20180101", "20180201");

        TestObserver<SearchArticle> testObserver = new TestObserver<>();

        searchArticleObservable.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        SearchArticle searchArticleFetched = testObserver.values().get(0);

        assertThat("Search Article", searchArticleFetched.getResponse().getDocs().get(0).getNewDesk().equals("Foreign"));
    }
}
