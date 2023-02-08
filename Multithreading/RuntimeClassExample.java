import java.io.IOException;

public class RuntimeClassExample {
    public static void main(String[] args) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.availableProcessors());
        System.out.println(runtime.totalMemory());
        System.out.println(runtime.freeMemory() );
    }
}
