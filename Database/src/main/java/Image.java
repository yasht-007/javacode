import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Reader;
import java.sql.*;

public class Image
{
    public static void main(String[] args)
    {
        Connection connection = DbConnection.connect();

        try
        {
            if (connection != null && !connection.isClosed())
            {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                statement.executeUpdate("create table IMAGES(name varchar(4000),picture blob)");

                PreparedStatement insertImage = connection.prepareStatement("insert into IMAGES values(?,?)");

                insertImage.setString(1, "Butterfly");

                FileInputStream inputStream = new FileInputStream(Image.class.getResource("image1.jpg").getPath());

                insertImage.setBinaryStream(2, inputStream, inputStream.available());

                int update = insertImage.executeUpdate();

                System.out.println(update + " rows affected");

                inputStream.close();

                insertImage.close();

                System.out.println("Now retrieving image...");

                ResultSet resultSet = statement.executeQuery("select * from IMAGES");

                resultSet.next();

                Blob blob = resultSet.getBlob(2);

                BufferedImage bufferedImage = ImageIO.read(blob.getBinaryStream());

                ImageIO.write(bufferedImage,"jpg",new File("ButterlyImage"));

                System.out.println("Image write success...");

                statement.executeUpdate("drop table IMAGES");

                statement.close();
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
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
}
