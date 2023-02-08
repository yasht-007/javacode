import java.util.Set;

public class ThreadStatesExample extends Thread {
    public void run (){
        System.out.println("Current Thread : "+Thread.currentThread().getName() + " "+ Thread.currentThread().getState());
        try {
            Thread.sleep(300);
            System.out.println("Current Thread : "+Thread.currentThread().getName() + " "+ Thread.currentThread().getState());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ThreadStatesExample t1= new ThreadStatesExample();
        ThreadStatesExample t2= new ThreadStatesExample();

        System.out.println("Current Thread : "+Thread.currentThread().getName()+ " "+ Thread.currentThread().getState());

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(t1.getState());
        System.out.println(t2.getState());
        System.out.println("Current Thread  after t1 starts: "+Thread.currentThread().getName()+ " "+ Thread.currentThread().getState());

    }
}
