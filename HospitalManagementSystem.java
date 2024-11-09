import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static  String username; //= "root";
    private static  String password; //= "R1shabh_s1ngh";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        
        
        try{
            System.out.print("  Enter your username: ");
            username = scanner.nextLine();
            System.out.print("  Enter your password: ");
            password = scanner.nextLine();
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection,scanner);
            Doctor doctor = new Doctor(connection);
            Appointment appointment = new Appointment(connection, scanner, patient, doctor);
            while(true){
                System.out.println("  HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("|-----------------------------|");
                System.out.println("  1.Add patient");
                System.out.println("  2.View Patient");
                System.out.println("  3.View Doctors");
                System.out.println("  4.Book Appointment");
                System.out.println("  5.Exit...");
                System.out.println("|-----------------------------|");
                System.out.print("  Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        //Add patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        //View Patient
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        //View doctor
                        doctor.viewDoctor();
                        System.out.println();
                        break;
                    case 4:
                        //Book Appointment
                        appointment.bookAppointment();
                        System.out.println();
                        break;
                    case 5:
                        //Exit
                        System.out.println("  CODE BY RISHABH...");
                        connection.close();
                        scanner.close();
                        return;            
                    default:
                        System.out.println("  Enter valid choice!!!");
                        System.out.println();
                        break;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }
}
