package com.leothosthoren.mynews.model;

/**
 * Created by Sofiane M. alias Leothos Thoren on 03/04/2018
 */
public class StringFormater {

    public StringFormater() {
    }

    public String getSearchArticleDate(String dateToFormat) {
        if (dateToFormat.isEmpty())
            return "";
        String[] fDate = dateToFormat.split("/");
        return String.format("%s%s%s", fDate[2], fDate[1], fDate[0]);
    }

    public String getNewDesk(String[] strings) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            if (i > 0)
                res.append(" ");
            res.append(strings[i]);
        }
        return res.toString();
    }

    public String getItemFormatedDate(String dateToChange) {
        String sub[] = dateToChange.substring(2, 10).split("-");
        return String.format("%s/%s/%s", sub[2], sub[1], sub[0]);
    }
}
