package com.leothosthoren.mynews;

import android.graphics.Color;
import android.widget.CheckBox;

import com.leothosthoren.mynews.model.ModelTools;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MyNewsUnitTest {

    //==========================
    // STRING FORMAT OPERATIONS
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
//        assertEquals("20180305", formater.setFormatCalendarMinusYear());

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
        calendar.add(Calendar.DATE, 0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        String actual = simpleDateFormat.format(calendar.getTime());
        String expected = modelTools.setFormatCalendar();

        assertEquals(expected, actual);
        assertEquals(expected, "20180411");
    }

    @Test
    public void checkHour() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 22);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);

        String hour22 = sdf.format(now.getTime());

        assertEquals("22:00:00", hour22);
    }

    //=======================
    // CHECKBOXES OPERATIONS
    //=======================

    @Test
    public void getNewDesk() throws Exception {
        ModelTools modelTools = new ModelTools();

        String[] empty = {"", "", ""};
        String[] full = {"Politics", "Sports", "Travels"};
        String[] nul = new String[5];

        assertEquals(modelTools.getNewDesk(full), "Politics Sports Travels");
        assertEquals(modelTools.getNewDesk(empty), "Culture");
        assertEquals(modelTools.getNewDesk(nul), "Culture");
    }

    //Test using Mockito
//    @Test
//    public void testBoxChecking() throws Exception {
//        SearchArticlesActivity activity = mock(SearchArticlesActivity.class);
//
//        when(activity.boxSelection(true, 0)).thenReturn("Sports");
//    }

    @Test
    public void testNewDeskMethod() throws Exception {
        ModelTools modelTools = new ModelTools();
        String[] error = new String[6];
        error[0] = "Culture";

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < error.length; i++) {
            if (error[i] != null) {
                if (i > 0 && ((!error[i].isEmpty())))
                    res.append(" ");
                res.append(error[i]);
            }
        }

        String s = res.toString().isEmpty() ? "" : res.toString();

        assertEquals("Culture", s);
        assertEquals("Culture", modelTools.getNewDesk(error));
    }

    //Test using Mockito
    @Test
    public void changeCheckboxTextColor() throws Exception {
        ModelTools modelTools = new ModelTools();
        CheckBox box1 = mock(CheckBox.class), box2 = mock(CheckBox.class), box3 = mock(CheckBox.class);
        CheckBox[] checkBoxes = {box1,box2,box3};

       assertEquals(checkBoxes[0].getTextColors(), checkBoxes[2].getTextColors());
    }

}