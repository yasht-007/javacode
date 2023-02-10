
class Data {
   volatile int sharedVariable = 0;

    void incrementCounter() {
        for (int index = 0; index < 10000; index++) {
            sharedVariable++;
        }
    }

    void decrementCounter() {
        for (int index = 0; index < 10000; index++) {
            sharedVariable--;
        }
    }
}

class UpCounter extends Thread {

    Data data;
    public UpCounter(Data data){
        this.data=data;
    }
    @Override
    public void run() {
        data.incrementCounter();
    }
}

class DownCounter extends Thread {
    Data data;
    public DownCounter(Data data){
        this.data=data;
    }

    @Override
    public void run() {
        data.decrementCounter();
    }
}

public class VolatileExample {
    public static void main(String[] args) {
        Data data = new Data();
        UpCounter upCounter = new UpCounter(data);
        DownCounter downCounter = new DownCounter(data);

        upCounter.start();
        downCounter.start();

        System.out.println(data.sharedVariable);
    }
}
