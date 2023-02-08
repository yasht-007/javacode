import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ThreadUtilize implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " (Start)");
        System.out.println(Thread.currentThread().getName() + " (End)");
    }
}

public class ThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i=0;i<3;i++){
            Runnable worker = new ThreadUtilize();
            executorService.execute(worker);
        }

        executorService.shutdown();
    }
}
