public class ThreadGroupExample extends Thread {
    ThreadGroupExample(ThreadGroup tg, String s) {
        super(tg, s);
    }

    public void run() {
        System.out.println("we are leaving");
//        System.out.println("Current Thread name " + Thread.currentThread().getName());
//        System.out.println("Current Thread group name " + Thread.currentThread().getThreadGroup());
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup threadGroup1 = new ThreadGroup("Group A");
        ThreadGroup threadGroup2 = new ThreadGroup("Group B");
        ThreadGroupExample t0 = new ThreadGroupExample(threadGroup1, "Thread 0");
        ThreadGroupExample t1 = new ThreadGroupExample(threadGroup1, "Thread 1");
        ThreadGroupExample t2 = new ThreadGroupExample(threadGroup2, "Thread 2");
        ThreadGroupExample t3 = new ThreadGroupExample(threadGroup2, "Thread 3");

        t0.start();
        System.out.println("Thread 0 " + t0.getThreadGroup().getName());
        if (t0.getThreadGroup() != null)
            System.out.println("Thread 0 Parent " + t0.getThreadGroup().getParent().getName());

        t1.start();
        System.out.println("Thread 1 " + t1.getThreadGroup().getName());
        if (t1.getThreadGroup() != null)
            System.out.println("Thread 1 Parent " + t1.getThreadGroup().getParent().getName());


        t2.start();
        System.out.println("Thread 2 " + t2.getThreadGroup().getName());
        if (t2.getThreadGroup() != null)
            System.out.println("Thread 2 Parent " + t2.getThreadGroup().getParent().getName());
        t3.start();
        System.out.println("Thread 3 " + t3.getThreadGroup().getName());
        if (t3.getThreadGroup() != null)
            System.out.println("Thread 3 Parent " + t3.getThreadGroup().getParent().getName());

    }
}
