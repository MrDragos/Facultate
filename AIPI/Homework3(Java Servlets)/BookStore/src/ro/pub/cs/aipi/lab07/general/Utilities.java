package ro.pub.cs.aipi.lab07.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

public class Utilities {
    public static String removeSpaces(String text) {
        String result = new String();
        StringTokenizer stringTokenizer = new StringTokenizer(text," ");
        while (stringTokenizer.hasMoreTokens())
            result += stringTokenizer.nextToken();
        return result;
    }
    
    public static String generateInvoiceNumber() {
        String result = new String();
        Random random = new Random();
        for (int currentIndex = 0; currentIndex < 3; currentIndex++)
            result += (char)('A'+random.nextInt(26));
        result += random.nextInt(1000000);
        return result;
    }
    
    public static boolean isSystemFunction(String record) {
        for (String function:Constants.SYSTEM_FUNCTION)
            if (record.contentEquals(function))
                return true;
        return false;
    }
    
    public static String compress(ArrayList<String> record) {
        String result = record.get(0);
        for (int position = 1; position < record.size(); position++) {
            result += " / " + record.get(position);
        }
        return result;
    }

    public static ArrayList<String> decompress(String record) {
        ArrayList<String> result = new ArrayList<>(Arrays.asList(record.split("/")));
        for (int position = 0; position < result.size(); position++) {
            result.set(position, result.get(position).trim().replace("'", "\\'"));
        }
        return result;
    }
}
