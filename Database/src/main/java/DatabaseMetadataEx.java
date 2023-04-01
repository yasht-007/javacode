import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class DatabaseMetadataEx
{
    public static void main(String[] args)
    {
        Connection connection = DbConnection.connect();

        try
        {
            if (connection!= null && !connection.isClosed())
            {
                DatabaseMetaData metaData =  connection.getMetaData();

                System.out.println(metaData.getDriverName());

                System.out.println(metaData.getDriverVersion());

                System.out.println(metaData.getUserName());

                System.out.println(metaData.getURL());

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
                if (connection != null) connection.close();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

    }
}
