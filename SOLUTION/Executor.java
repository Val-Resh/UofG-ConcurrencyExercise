import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Executor {
    private static final Map<Long, Thread> threads = new HashMap<>();
    private static final Map<Long, Long> finishedCalculations = new ConcurrentHashMap<>();

    /**
     * Method to store the calculated result of N.
     * Uses a HashMap where the key is the calculated number and the value is the result of calculation.
     * @param calculated key : number calculated.
     * @param calculationResult value : calculation result.
     */
    public static void storeCalculationResult(long calculated, long calculationResult){
        finishedCalculations.put(calculated, calculationResult);
    }

    /**
     * Method to create, store and start a thread to run the calculation of value N.
     * Uses a HashMap where the key is the number the thread is working on, and value is the thread's reference.
     * If a number has been previously calculated, then it will not start unnecessary threads,
     * as the result is already available in the finishedCalculations.
     * @param workingOn key : number
     */
    public static void createWorkingThread(long workingOn){
        if(!finishedCalculations.containsKey(workingOn)) {
            Thread thread = new Thread(new SlowCalculator(workingOn));
            threads.put(workingOn, thread);
            thread.start();
        }
    }

    /**
     * Returns the result of a calculation if finished.
     * @param calculatedNumber the N used for calculation.
     * @return long result if finished, -1 otherwise.
     */
    public static long getCalculationResult(long calculatedNumber){
        return (finishedCalculations.containsKey(calculatedNumber)) ?
            finishedCalculations.get(calculatedNumber) : -1;
    }

    /**
     * Returns the currently running threads on program.
     * Since a thread dereferences itself after completing a task, checking whether the thread is running isn't necessary.
     * @return Key: Long, Value: Thread
     * */
    public static Map<Long, Thread> getRunningThreads(){
        return threads;
    }
    /**
     * Method to terminate all current threads.
     */
    public static void terminateAllThreads(){
        threads.forEach((value, thread) -> thread.stop());
        threads.clear();
    }

    /**
     * Removes the thread working on the number passed as parameter from the hash map.
     * By doing so, it dereferences the thread freeing its memory space.
     * @param workingOn key : number
     */
    public static void clearThread(long workingOn){
        if(finishedCalculations.containsKey(workingOn)){
            threads.remove(workingOn);
        }
    }
}
