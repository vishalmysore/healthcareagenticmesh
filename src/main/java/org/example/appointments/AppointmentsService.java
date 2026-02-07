package org.example.appointments;

import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Agent(groupName = "appointmentsOperations")
@Service
public class AppointmentsService {

    @Action(description = "Schedule a new medical appointment")
    public String scheduleAppointment(String patientId, String doctorName, String appointmentType, String preferredDate) {
        String appointmentId = "APT-" + (System.currentTimeMillis() % 100000);
        return String.format("Appointment scheduled successfully!\n" +
               "Appointment ID: %s\n" +
               "Patient ID: %s\n" +
               "Doctor: Dr. %s\n" +
               "Type: %s\n" +
               "Date & Time: %s at 10:00 AM\n" +
               "Duration: 30 minutes\n" +
               "Location: Medical Center - Room 205\n" +
               "Status: CONFIRMED\n" +
               "Reminder: SMS will be sent 24 hours before appointment", 
               appointmentId, patientId, doctorName, appointmentType, preferredDate);
    }

    @Action(description = "Get appointment details")
    public String getAppointmentDetails(String appointmentId) {
        return String.format("Appointment Details for %s:\n" +
               "=================================\n" +
               "Patient: John Doe (PT-12345)\n" +
               "Doctor: Dr. Sarah Johnson\n" +
               "Specialty: Cardiology\n" +
               "Type: Follow-up Consultation\n" +
               "Date: 2026-02-10 at 10:00 AM\n" +
               "Duration: 30 minutes\n" +
               "Location: Medical Center - Room 205\n" +
               "Status: CONFIRMED\n" +
               "Pre-appointment Instructions: Fasting required (8 hours)", 
               appointmentId);
    }

    @Action(description = "Cancel an appointment")
    public String cancelAppointment(String appointmentId, String reason) {
        return String.format("Appointment %s has been CANCELLED.\n" +
               "Reason: %s\n" +
               "Cancellation Date: %s\n" +
               "Cancellation Fee: Waived (>24 hours notice)\n" +
               "Status: CANCELLED\n" +
               "Note: You can reschedule by calling (555) 123-4567", 
               appointmentId, reason, java.time.LocalDateTime.now());
    }

    @Action(description = "Reschedule an existing appointment")
    public String rescheduleAppointment(String appointmentId, String newDate) {
        return String.format("Appointment %s has been RESCHEDULED.\n" +
               "Original Date: 2026-02-10 at 10:00 AM\n" +
               "New Date: %s at 2:00 PM\n" +
               "Rescheduled On: %s\n" +
               "Confirmation SMS sent to patient\n" +
               "Status: CONFIRMED", 
               appointmentId, newDate, java.time.LocalDateTime.now());
    }

    @Action(description = "Get available appointment slots")
    public String getAvailableSlots(String doctorName, String date) {
        return String.format("Available Slots for Dr. %s on %s:\n" +
               "=================================\n" +
               "Morning Slots:\n" +
               "  9:00 AM - 9:30 AM (Available)\n" +
               "  10:00 AM - 10:30 AM (Available)\n" +
               "  11:00 AM - 11:30 AM (Booked)\n" +
               "Afternoon Slots:\n" +
               "  2:00 PM - 2:30 PM (Available)\n" +
               "  3:00 PM - 3:30 PM (Available)\n" +
               "  4:00 PM - 4:30 PM (Available)\n" +
               "To book, call (555) 123-4567 or use the patient portal", 
               doctorName, date);
    }

    @Action(description = "Get patient upcoming appointments")
    public String getUpcomingAppointments(String patientId) {
        return String.format("Upcoming Appointments for Patient %s:\n\n", patientId) +
               "1. APT-12345 - Dr. Johnson (Cardiology) - Feb 10, 2026 10:00 AM\n" +
               "2. APT-12346 - Dr. Smith (General) - Feb 15, 2026 2:00 PM\n" +
               "3. APT-12347 - Dr. Lee (Lab Work) - Feb 20, 2026 9:00 AM\n" +
               "Total Upcoming: 3 appointments";
    }

    @Action(description = "Check in patient for appointment")
    public String checkInPatient(String appointmentId, String patientId) {
        return String.format("Patient Check-In Successful!\n" +
               "Appointment ID: %s\n" +
               "Patient ID: %s\n" +
               "Check-In Time: %s\n" +
               "Status: CHECKED IN\n" +
               "Estimated Wait Time: 10 minutes\n" +
               "Please have a seat in the waiting area. You will be called shortly.", 
               appointmentId, patientId,
               LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Action(description = "Send appointment reminder")
    public String sendAppointmentReminder(String appointmentId) {
        return String.format("Appointment Reminder Sent!\n" +
               "Appointment ID: %s\n" +
               "Reminder Type: SMS + Email\n" +
               "Sent At: %s\n" +
               "Message: 'Reminder: You have an appointment with Dr. Johnson tomorrow at 10:00 AM'\n" +
               "Status: DELIVERED", 
               appointmentId,
               LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
