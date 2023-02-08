public class ThreadPriorityExample implements Runnable {
    public static void main(String[] args) {
        ThreadPriorityExample tpe = new ThreadPriorityExample();
        Thread t1 = new Thread(tpe);
        Thread t2 = new Thread(tpe);

        System.out.println("Main thread priority: "+ Thread.currentThread().getPriority());

        System.out.println("Thread 0 default priority : "+ t1.getPriority());
        System.out.println("Thread 1 default priority : "+ t2.getPriority());

        t1.setPriority(8);

        System.out.println("Thread 0 priority after setting: "+ t1.getPriority());

        t1.start();
        t2.start();

    }

    @Override
    public void run() {
        System.out.println("Name :" + Thread.currentThread().getName());
        System.out.println("Priority :" + Thread.currentThread().getPriority());
        System.out.println("Thread Group :" + Thread.currentThread().getThreadGroup());
    }
}
