public class ThreadPoolManagerTest {
    public static void main(String[] args) throws Exception {
        int numWorkers = Integer.parseInt(args[0]);
        int threadPoolSize = Integer.parseInt(args[1]);

        ThreadPoolManager poolManager = new ThreadPoolManager(threadPoolSize,
                1000);
        WorkerThread<String>[] workers = new WorkerThread[numWorkers];
        for (int i = 0; i < numWorkers; i++) {
            workers[i] = new WorkerThread<String>(i, " thread_name_" + i);

            poolManager.execute(workers[i]);
        }
    }
}
