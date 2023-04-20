
import java.io.*;
import java.sql.*;
import java.time.Instant;
import java.util.Arrays;

public class BlobExample
{

    public static void main(String[] args) throws ClassNotFoundException
    {

        Connection connection = null;

        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/" + Const.DB_NAME + "?maxAllowedPacket=1000000000";

        FileOutputStream writer = null;

        FileInputStream inputStream = null;

        Statement statement = null;


        try
        {
            int length;

            connection = DriverManager.getConnection(url, Const.USERNAME, Const.PASSWORD);

            if (connection != null && !connection.isClosed())
            {

                statement = connection.createStatement();

                inputStream = new FileInputStream("/home/yash/Downloads/1GB.bin");

                statement.execute("CREATE TABLE DATA(id INTEGER NOT NULL, file LONGBLOB, time DECIMAL)");

                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO DATA VALUES (?,?,?)");

                byte[] buffer = new byte[100000000];


                while ((length = inputStream.read(buffer)) != -1)
                {

                    preparedStatement.setInt(1, 1);

                    preparedStatement.setBytes(2, Arrays.copyOfRange(buffer, 0, length));

                    preparedStatement.setLong(3, Instant.now().getEpochSecond());

                    preparedStatement.executeUpdate();
                }

                statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT file FROM DATA WHERE id=1 ORDER BY time;");

                writer = new FileOutputStream("/home/yash/1GB.bin", true);

                while (resultSet.next())
                {

                    InputStream stream = resultSet.getBlob("file").getBinaryStream();

                    while ((length = stream.read(buffer)) != -1)
                    {

                        writer.write(buffer, 0, length);

                        writer.flush();
                    }
                }

            }

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            try
            {

                if (connection != null)
                {
                    connection.close();
                }

                if (inputStream != null)
                {
                    inputStream.close();
                }

                if (writer != null)
                {
                    writer.close();
                }

                if (statement != null)
                {
                    statement.close();
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
}
