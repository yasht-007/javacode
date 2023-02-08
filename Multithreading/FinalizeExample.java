public class FinalizeExample {
    public void finalize() {
        System.out.println("object is garbage collected");
    }


    public static void main(String[] args) {
       FinalizeExample f1 = new FinalizeExample();
       FinalizeExample f2 = new FinalizeExample();

       f1= null;
       f2=f1;
       System.gc();
    }
}
