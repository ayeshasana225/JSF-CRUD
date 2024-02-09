package db;

import java.sql.*;

public class DbConnection {
    private String dbURL = "jdbc:mysql://localhost:3306/students";
    private String username = "root";
    private String password = "";
    private Connection connection;
    public DbConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL,username,password);
            if(connection!=null){
                System.out.println("Success");
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertRecord(String student_name, String student_email, String student_password, String student_gender, String student_address){
        try {
            String sqlQuery = "insert into student_record (student_name, student_email, student_password, student_gender, student_address) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, student_name);
            preparedStatement.setString(2, student_email);
            preparedStatement.setString(3, student_password);
            preparedStatement.setString(4, student_gender);
            preparedStatement.setString(5, student_address);
            int noOfRowsInserted = preparedStatement.executeUpdate();
            if(noOfRowsInserted>0){
                System.out.println(noOfRowsInserted + " rows inserted!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRecord(String student_name,String student_email,String student_password,String student_gender,String student_address, int student_id){
        try {
            String sqlQuery = "update student_record set student_name=?, student_email=?, student_password=?, student_gender=?, student_address=? where student_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,student_name);
            preparedStatement.setString(2,student_email);
            preparedStatement.setString(3,student_password);
            preparedStatement.setString(4,student_gender);
            preparedStatement.setString(5,student_address);
            preparedStatement.setInt(6,student_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecord(int id){
        try {
            String sqlQuery = "DELETE from student_record where student_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getRecord(int id){
        try {
            String sqlQuery = "select * from student_record where student_id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,id);
            ResultSet result = preparedStatement.executeQuery();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getRecords(){
        try {
            String sqlQuery = "SELECT * from student_record";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sqlQuery);
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
