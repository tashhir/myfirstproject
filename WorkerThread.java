public class WorkerThread<E> implements Runnable {
    private final int workerNumber;
    private E item;

    WorkerThread(int number) {
        workerNumber = number;
    }

    WorkerThread(int number, E item) {
        workerNumber = number;
        this.item = item;
    }

    @Override
    public void run() {
        if (this.item != null) {
            System.out.println("\n The Thread : " + item.toString());
        }
        for (int i = 0; i <= 5; i++) {
            System.out.println("Worker number: " + workerNumber
                    + ", sub-task completed: " + i * 20 + "% Thread pool: "
                    + Thread.currentThread().getName());
            try {
                Thread.sleep((int) (Math.random() * 1000));
            } catch (InterruptedException e) {
            }
        }
    }

}