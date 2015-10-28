package challenge_2015_10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Part1 {
    private static final int MINUTES_IN_HOUR = 60;
    private static final String IMPOSSIBLE = "IMPOSSIBLE";

    public static void main(String [] args) throws IOException {
        // read from input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String [] dataToParse = br.readLine().split("\\s+");
        int attractionCount = Integer.parseInt(dataToParse[0]); // 0 < R < 100
        int hoursOpened = Integer.parseInt(dataToParse[1]); // 1 <= H <= 23

        int [][] queueTimeMatrix = new int[attractionCount][];
        parseQueueTime(br, queueTimeMatrix, hoursOpened);
        int queriesCount = Integer.parseInt(br.readLine()); // 0 <= Q < 100

        for (int i = 0; i < queriesCount; i++) {
            int guestEnterTime = Integer.parseInt(br.readLine()); // T
            int desireVisitAttractionCount = Integer.parseInt(br.readLine()); // 0 <= D <= 20
            int [] desiredAttractions = stringArrToIntArray(br.readLine().split("\\s+"));
            printLeastAmountOfTimeToVisitDesiredAttractions(queueTimeMatrix, guestEnterTime, desiredAttractions, hoursOpened);
        }
    }

    static void printLeastAmountOfTimeToVisitDesiredAttractions(int [][] queueTimeMatrix, int guestEnterTime, int [] desiredAttractions, int hoursOpened) {
        final int MAX_MINUTES_OPENED = hoursOpened * MINUTES_IN_HOUR;
        if (guestEnterTime > MAX_MINUTES_OPENED) {
            System.out.println(IMPOSSIBLE);
            return;
        }

        int result;
        int desiredAttractionCount = desiredAttractions.length;
        if (desiredAttractionCount == 1) {
            result = findLeastTimeInQueue(queueTimeMatrix, guestEnterTime, desiredAttractions[0]);
            System.out.println(result);
        }
    }

    static int findLeastTimeInQueue(int [][] queueTimeMatrix, int guestEnterTime, int attractionIndex) {
        int [] hoursQueues = queueTimeMatrix[attractionIndex];
        int hoursOpened = hoursQueues.length;

        int startHourIndex = guestEnterTime / MINUTES_IN_HOUR;
        int guestEnterTimeStartsFromStartHour = guestEnterTime % MINUTES_IN_HOUR;
        int curHourQueueTime = hoursQueues[startHourIndex];
        int timeToNextHour = MINUTES_IN_HOUR - guestEnterTimeStartsFromStartHour;

        int [] guestQueues = new int[hoursOpened];
        guestQueues[startHourIndex] = curHourQueueTime;
        for (int i = startHourIndex + 1; i < hoursOpened; i++) {
            curHourQueueTime = hoursQueues[i];
            if (i == startHourIndex + 1) {

            }
        }




        return 0;
    }

    static void parseQueueTime(BufferedReader br, int [][] queueTimeMatrix, int hoursOpened) throws IOException {
        int attractionCount = queueTimeMatrix.length;
        for (int i = 0; i < attractionCount; i++) {
            String [] queueTimeString = br.readLine().split("\\s+");
            queueTimeMatrix[i] = stringArrToIntArray(queueTimeString);
        }
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
