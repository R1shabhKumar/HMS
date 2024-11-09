import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Doctor {
    private Connection connection;
    public Doctor(Connection connection){
        this.connection = connection;
    }

    public void viewDoctor(){
        String query = "SELECT * FROM Doctor";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("DOCTOR: ");
            System.out.println("+------+--------------------------+---------------------+");
            System.out.println("|  Did  |     NAME                |  SPECIALIZATION     |");
            System.out.println("+------+--------------------------+---------------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("Did");
                String name = resultSet.getString("NAME");
                String specialization = resultSet.getString("SPECIALIZATION");
                System.out.printf("|%-7s|%-25s|%-21s|\n",id,name,specialization);
                System.out.println("+------+--------------------------+---------------------+");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id){
        String query = "SELECT * FROM Doctor WHERE Did = ?";
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
