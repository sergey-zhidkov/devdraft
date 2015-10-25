package challenge_2015_09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Part0FasterVersion {

    static final String [] COMPOUNDS = {"FIZ", "BAR", "BAZ", "ZIF", "RAB", "ZAB"};

    public static void main(String [] args) throws IOException {
        // read from input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int crystalsNum = Integer.parseInt(br.readLine());
        char [] workbench = br.readLine().toCharArray();
        int swapsNum = Integer.parseInt(br.readLine());
        String [] swaps = br.readLine().split("\\s+");

        // swap and remove compounds
        workbench = findAndRemoveCompounds(workbench, 0);
        for (int i = 0; i < swaps.length; i++) {
            int swapIndex = Integer.parseInt(swaps[i]);
            workbench = swapCrystals(workbench, swapIndex);
            int lenBefore = workbench.length;
            int searchFromIndex = swapIndex - 2;
            workbench = findAndRemoveCompounds(workbench, searchFromIndex);
            int lenAfter = workbench.length;
            if (lenBefore == lenAfter) { // don't allow swap if no new compounds
                workbench = swapCrystals(workbench, swapIndex);
            }
        }

        System.out.println(workbench);
    }

    static char [] swapCrystals(char [] workbench, int leftIndex) {
        char temp = workbench[leftIndex];
        workbench[leftIndex] = workbench[leftIndex + 1];
        workbench[leftIndex + 1] = temp;
        return workbench;
    }

    static char [] findAndRemoveCompounds(char [] workbench, int searchFromIndex) {
        if (searchFromIndex < 0) searchFromIndex = 0;

        findCompound(workbench, searchFromIndex);



        return workbench;
    }

    static int findCompound(char [] workbench, int startIndex) {
        int substrLen = 6;
        int workbenchLen = workbench.length;
        if (workbenchLen - startIndex < 6) {
            substrLen = workbenchLen - startIndex;
        }

        char [] temp = Arrays.copyOfRange(workbench, startIndex, substrLen + startIndex);
        String stringToSearch = new String(temp);


        return -1;
    }


//    static void findCompound(char [] workbench, int startIndex) {
//        String temp = new String(workbench);
//        int curIndex = startIndex;
//        int compoundStartIndex = startIndex;
//        while(true) {
//            if (curIndex + 2 >= workbench.length) {
//                break;
//            }
//            char letter = workbench[curIndex];
//            switch (letter) {
//                case 'B':
//                    if (workbench[curIndex + 1] == 'A' && workbench[curIndex + 2] == 'R') {
//
//                    } else if (workbench[curIndex + 1] == 'A' && workbench[curIndex + 2] == 'Z') {
//
//                    } else {
//                        curIndex++;
//                    }
//                    break;
//                case 'F':
//                    if (workbench[curIndex + 1] == 'I' && workbench[curIndex + 2] == 'Z') {
//
//                    } else {
//                        curIndex++;
//                    }
//                    break;
//                case 'R':
//                    if (workbench[curIndex + 1] == 'A' && workbench[curIndex + 2] == 'B') {
//
//                    } else {
//                        curIndex++;
//                    }
//                    break;
//                case 'Z':
//                    if (workbench[curIndex + 1] == 'A' && workbench[curIndex + 2] == 'B') {
//
//                    } else if (workbench[curIndex + 1] == 'I' && workbench[curIndex + 2] == 'Z') {
//
//                    } else {
//                        curIndex++;
//                    }
//                    break;
//                default: curIndex++; break;
//            }
//        }
//    }


}
