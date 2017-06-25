package sort;
import cs1c.FHsort;
import cs1c.TimeConverter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.annotation.ElementType;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Tests Quicksort at multiple recursion limits at multiple array sizes, which are 20k * 2^N
 * recursion limits go from 2 to 300, with a step size of 2
 * @author Myron Pow 6/25/2017
 */
public class MainSortTester {
    final static int MIN_RECURSION = 2;
    final static int MAX_RECURSION = 300;
    final static int STEP_SIZE = 2;
    final static int [] ARRAY_ELEMENTS = {20000,40000,80000,160000,320000,640000,1280000};
    static int [] recursionSteps = new int[MAX_RECURSION/STEP_SIZE];

    /**
     * Method uses material from Project_09 for date stamps and file writing.
     * Main is 4 for loops
     * first one populates the recursion steps
     * next 3 are nested to populate arrays, sort those arrays, and to do so at each step of the quicksort
     * @param args
     */
    public static void main(String[] args){
        final String filePath = "resources/";	// Directory path for Mac OS X
        DateFormat format = new SimpleDateFormat("yyyyMMdd_hhmmss");
        String timeStamp = format.format(new Date());
        String outputFileName = "sort_results" + timeStamp + ".txt";

        FileOutputStream out;
        try{
            out = new FileOutputStream(filePath+outputFileName);
        }
        catch (FileNotFoundException e){
            System.err.println(e);
            return;
        }
        PrintWriter sortedFiles = new PrintWriter(out, true);


        int randomInt;
        int currentRecursion = MIN_RECURSION;
        long startTime, estimatedTime;
        Integer[] sortTargetClone;

        for (int i = 0; i < recursionSteps.length; i++){
            recursionSteps[i] = currentRecursion;
            currentRecursion += STEP_SIZE;
        }
        for (int i = 0; i < recursionSteps.length; i++)
        {
            FHsort.setRecursionLimit(recursionSteps[i]);
            System.out.println("Recursion Limit = " + recursionSteps[i]);

            for (int j = 0; j < ARRAY_ELEMENTS.length; j++){
                int sampleSize = ARRAY_ELEMENTS[j];
                Integer[] generatedArray = new Integer[sampleSize];
                for (int k = 0; k < sampleSize; k++){
                    randomInt = (int)(Math.random() * sampleSize);
                    generatedArray[k] = randomInt;
                }
                sortTargetClone = generatedArray.clone();


                startTime = System.nanoTime();
                FHsort.quickSort(sortTargetClone);
                estimatedTime = System.nanoTime() - startTime;


                System.out.printf("%-3s","Elements: " + sampleSize);
                System.out.println("Time:" + TimeConverter.convertTimeToString(estimatedTime));
                System.out.println("Raw Time (ns):" + estimatedTime);
                sortedFiles.println(recursionSteps[i] + "," + sampleSize + "," + estimatedTime + ",");


            }
            System.out.println("\n\n\n");
        }
        sortedFiles.close();
    }



}
