import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FinancialAssesDashboard {

    public static class PricesContainer {

        private Lock lockObject = new ReentrantLock();
        private double bitcoinPrice;
        private double ethereumPrice;
        private double solanaPrice;


        public Lock getLockObject() {
            return lockObject;
        }

        public void setLockObject(Lock lockObject) {
            this.lockObject = lockObject;
        }

        public double getBitcoinPrice() {
            return bitcoinPrice;
        }

        public void setBitcoinPrice(double bitcoinPrice) {
            this.bitcoinPrice = bitcoinPrice;
        }

        public double getEthereumPrice() {
            return ethereumPrice;
        }

        public void setEthereumPrice(double ethereumPrice) {
            this.ethereumPrice = ethereumPrice;
        }

        public double getSolanaPrice() {
            return solanaPrice;
        }

        public void setSolanaPrice(double solanaPrice) {
            this.solanaPrice = solanaPrice;
        }

        public double getCardanoPrice() {
            return cardanoPrice;
        }

        public void setCardanoPrice(double cardanoPrice) {
            this.cardanoPrice = cardanoPrice;
        }

        private double cardanoPrice;
    }

    public static class PriceUpdater extends Thread {

        private PricesContainer pricesContainer;
        private Random random = new Random();

        public PriceUpdater(PricesContainer pricesContainer) {
            this.pricesContainer = pricesContainer;
        }

        @Override
        public void run() {
            System.out.println("Current running thread: "+Thread.currentThread().getName());
            pricesContainer.getLockObject().lock();
            try {
                pricesContainer.setBitcoinPrice(random.nextInt(20000));
                pricesContainer.setEthereumPrice(random.nextInt(2000));
                pricesContainer.setSolanaPrice(random.nextInt(500));
                pricesContainer.setCardanoPrice(random.nextDouble());
            } finally {
                pricesContainer.getLockObject().unlock();
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        PricesContainer pricesContainer = new PricesContainer();
        PriceUpdater priceUpdater = new PriceUpdater(pricesContainer);
        PriceUpdater priceUpdater1 = new PriceUpdater(pricesContainer);

        priceUpdater.start();
        priceUpdater1.start();
        priceUpdater.join();
        priceUpdater1.join();


        if (pricesContainer.getLockObject().tryLock()) {
            try {
                System.out.println("BTC : " + pricesContainer.getBitcoinPrice());
                System.out.println("ETH : " + pricesContainer.getEthereumPrice());
                System.out.println("SOL : " + pricesContainer.getSolanaPrice());
                System.out.println("ADA : " + pricesContainer.getCardanoPrice());

            } finally {
                pricesContainer.getLockObject().unlock();
            }
        }

    }
}
