import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.nio.file.Paths;
public class images {
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/img_db";
        String user="root";
        String pass="deepak";
        String img_path="D:\\JDBC\\";
//        String query="insert into image_table(img_data) VALUES(?);";
        String query1="select img_data from image_table where img_id=?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try {
            Connection con=DriverManager.getConnection(url,user,pass);
            System.out.println("connection done!!!!");
//            FileInputStream fileInputStream=new FileInputStream(img_path);
//            byte[] image_data=new byte[fileInputStream.available()];
//            fileInputStream.read();
            PreparedStatement preparedStatement =con.prepareStatement(query1);
            preparedStatement.setInt(1,1);
//            int res=preparedStatement.executeUpdate( );
//            if(res>0){
//                System.out.println("image inserted succesfully!!");
//            }
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                byte[] image_data=resultSet.getBytes("img_data");
                String image_path = Paths.get(img_path, "extractedImage.jpg").toString();

                try (FileOutputStream fileOutputStream = new FileOutputStream(image_path)) {
                    fileOutputStream.write(image_data);
                    fileOutputStream.flush();
                    System.out.println("Image saved at: " + image_path);
                }
            }
            else {
                System.out.println("No image found with the given ID.");
            }
        }
        catch (SQLException | FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
