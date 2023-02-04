package timingtest;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
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

    /**
     * 1. Create Stopwatch object.
     * 2. Create empty ALists for timing tests and for storing results:
     * Integer AList for number of items
     * Double AList for Stopwatch times
     * Integer AList for number of operation
     * 3. Set target size of list.
     * 4. Iterate over that target and addLast to the AList.
     * Time this.
     * 5. Store result in appropriate AList.
     * 6. Once done, pass data to printTimingTable method.
     */
    public static void timeAListConstruction() {
        AList<Integer> testSizes = new AList<>();
        AList<Double> testTimes = new AList<>();
        AList<Integer> testOps = new AList<>();

        // Create AList of sizes for testList.
        int number = 1000;
        while (number <= 128000) {
            testSizes.addLast(number);
            number = number * 2;
        }

        // Iterate over testSizes
        for (int n = 0; n < testSizes.size(); n++) {
            AList<Integer> testList = new AList<>();
            int size = testSizes.get(n);
            // Call addLast as many times as each testSizes value.
            // Time with Stopwatch sw.
            Stopwatch sw = new Stopwatch();
            for (int i = 0; i < size; i++) {
                testList.addLast(i);
            }
            double timeInSeconds = sw.elapsedTime();
            // Store in appropriate AList
            testTimes.addLast(timeInSeconds);
            // Store number of ops
            testOps.addLast(size);
        }

        // Call print method
        printTimingTable(testSizes, testTimes, testOps);
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }
}