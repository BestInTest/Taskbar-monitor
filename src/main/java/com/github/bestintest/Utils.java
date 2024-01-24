package com.github.bestintest;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Utils {

    public static boolean isCli(String[] args) {
        for (String arg : args) {
            if (arg.equals("-cli")) {
                return true;
            }
        }
        return false;
    }

    public static String getCurrentTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return currentTime.format(formatter);
    }

    public static boolean areListsEqualUnordered(List<?> list1, List<?> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        List<?> sortedList1 = new ArrayList<>(list1);
        List<?> sortedList2 = new ArrayList<>(list2);

        Comparator<Object> comparator = (o1, o2) -> {
            String str1 = String.valueOf(o1);
            String str2 = String.valueOf(o2);
            return str1.compareTo(str2);
        };

        sortedList1.sort(comparator);
        sortedList2.sort(comparator);

        return sortedList1.equals(sortedList2);
    }
}
