package challenge_2015_09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Part0 {
    static final String [] COMPOUNDS = {"FIZ", "BAR", "BAZ", "ZIF", "RAB", "ZAB"};
    static final int COMPOUND_LEN = 3;

    public static void main(String [] args) throws IOException {
        // read from input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int crystalsNum = Integer.parseInt(br.readLine());
        char [] workbench = br.readLine().toCharArray();
        int swapsNum = Integer.parseInt(br.readLine());
        String [] swaps = br.readLine().split("\\s+");

        char [] newWorkbench;
        // swap and remove compounds
        workbench = findAndRemoveCompounds(workbench, 0);
        for (int i = 0; i < swaps.length; i++) {
            int swapIndex = Integer.parseInt(swaps[i]);
            workbench = swapCrystals(workbench, swapIndex);
            newWorkbench = findAndRemoveCompounds(workbench, swapIndex);
            if (workbench.length == newWorkbench.length) { // don't allow swap if no new compounds
                workbench = swapCrystals(workbench, swapIndex);
                continue;
            }
            workbench = newWorkbench;
        }

        System.out.println(workbench);
    }

    static char [] findAndRemoveCompounds(final char[] workbench, final int swapIndex) {
        String temp = new String(workbench);
        int searchFromIndex = swapIndex - 2;
        if (searchFromIndex < 0) {
            searchFromIndex = 0;
        }

        int firstIndex = findCompound(temp, searchFromIndex);
        if (firstIndex < 0) {
            return workbench;
        }

        int secondIndex = findCompound(temp, firstIndex + 2);
        char [] newWorkbench;
        if (secondIndex < 0) { // then there is only 1 compound
            newWorkbench = removeCompound(temp, firstIndex, COMPOUND_LEN);
            return findAndRemoveCompounds(newWorkbench, swapIndex - 2);
        } else if (secondIndex == firstIndex + 2) { // overlapping compounds
            newWorkbench = removeCompound(temp, firstIndex, COMPOUND_LEN + 2);
            return findAndRemoveCompounds(newWorkbench, swapIndex - 2);
        } else if (secondIndex == firstIndex + COMPOUND_LEN) { // two independent compounds
            newWorkbench = removeCompound(temp, firstIndex, COMPOUND_LEN * 2);
            return findAndRemoveCompounds(newWorkbench, swapIndex - 2);
        } else { // should not be
            return workbench;
        }
    }

    static int findCompound(String workbench, int searchFromIndex) {
        int compoundIndex = -1;
        for (String compound : COMPOUNDS) {
            int index = workbench.indexOf(compound, searchFromIndex);
            if (index != -1) {
                compoundIndex = index;
                break;
            }
        }
        return compoundIndex;
    }

    static char [] removeCompound(String workbench, int fromIndex, int length) {
        String newWorkbench = workbench.substring(0, fromIndex) + workbench.substring(fromIndex + length);
        return newWorkbench.toCharArray();
    }

//    static char [] findAndRemoveCompounds(char[] workbench) {
//        String temp = new String(workbench);
//        final int compoundLen = 3;
//        for (String compound : COMPOUNDS) {
//            int index = temp.indexOf(compound);
//            if (index != -1) {
//                temp = temp.substring(0, index) + temp.substring(index + compoundLen);
//                return findAndRemoveCompounds(temp.toCharArray());
//            }
//        }
//
//        return temp.toCharArray();
//    }

    static char [] swapCrystals(char [] workbench, int leftIndex) {
        char temp = workbench[leftIndex];
        workbench[leftIndex] = workbench[leftIndex + 1];
        workbench[leftIndex + 1] = temp;
        return workbench;
    }
}
