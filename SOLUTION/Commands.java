import java.util.Map;

public class Commands {

    /**
     * Creates a new thread if required to process the calculation of value N.
     * @param N number to calculate.
     */
    public static void start(long N){
        Executor.createWorkingThread(N);
    }

    /**
     * Prints the return of a finished calculation. If unfinished, prints calculating.
     * Result of value -1 represents unfinished calculation.
     * @param N number who's result to get.
     */
    public static void get(long N){
        long result = Executor.getCalculationResult(N);
        boolean containsThread = Executor.getRunningThreads().containsKey(N);
        if (result == -1 && containsThread) {
            System.out.printf("calculating%n");
        } else if(result != -1){
            System.out.printf("result is %d%n", result);
        }
    }

    /**
     * Cancels a currently running thread with task of calculating N.
     * It interrupts the thread and dereferences it from the running threads map.
     * @param N number the thread is calculating.
     */
    public static void cancel(long N) {
        Map<Long, Thread> runningThreads = Executor.getRunningThreads();
        Thread thread = runningThreads.remove(N);
        if(thread != null){
            thread.interrupt();
        }
    }

    /**
     * Method that prints the total number of calculations running on threads with their values.
     */
    public static void running(){
        Map<Long, Thread> runningThreads = Executor.getRunningThreads();
        int numberOfThreads = runningThreads.size();
        String allThreadsValues = getRunningThreadsValues(runningThreads).trim();
        System.out.printf("%d calculations running: %s%n",
                numberOfThreads, allThreadsValues);
    }

    /**
     * Helper function that returns the values of all running thread in a String format.
     * @param runningThreads Map: Key: Long, Value: Thread - The running threads.
     * @return the String representation of all values currently running on all threads.
     */
    private static String getRunningThreadsValues(Map<Long, Thread> runningThreads){
        StringBuilder allThreadsValuesString = new StringBuilder();
        for(Long value : runningThreads.keySet()){
            allThreadsValuesString.append(Long.toString(value)).append(" ");
        }
        return allThreadsValuesString.toString();
    }
    /**
     * Terminates all threads.
     */
    public static void abort(){
        Executor.terminateAllThreads();
    }
}
