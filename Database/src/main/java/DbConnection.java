import java.sql.DriverManager;

public class DbConnection
{
    public static java.sql.Connection connect()
    {
        java.sql.Connection connection = null;

        try
        {

            String url = "jdbc:mysql://localhost:3306/" + Const.DB_NAME;

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, Const.USERNAME, Const.PASSWORD);

            return connection;

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return null;

    }
}
