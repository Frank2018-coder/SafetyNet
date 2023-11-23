package com.safety.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Calendar;
import java.util.Date;

public class Utility {
    public static int getNbreAnnee(Date dateFrom, Date dateTo){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(dateFrom);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(dateTo);
        return  calendar2.get(Calendar.YEAR) - calendar1.get(Calendar.YEAR);
    }

    public static Date subStractDay(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static String jsonEncode(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException var3) {
            return null;
        }
    }

    public static <T> T jsonDecode(String json, Class<T> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, tClass);
        } catch (Exception var4) {
            //var4.printStackTrace();
            return null;
        }
    }
}
