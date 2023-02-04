package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    /** Times the getLast method of a SLList of various sizes. */
    public static void timeGetLast() {
        AList<Double> testTimes = new AList<>();
        // Create AList of data structure sizes to test.
        AList<Integer> testSizes = new AList<>();
        // Create AList of operation counts
        AList<Integer> opCounts = new AList<>();
        int number = 1000;
        while (number <= 128000) {
            testSizes.addLast(number);
            opCounts.addLast(10000);
            number *= 2;
        }

        // Iterate over each size.
        for (int s = 0; s < testSizes.size(); s++) {
            // Create SLList of size testSize
            SLList<Integer> testList = new SLList<>();
            for (int i = 0; i < testSizes.get(s); i++) {
                testList.addLast(0);
            }

            // Start Stopwatch
            Stopwatch sw = new Stopwatch();
            // Perform 10,000 getLast operations
            for (int k = 0; k < 10000; k++) {
                int testInt = testList.getLast();
            }
            // Stop Stopwatch
            double timeInSeconds = sw.elapsedTime();
            testTimes.addLast(timeInSeconds);
        }
        // Print results
        printTimingTable(testSizes, testTimes, opCounts);
    }

}