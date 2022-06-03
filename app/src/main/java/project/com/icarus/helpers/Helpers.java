package project.com.icarus.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helpers {
    public static final Date toDate(String d)  {
        try {
            SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
            return sdformat.parse(d);
        }
        catch(Exception ex) {
            return null;
        }
    }
    public static final String fromDate(Date d) {
        SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
        return sdformat.format(d);
    }
}
