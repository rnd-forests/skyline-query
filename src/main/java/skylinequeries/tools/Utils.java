package skylinequeries.tools;

import skylinequeries.tools.database.Query;

import java.util.List;

/**
 * @author Vinh Nguyen
 */
public class Utils {

    /**
     * Prevents outsiders from instantiating the class.
     */
    private Utils() {

    }

    /**
     * Gets the application memory information in runtime from JVM.
     */
    public static void getMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        System.out.println();
        System.out.println("Java virtual machine memory information");
        System.out.println("\t└──>Used Memory  = " + (runtime.totalMemory() - runtime.freeMemory()) / 1000000 + " MB");
        System.out.println("\t└──>Free Memory  = " + runtime.freeMemory() / 1000000 + " MB");
        System.out.println("\t└──>Total Memory = " + runtime.totalMemory() / 1000000 + " MB");
        System.out.println("\t└──>Max Memory   = " + runtime.maxMemory() / 1000000 + " MB");
    }

    /**
     * Prints algorithms experimental results to the console.
     *
     * @param skyline       list of skyline points
     * @param size          the size of the input data
     * @param executionTime algorithm execution time
     */
    public static void console(final List skyline, final long size,
                               final double executionTime, final long accessedNodes) {
        System.out.println("Algorithm performance");
        System.out.println("\t└──>Total points in dataset        = " + size);
        System.out.println("\t└──>Number of skyline points       = " + skyline.size());
        System.out.println("\t└──>Total time to query the DB (s) = " + Query.QUERY_PROCESSING_TIME);
        System.out.println("\t└──>CPU execution time (s)         = " + executionTime);
        if (accessedNodes != 0)
            System.out.println("\t└──>Number of accessed nodes       = " + accessedNodes + " (" + (float) (accessedNodes * 100.0 / size) + "%)");
        Utils.getMemoryInfo();
        System.out.println("--------------------------------\n\n");
    }
}
