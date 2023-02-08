public class ShutdownHookExample extends Thread {
    public void run() {
        System.out.println("shut down hook task completed..");
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        ShutdownHookExample t0 = new ShutdownHookExample();
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(t0);
        runtime.addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Anonymous shutdown");
            }
        });
    }
}
