import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CryptoCurrency
{
    public static void main(String[] args)
    {
        java.sql.Connection connection = DbConnection.connect();

        Statement statement = null;

        PreparedStatement preparedStatement = null;

        ResultSet result = null;

        try
        {

            if (connection != null && !connection.isClosed())
            {
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                final String CRYPTO_TABLE = "CREATE TABLE CRYPTOCURRENCY " +
                        "(id INTEGER NOT NULL," +
                        " name VARCHAR(255), " +
                        " price INTEGER, " +
                        "PRIMARY KEY ( id ))";


                statement.executeUpdate(CRYPTO_TABLE);

                preparedStatement = connection.prepareStatement("INSERT INTO CRYPTOCURRENCY " +
                                                                        "VALUES(?,?,?)");

                preparedStatement.setInt(1, 1);

                preparedStatement.setString(2, "Bitcoin");

                preparedStatement.setInt(3, 28000);

                preparedStatement.addBatch();

                preparedStatement.setInt(1, 2);

                preparedStatement.setString(2, "Ethereum");

                preparedStatement.setInt(3, 2000);

                preparedStatement.addBatch();

                preparedStatement.setInt(1, 3);

                preparedStatement.setString(2, "Solana");

                preparedStatement.setInt(3, 23);

                preparedStatement.addBatch();

                preparedStatement.executeBatch();

                result = statement.executeQuery("SELECT * FROM CRYPTOCURRENCY");

                System.out.println("After Insertion : ");

                while (result.next())
                {
                    System.out.println(result.getInt(1) + "  " + result.getString(2) + "  " + result.getString(3));
                }

                System.out.println("\nMoving cursor to third row : "+ result.absolute(3));

                System.out.println("Position of pointer : "+result.getRow());

                System.out.println("\nMoving cursor back by 3 rows : "+ result.relative(-2)+"\n");

                System.out.println("Position of pointer after relative : "+result.getRow()+"\n");

                PreparedStatement update = connection.prepareStatement("update CRYPTOCURRENCY set price=? where id=?");

                update.setInt(1, 3000);

                update.setInt(2, 2);

                update.executeUpdate();

                System.out.println("After Updation : ");

                result = preparedStatement.executeQuery("SELECT * FROM CRYPTOCURRENCY");

                while (result.next())
                {
                    System.out.println(result.getInt(1) + "  " + result.getString(2) + "  " + result.getString(3));
                }

                System.out.println();

                statement.execute("DELETE FROM CRYPTOCURRENCY WHERE name='Solana'");

                result = preparedStatement.executeQuery("SELECT * FROM CRYPTOCURRENCY");

                System.out.println("After Deletion : ");

                while (result.next())
                {
                    System.out.println(result.getInt(1) + "  " + result.getString(2) + "  " + result.getString(3));
                }


//                statement.addBatch("INSERT INTO CRYPTOCURRENCY " +
//                        "VALUES(1,'BITCOIN',28000);");
//
//                statement.addBatch("INSERT INTO CRYPTOCURRENCY " +
//                        "VALUES(2,'ETHEREUM',2000);");
//
//                statement.addBatch("INSERT INTO CRYPTOCURRENCY " +
//                        "VALUES(3,'SOLANA',23);");
//
//                statement.executeBatch();

//                statement.clearBatch();

//                preparedStatement.executeBatch();
//
//                 result = preparedStatement.executeQuery("SELECT * FROM CRYPTOCURRENCY");
//

//
//                preparedStatement.addBatch();
//
                statement.executeUpdate("DROP TABLE CRYPTOCURRENCY");

            }

            else
            {
                System.err.println("Error Connecting DB");
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

                preparedStatement.close();

                statement.close();

                connection.close();
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
}
