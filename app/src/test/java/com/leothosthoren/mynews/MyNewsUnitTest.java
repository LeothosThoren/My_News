package com.leothosthoren.mynews;

import com.leothosthoren.mynews.controler.activities.SearchArticlesActivity;
import com.leothosthoren.mynews.model.ModelTools;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MyNewsUnitTest {

    //==========================
    // FORMAT OPERATIONS
    //==========================

    @Test
    public void checkEndDateFormater() throws Exception {
        ModelTools formater = new ModelTools();

        assertEquals("19840808", formater.getEndDate("08/08/1984"));
        assertEquals(formater.setFormatCalendar(), formater.getEndDate(""));
    }

    @Test
    public void checkBeginDateFormater() throws Exception {
        ModelTools formater = new ModelTools();

        assertEquals("20130205", formater.getEndDate("05/02/2013"));
        assertEquals(formater.setFormatCalendarMinusYear(), formater.getBeginDate(""));
//        assertEquals("20170404", formater.setFormatCalendarMinusYear());

    }

    @Test
    public void getItemArticleFormatedDate() throws Exception {
        ModelTools modelTools = new ModelTools();

        assertEquals(modelTools.getItemArticleFormatedDate("2018-04-02T20:15:47-04:00"), "02/04/18");
    }


    @Test
    public void formatCalendar() throws Exception {
        ModelTools modelTools = new ModelTools();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        String actual = simpleDateFormat.format(calendar.getTime());
        String expected = modelTools.setFormatCalendar();

        assertEquals(expected, actual);
    }


    //======================
    // CHECKBOXES
    //======================

    @Test
    public void getNewDesk() throws Exception {
        ModelTools modelTools = new ModelTools();

        String[] empty = {"", "", ""};
        String[] full = {"Culture", "Sports", "Travels"};
        String[] nul = new String[5];

        assertEquals(modelTools.getNewDesk(full), "Culture Sports Travels");
        assertEquals(modelTools.getNewDesk(empty), "Culture");
        assertEquals(modelTools.getNewDesk(nul), "Culture");
    }

    //Test using Mockito
    @Test
    public void testBoxChecking() throws Exception {
        SearchArticlesActivity activity = mock(SearchArticlesActivity.class);

        when(activity.boxSelection(true, 0)).thenReturn("Sports");
    }

    @Test
    public void testNewDeskMethod() throws Exception {
        String[] error = new String[6];
        error[0] = "Arts";

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < error.length; i++) {
            if (error[i] != null) {
                if (i > 0 && ((!error[i].isEmpty())))
                    res.append(" ");
                res.append(error[i]);
            }
        }

        String s = res.toString().isEmpty() ? "" : res.toString();

        assertEquals("Arts", s);
    }

}