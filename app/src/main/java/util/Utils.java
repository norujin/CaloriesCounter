package util;

import java.text.DecimalFormat;

public class Utils
{


    public static String formatNum (int val)
    {

        DecimalFormat formatter = new DecimalFormat("#,###,###");

        String formated = formatter.format(val);

        return formated;


    }


}
