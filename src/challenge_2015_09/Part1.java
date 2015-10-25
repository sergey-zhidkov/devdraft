package challenge_2015_09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Part1 {
    public static void main(String [] args) throws IOException {
        // read from input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String [] firstLine = br.readLine().split("\\s+");
        String [] secondLine = br.readLine().split("\\s+");
        String [] thirdLine = br.readLine().split("\\s+");
        int blocksNum = Integer.parseInt(firstLine[0]);
        int patrolsLength = Integer.parseInt(firstLine[1]);

        int [] threats = new int[blocksNum];
        for (int i = 0; i < blocksNum; i++) {
            threats[i] = Integer.parseInt(secondLine[i]);
        }



    }
}
