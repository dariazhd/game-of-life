package de.hawhamburg.inf.gol;

import static de.hawhamburg.inf.gol.Application.SLEEP;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

/**
 * A pool of LifeThreads and a queue holding Runnables to be processed.
 * 
 * @author Christian Lins
 */
public class LifeThreadPool {
    /* Unsynchronized Queue of Runnables */
    public final Queue<Runnable> tasks = new LinkedList<>();
    
    /* Number of threads managed by this pool */
    private final int numThreads;
    
    /* The collection of LifeThread instances forming this pool */
    private final LifeThread[] threads;
    
    public LifeThreadPool(int numThreads) {
        this.numThreads = numThreads;
        this.threads = new LifeThread[numThreads];
    }
    
    /**
     * This method will block until the queue of tasks has been emptied by the
     * running threads.
     * @throws InterruptedException 
     */
    public void barrier() throws InterruptedException {
        while (!tasks.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                System.err.println("Application InterruptedException");
            }	  
        }
    }
    
    /**
     * Calls interrupt() on every thread in this pool.
     */
    public void interrupt() {
        Arrays.stream(threads).forEach(t -> t.interrupt());
    }
    
    /**
     * Waits for all tasks to finish and calls interrupt on every thread. This
     * method is identical to synchronized calling barrier() and interrupt().
     * 
     * @throws InterruptedException 
     */
    public void joinAndExit() throws InterruptedException {
        barrier();
        interrupt();
    }
        
    /**
     * Adds a task to the queue of this pool.
     * 
     * @param task Runnable containing the work to be done 
     */
    public void submit(Runnable task) {
        tasks.add(task);
    }
    
    /**
     * Removes and returns the next task from the queue of this pool.
     * This method blocks if the queue is currently empty.
     * 
     * @return Next task from the pool queue
     * @throws InterruptedException 
     */
    public Runnable nextTask() throws InterruptedException {  
        synchronized(tasks) {
            while (tasks.isEmpty()) {
                tasks.wait();
            }
            return tasks.poll();
        }
    }
    
    /**
     * Start all threads in this pool.
     */
    public void start() {
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new LifeThread(this);
        }        
        Arrays.stream(threads).forEach(t -> t.start());
    }
}
