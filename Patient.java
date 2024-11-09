import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient{
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection,Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        System.out.print("  Enter Patient name: ");
        String name = scanner.next();
        System.out.print("  Enter age: ");
        int age = scanner.nextInt();
        System.out.print("  Enter Gender: ");
        String gender = scanner.next();

        try{
            String query = "INSERT INTO Patient(NAME,AGE,GENDER) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows>0){
                System.out.println("  Patient data added successfully!!");
            }else{
                System.out.println("  Failed to add patient data!!");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatient(){
        String query = "SELECT * FROM Patient";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("PATIENT: ");
            System.out.println("+------+--------------------------+-------+--------------+");
            System.out.println("|  Pid  |     NAME                |  AGE   |   GENDER    |");
            System.out.println("+------+--------------------------+-------+--------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("Pid");
                String name = resultSet.getString("NAME");
                int age = resultSet.getInt("AGE");
                String gender = resultSet.getString("GENDER");
                System.out.printf("|%-7s|%-25s|%-8s|%-13s|\n",id,name,age,gender);
                System.out.println("+------+--------------------------+-------+--------------+");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id){
        String query = "SELECT * FROM Patient WHERE Pid = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
            return false;    
    }
}