package challenge_2015_11;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class Part3 {
    public static void main (String[] args) throws java.lang.Exception {
        // parse data
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int lineLength = Integer.parseInt(br.readLine());
        long[] dominoHeights = new long[lineLength];
        String dominoLine = br.readLine().trim();
        int index = dominoLine.indexOf(" ");
        int prevIndex = 0;
        int i = 0;
        int len = dominoLine.length();
        while (prevIndex < index) {
            dominoHeights[i++] = Long.parseLong(dominoLine.substring(prevIndex, index));
            prevIndex = index + 1;
            index = dominoLine.indexOf(' ', prevIndex);
        }
        dominoHeights[i] = Long.parseLong(dominoLine.substring(prevIndex, len));

        long[] rightCascadeDistances = new long[lineLength];
        long[] leftCascadeDistances = new long[lineLength];
        // go from left to right, calculate and cache values
        for (int position = lineLength - 1; position >= 0; position--) {
            rightCascadeDistances[position] = findCascadeDistance(dominoHeights, rightCascadeDistances, position);
        }

        dominoHeights = reverseArray(dominoHeights);

        for (int position = lineLength - 1; position >= 0; position--) {
            leftCascadeDistances[position] = findCascadeDistance(dominoHeights, leftCascadeDistances, position);
        }

        // print output
        StringBuilder outSb = new StringBuilder();
        for (long cascadeDistance: rightCascadeDistances) {
            outSb.append(cascadeDistance).append(" ");
        }
        System.out.println(outSb.toString().trim());

        outSb = new StringBuilder();
        for (i = leftCascadeDistances.length - 1; i >= 0; i--) {
            outSb.append(leftCascadeDistances[i]).append(" ");
        }
        System.out.println(outSb.toString().trim());
    }

    public static long findCascadeDistance(long[] dominoHeights, long[] cascadeDistances, int position) {
        long currentHeight = dominoHeights[position];
        if (currentHeight == 0) {
            return currentHeight;
        }
        // check each before on the length of current height of this domino
        return getMaximumCascadeDistance(cascadeDistances, position, currentHeight);
    }

    public static long getMaximumCascadeDistance(long[] cascadeDistances, int position, long height) {
        int len = cascadeDistances.length;
        long from = position;
        long to = position + height;
        if (to >= len) {
            to = len - 1;
        }

        long maximumCascadeDistance = height;
        long maximumCascadeDifference = position - from; // 0
        for (int i = (int) from + 1; i <= to; i++) {
            long cascadeDistance = cascadeDistances[i]; // calculated before
            long cascadeDifference = i - position;
            if (maximumCascadeDistance + maximumCascadeDifference < cascadeDistance + cascadeDifference) {
                maximumCascadeDistance = cascadeDistance;
                maximumCascadeDifference = cascadeDifference;
            }
        }

        return maximumCascadeDistance + maximumCascadeDifference;
    }

    public static long[] reverseArray(long[] array) {
        long temp;
        int len = array.length;
        int halfLen = len / 2;
        for (int i = 0; i < halfLen; i++) {
            temp = array[i];
            array[i] = array[len - i - 1];
            array[len - i - 1] = temp;
        }
        return array;
    }
}