package futurepromise;

import io.vertx.core.Vertx;
import io.vertx.core.file.FileSystem;

public class FileRead
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        FileSystem fs = vertx.fileSystem();

        String s;

//        fs.readFile("/home/yash/iop.txt")
    }
}
