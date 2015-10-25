package challenge_2015_10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Part0 {

    public static void main(String [] args) throws IOException {
        // read from input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int attractionCount = Integer.parseInt(br.readLine()); // 0 < attractionCount <= 100
        String [] walkTimeString = br.readLine().split("\\s+");
        int queryCount = Integer.parseInt(br.readLine()); // 0 <= queryCount <= 100

        for (int i = 0; i < queryCount; i++) {
            int visitCount = Integer.parseInt(br.readLine()); // 0 < visitCount <= attractionCount
            String [] attractionsToVisitString = br.readLine().split("\\s+");
            printLeastNumberOfMinutesPossiblySpentWalking(stringArrToIntArray(walkTimeString), stringArrToIntArray(attractionsToVisitString));
        }
    }

    static void printLeastNumberOfMinutesPossiblySpentWalking(int [] walkingTime, int [] attractionsToVisit) {
        int visitCount = attractionsToVisit.length;
        int result = 0;
        for (int i = 1; i < visitCount; i++) {
            int minutesToVisitInCW  = getMinutesToVisitInCW(attractionsToVisit[i - 1], attractionsToVisit[i], walkingTime);
            int minutesToVisitInCCW = getMinutesToVisitInCCW(attractionsToVisit[i - 1], attractionsToVisit[i], walkingTime);
            result += Math.min(minutesToVisitInCW, minutesToVisitInCCW);
        }

        System.out.println(result);
    }

    static int getMinutesToVisitInCW(int from, int to, int [] walkingTime) {
        int attractionCount = walkingTime.length;
        int result = 0;
        if (from == to) {
            return result;
        }

        int currAttractionIndex = from;
        while (currAttractionIndex != to) {
            result += walkingTime[currAttractionIndex];
            currAttractionIndex++;
            if (currAttractionIndex >= attractionCount) {
                currAttractionIndex = 0;
            }
        }

        return result;
    }

    static int getMinutesToVisitInCCW(int from, int to, int [] walkingTime) {
        int attractionCount = walkingTime.length;
        int result = 0;
        if (from == to) {
            return result;
        }

        int currAttractionIndex = from;
        while (currAttractionIndex != to) {
            if (currAttractionIndex <= 0) {
                currAttractionIndex = attractionCount;
            }
            result += walkingTime[currAttractionIndex - 1];
            currAttractionIndex--;
        }

        return result;
    }

    static int [] stringArrToIntArray(String[] array) {
        int len = array.length;
        int [] result = new int[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.parseInt(array[i]);
        }
        return result;
    }
}
