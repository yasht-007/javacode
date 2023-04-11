package Utility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection implements AutoCloseable
{
    Socket socket;
    DataInputStream connectionInput;
    DataOutputStream connectionOutput;

    public Connection(String host, int port) throws IOException
    {

        socket = new Socket(host, port);

        connectionInput = new DataInputStream(socket.getInputStream());

        connectionOutput = new DataOutputStream(socket.getOutputStream());
    }

    public String read() throws IOException
    {
        return connectionInput.readUTF();
    }

    public void write(String message) throws IOException
    {
        connectionOutput.writeUTF(message);

        connectionOutput.flush();
    }


    @Override
    public void close() throws Exception
    {
        socket.close();

        connectionOutput.close();

        connectionInput.close();
    }
}
