import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPoolManager {

    private BlockingQueue<Runnable> taskQueue = null;
    private final List<PoolThread> threads = new ArrayList<PoolThread>();
    private boolean isStopped = false;

    public ThreadPoolManager(int noOfThreads, int maxNoOfTasks) {
        taskQueue = new ArrayBlockingQueue<Runnable>(maxNoOfTasks);

        for (int i = 0; i < noOfThreads; i++) {
            threads.add(new PoolThread(taskQueue));
        }
        for (PoolThread thread : threads) {
            thread.start();
        }
    }

    public synchronized void execute(Runnable task) throws Exception {
        if (this.isStopped)
            throw new IllegalStateException("ThreadPool is stopped");

        this.taskQueue.put(task);
    }

    public synchronized void stop() {
        this.isStopped = true;
        for (PoolThread thread : threads) {
            thread.doStop();
        }
    }

    public class PoolThread extends Thread {

        private BlockingQueue<Runnable> taskQueue = null;
        private boolean isStopped = false;

        public PoolThread(BlockingQueue<Runnable> queue) {
            taskQueue = queue;
        }

        @Override
        public void run() {
            while (!isStopped()) {
                try {
                    Runnable runnable = taskQueue.take();
                    runnable.run();
                } catch (Exception e) {
                    // log or otherwise report exception,
                    // but keep pool thread alive.
                }
            }
        }

        public synchronized void doStop() {
            isStopped = true;
            this.interrupt(); // break pool thread out of dequeue() call.
        }

        public synchronized boolean isStopped() {
            return isStopped;
        }
    }
}