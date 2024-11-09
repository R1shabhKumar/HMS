import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Appointment {
    private Connection connection;
    private Scanner scanner;
    private Patient patient;
    private Doctor doctor;

    public Appointment(Connection connection,Scanner scanner,Patient patient,Doctor doctor){
        this.connection=connection;
        this.scanner=scanner;
        this.patient=patient;
        this.doctor=doctor;
    }
    

    public void bookAppointment(/*Patient patient, Doctor doctor*/){
        System.out.print("  Enter Patient Id: ");
        int patientId = scanner.nextInt();
        System.out.print("  Enter Doctor Id: ");
        int doctorId = scanner.nextInt();
        System.out.print("  Enter Appointment Date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();
        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
            if(checkDoctorAvailability(doctorId,appointmentDate)){
                String query = "INSERT INTO Appointments(PATIENT_ID,DOCTOR_ID,APPOINTMENT_DATE) VALUES (?,?,?)";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2,doctorId);
                    preparedStatement.setString(3, appointmentDate);
                    int rowsAffected = preparedStatement.executeUpdate();

                    if(rowsAffected>0){
                        System.out.println("  SUCCESS!!  Appointment booked.");
                    }else{
                        System.out.println("  Failed to book appointment!!!");
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("  Doctor isn't available on this date!!!");
            }
        }else{
            System.out.println("  Either doctor or patient doesn't exist!!!");
        }
    }
    
    public boolean checkDoctorAvailability(int doctorId,String appointmentDate){
        String query = "SELECT COUNT(*) FROM Appointments WHERE DOCTOR_ID = ? AND APPOINTMENT_DATE = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count==0){
                    return true;
                }else{
                    return false;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
            return false;

    }
}
