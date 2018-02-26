package com.leothosthoren.mynews;

import android.support.test.runner.AndroidJUnit4;

import com.leothosthoren.mynews.model.MostPopular;
import com.leothosthoren.mynews.model.TopStories;
import com.leothosthoren.mynews.model.Utils.NewYorkTimeStream;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

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
//    @Test
//    public void fetchMostPopularApi() throws Exception {
//
//        Observable<MostPopular> mostPopularObservable = NewYorkTimeStream.streamFetchMostPopular("all-sections");
//
//        TestObserver<MostPopular> testObserver = new TestObserver<>();
//
//        mostPopularObservable.subscribeWith(testObserver)
//                .assertNoErrors()
//                .assertNoTimeout()
//                .awaitTerminalEvent();
//
//        MostPopular mostPopularFetched = testObserver.values().get(0);
//
//        assertThat("View number of article 1707", mostPopularFetched.getResults().get(0).getType().equals("Article"));
//    }

    @Test
    public void fetchTopStoriesApi() throws Exception {

        Observable<TopStories> mostPopularObservable = NewYorkTimeStream.streamFetchTopStories("world");

        TestObserver<TopStories> testObserver = new TestObserver<>();

        mostPopularObservable.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        TopStories mostPopularFetched = testObserver.values().get(0);

        assertThat("View", mostPopularFetched.getResults().get(0).getItemType().equals("Article"));
    }
}
