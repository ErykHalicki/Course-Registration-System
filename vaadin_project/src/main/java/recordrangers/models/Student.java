package recordrangers.models;
import java.sql.Date;
import java.sql.Timestamp;


 public class Student extends User {
     private int studentId;
     private Date enrollment_date;
     private Status status;

     public Student(){
         super();
     }
     public Student(int userId, String firstName, String lastName, String email, String password, byte[] profilePicture, Timestamp timeCreated, Timestamp timeUpdated, Date enrollment_date, Status status){
         super(userId, firstName,lastName, email, password, UserType.STUDENT, profilePicture, timeCreated, timeUpdated);
         this.studentId = userId;
         this.enrollment_date = enrollment_date;
         this.status = status;
     }
     public enum Status{
         ACTIVE, INACTIVE, GRADUATED
     }
     public int getStudentId() {
         return studentId;
     }
     public void setStudentId(int studentId) {
         this.studentId = studentId;
     }
     public Date getEnrollment_date() {
         return enrollment_date;
     }
     public void setEnrollment_date(Date enrollment_date) {
         this.enrollment_date = enrollment_date;
     }
     public Status getStatus() {
         return status;
     }
     public void setStatus(Status status) {
         this.status = status;
     }
     @Override
     public String toString() {
         return "Student{" +
                 "studentId=" + studentId +
                 ", enrollment_date=" + enrollment_date +
                 ", status=" + status +
                 '}';
     }

 }
