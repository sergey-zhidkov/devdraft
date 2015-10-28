package challenge_2015_10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Models the stations and trains of a light rail system
 * to calculate when the next train arrives at a particular stop.
 */
class LightRailApp {

    private static final int MINUTES_IN_DAY = 24 * 60;

    /**
     * Converts a string in the format hh:mm (of a 24-hour clock)
     * to minutes past midnight.  For example, 01:30 becomes 90.
     * @param hhmm a string in the format hh:mm such as 23:41
     * @return how many minutes past midnight have elapsed
     */
    public static int toMinutes(final String hhmm) {
        final String parts[] = hhmm.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    /**
     * Converts a number of minutes past midnight into a String representation
     * in the format hh:mm of a 24-hour clock.  For example, 90 becomes 01:30.
     * @param minutes time past midnight
     * @return the time in format h:mm or hh:mm, such as 23:41
     */
    public static String formatMinutes(final int minutes) {
        return String.format("%02d:%02d",minutes / 60,  minutes % 60);
    }


    /**
     * When the first northbound train leaves the southernmost station,
     * as minutes past midnight.
     */
    private final int northFirst;

    /**
     * The latest a northbound train may leave the southernmost station.
     * This does not guarantee a train leaves then--it just means a train
     * won't leave the southernmost station if it's past this time.
     */
    private final int northLast;

    /**
     * When the first southbound train leaves the northernmost station,
     * as minutes past midnight.
     */
    private final int southFirst;

    /**
     * The latest a southbound train may leave the northernmost station.
     * This does not guarantee a train leaves then--it just means a train
     * won't leave the northernmost station if it's past this time.
     */
    private final int southLast;

    /**
     * How long it takes (in minutes) to go between stops i and i + 1, either
     * northbound or southbound.
     */
    private final int[] travelTimes;

    /**
     * The starts of time intervals that describe the frequency of trains
     * leaving from the northernmost and southernmost stations.  An interval
     * starts at the ith entry and ends 1 minute earlier than the (i + 1)th entry.
     * The starts are in increasing order.
     */
    private final int[] intervalStarts;

    /**
     * How frequently (in minutes) trains leave from the northernmost and southernmost stations
     * during a time interval described by intervalStarts.  That is, between the times
     * intervalStarts[i] and intervalStarts[i + 1] - 1, trains leave every
     * intervalPeriods[i] minutes.  The last element describes the frequency until the last
     * train leaves.
     */
    private final int[] intervalPeriods;

    /**
     * @param travelTimes
     *			How long it takes (in minutes) to go between stops i and i +
     *			1, either northbound or southbound.
     * @param intervalStarts
     *			The starts of time intervals that describe the frequency of
     *			trains leaving from the northernmost and southernmost
     *			stations. An interval starts at the ith entry and ends 1
     *			minute earlier than the (i + 1)th entry. The starts should
     *			be ordered chronologically.
     * @param intervalPeriods
     *			How frequently (in minutes) trains leave from the northernmost
     *			and southernmost stations during a time interval described by
     *			intervalStarts. That is, between the times intervalStarts[i]
     *			and intervalStarts[i + 1] - 1, trains leave every
     *			intervalPeriods[i] minutes. The last element describes the
     *			frequency until the last train leaves.
     * @param northFirst
     *			When the first northbound train leaves the southernmost
     *			station, as minutes past midnight.
     * @param northLast
     *			The latest a northbound train may leave the southernmost
     *			station. This does not guarantee a train leaves then--it just
     *			means a train won't leave the southernmost station if it's
     *			past this time.  Expressed as minutes past midnight.
     * @param southFirst
     *			When the first southbound train leaves the northernmost
     *			station, as minutes past midnight.
     * @param southLast
     *			The latest a southbound train may leave the northernmost
     *			station. This does not guarantee a train leaves then--it just
     *			means a train won't leave the northernmost station if it's
     *			past this time.  Expressed as minutes past midnight.
     */
    public LightRailApp(int[] travelTimes, int[] intervalStarts, int[] intervalPeriods,
                        int northFirst, int northLast, int southFirst, int southLast) {
        this.travelTimes = travelTimes;
        this.intervalStarts = intervalStarts;
        this.intervalPeriods = intervalPeriods;
        this.northFirst = northFirst;
        this.northLast = northLast;
        this.southFirst = southFirst;
        this.southLast = northLast;
    }

    /**
     * Returns the earliest time at or after the given time when a train
     * will arrive at the stop.
     * @param leave the time at or after the train may leave.
     * @param stop which stop to leave from (0 being southernmost)
     * @param north whether the train is northbound (otherwise southbound)
     * @return the earliest time a train will leave at or after the time given.
     */
    public int nextTrain(final int leave, final int stop, boolean north) {
        // how many minutes ahead this stop is from the first station
        // (the "first" station is the southernmost station if northbound,
        // and northernomst station if southbound).
        int offset = 0;

        // the earliest departure time of a train at the first station
        final int first;

        // the latest possible departure time of a train at the first stop
        final int last;
        if (north) {
            first = northFirst;
            last = northLast;
            for (int i = 0; i < stop; i++) {
                offset += travelTimes[i];
            }
        } else {
            first = southFirst;
            last = southLast;
            for (int i = travelTimes.length - 1; i > stop; i--) {
                offset += travelTimes[i];
            }
        }

        // normalized leave time--when the rider would want to leave, if they were
        // at the first station
        int normLeave = leave - offset;

        // if outside train operating hours, just return the first train.
        if (normLeave > last || normLeave < first) {
            return first + offset;
        }

        // when the desired train leaves the first station
        int trainLeave = first;
        int i = 0;
        while (trainLeave < normLeave) {
            trainLeave += intervalPeriods[i];
            if (i + 1 < intervalStarts.length && trainLeave >= intervalStarts[i + 1]) {
                i++;
            }
        }
        return trainLeave + offset;
    }


    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader (new InputStreamReader (System.in));

        // read stop count
        final int stopCount = Integer.parseInt(input.readLine());
        int[] stops = new int[stopCount - 1];

        // read stops
        String[] parts = input.readLine().split(" ");
        for (int i = 0; i < parts.length; i++) {
            stops[i] = Integer.parseInt(parts[i]);
        }

        // read first and latest departure times, for northbound and southbound
        parts = input.readLine().split(" ");
        final int northFirst = toMinutes(parts[0]);
        final int northLast = toMinutes(parts[1]);

        parts = input.readLine().split(" ");
        final int southFirst = toMinutes(parts[0]);
        final int southLast = toMinutes(parts[1]);

        // read intervals and periods
        final int intervalCount = Integer.parseInt(input.readLine());
        final int[] intervalStarts = new int[intervalCount];
        final int[] intervalPeriods = new int[intervalCount];
        for (int i = 0; i < intervalCount; i++) {
            parts = input.readLine().split(" ");
            intervalStarts[i] = toMinutes(parts[0]);
            intervalPeriods[i] = Integer.parseInt(parts[1]);
        }

        // read query count
        final int queryCount = Integer.parseInt(input.readLine());

        // read and process queries
        LightRailApp app = new LightRailApp(stops, intervalStarts, intervalPeriods,
                northFirst, northLast, southFirst, southLast);
        for (int i = 0; i < queryCount; i++) {
            parts = input.readLine().split(" ");
            System.out.println(formatMinutes(
                    app.nextTrain(toMinutes(parts[0]), Integer.parseInt(parts[1]), parts[2].equals("N"))));
        }
    }
}
