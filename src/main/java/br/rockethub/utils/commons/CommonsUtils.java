package br.rockethub.utils.commons;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CommonsUtils {

    public static int generateTokenNumbers() {
        Random random = new Random();
        int token = random.nextInt(999999);

        return Integer.parseInt(String.format("%06d", token));
    }

    public static Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public static boolean isNotValidToken(Date tokenDate){
        Calendar calendar = Calendar.getInstance();
        return (tokenDate.getTime() - calendar.getTime().getTime()) <= 0;
    }

}
