package com.leothosthoren.mynews;

import com.leothosthoren.mynews.controler.activities.SearchArticlesActivity;
import com.leothosthoren.mynews.model.StringFormater;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MyNewsUnitTest {

    @Test
    public void checkIfNewDeskIsCorrect() throws Exception {
        StringFormater stringFormater = new StringFormater();

        String[] strings = {"Arts", "Sports", "Travels"};

        assertEquals(stringFormater.getNewDesk(strings), "Arts Sports Travels");
    }

    @Test
    public void dateFormater() throws Exception {
        StringFormater formater = new StringFormater();

        assertEquals("19840808", formater.getSearchArticleDate("08/08/1984"));
    }

    @Test
    public void getFormatRecyclerViewsDate() throws Exception {
        StringFormater stringFormater = new StringFormater();

        assertEquals(stringFormater.getItemFormatedDate("2018-04-02T20:15:47-04:00"), "02/04/18");
    }


    //Test using Mockito
    @Test
    public void testBoxChecking() throws Exception {
        SearchArticlesActivity activity = mock(SearchArticlesActivity.class);

        when(activity.boxSelection(true, "", "Sports")).thenReturn("Sports");

    }
}