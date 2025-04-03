package recordrangers.services;

import recordrangers.models.LabSession;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class LabSessionDAO {

	public static List<LabSession> getAllLabSessions() throws SQLException {
        List<LabSession> labSessions = new ArrayList<>();
        
        // First, fetch all labs from the Labs table
        String labsSql = "SELECT lab_id, lab_name FROM Labs";
        
        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(labsSql);
             ResultSet labsResultSet = pstmt.executeQuery()) {
            
            while (labsResultSet.next()) {
                int labId = labsResultSet.getInt("lab_id");
                String labName = labsResultSet.getString("lab_name");
                
                // Now, fetch the lab sessions for each lab
                String sessionsSql = "SELECT ls.section_id, ls.lab_id, ls.capacity, ls.days, ls.start_time, ls.end_time, ls.location " +
                                     "FROM LabSection ls " +
                                     "WHERE ls.lab_id = ?";
                
                try (PreparedStatement sessionStmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sessionsSql)) {
                    sessionStmt.setInt(1, labId);
                    
                    try (ResultSet sessionsResultSet = sessionStmt.executeQuery()) {
                        while (sessionsResultSet.next()) {
                            int sectionId = sessionsResultSet.getInt("section_id");
                            int capacity = sessionsResultSet.getInt("capacity");
                            String days = sessionsResultSet.getString("days");
                            LocalTime startTime = sessionsResultSet.getTime("start_time").toLocalTime();
                            LocalTime endTime = sessionsResultSet.getTime("end_time").toLocalTime();
                            String location = sessionsResultSet.getString("location");
                            
                            // Create a LabSession object for each session
                            LabSession labSession = new LabSession(sectionId, labId, capacity, days, startTime, endTime, location, labName);
                            labSessions.add(labSession);
                        }
                    }
                }
            }
        }
        
        return labSessions;
    }
    // Get all lab sessions for a specific lab, including labName
    public static List<LabSession> getLabSessionsByLabId(int labId) throws SQLException {
        List<LabSession> labSessions = new ArrayList<>();
        String sql = "SELECT ls.section_id, ls.lab_id, ls.capacity, ls.days, ls.start_time, ls.end_time, ls.location, l.lab_name " +
                     "FROM LabSection ls " +
                     "JOIN Labs l ON ls.lab_id = l.lab_id " +
                     "WHERE ls.lab_id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, labId);
            try (ResultSet rst = pstmt.executeQuery()) {
                while (rst.next()) {
                    int sectionId = rst.getInt("section_id");
                    int capacity = rst.getInt("capacity");
                    String days = rst.getString("days");
                    LocalTime startTime = rst.getTime("start_time").toLocalTime();
                    LocalTime endTime = rst.getTime("end_time").toLocalTime();
                    String location = rst.getString("location");
                    String labName = rst.getString("lab_name");  // Get the lab name from Labs table

                    LabSession labSession = new LabSession(sectionId, labId, capacity, days, startTime, endTime, location, labName);
                    labSessions.add(labSession);
                }
            }
        }
        return labSessions;
    }

    // Get lab sessions by student ID, including labName
    public static List<LabSession> getRegisteredLabSections(int studentId) throws SQLException {
        List<LabSession> labSessions = new ArrayList<>();
        String sql = "SELECT ls.section_id, ls.lab_id, ls.capacity, ls.days, ls.start_time, ls.end_time, ls.location, l.lab_name " +
                     "FROM LabSection ls " +
                     "JOIN LabEnrollment le ON ls.section_id = le.section_id " +
                     "JOIN Labs l ON ls.lab_id = l.lab_id " +
                     "WHERE le.student_id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            try (ResultSet rst = pstmt.executeQuery()) {
                while (rst.next()) {
                    int sectionId = rst.getInt("section_id");
                    int labId = rst.getInt("lab_id");
                    int capacity = rst.getInt("capacity");
                    String days = rst.getString("days");
                    LocalTime startTime = rst.getTime("start_time").toLocalTime();
                    LocalTime endTime = rst.getTime("end_time").toLocalTime();
                    String location = rst.getString("location");
                    String labName = rst.getString("lab_name");  // Get the lab name from Labs table

                    LabSession labSession = new LabSession(sectionId, labId, capacity, days, startTime, endTime, location, labName);
                    labSessions.add(labSession);
                }
            }
        }
        return labSessions;
    }
}