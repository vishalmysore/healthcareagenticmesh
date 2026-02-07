package org.example.patientrecords;

import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import org.springframework.stereotype.Service;

@Agent(groupName = "patientRecordsOperations")
@Service
public class PatientRecordsService {

    @Action(description = "Create a new patient record")
    public String createPatientRecord(String patientName, int age, String bloodType, String address) {
        String patientId = "PT-" + (System.currentTimeMillis() % 100000);
        return String.format("Patient record created successfully!\n" +
               "Patient ID: %s\n" +
               "Name: %s\n" +
               "Age: %d years\n" +
               "Blood Type: %s\n" +
               "Address: %s\n" +
               "Status: ACTIVE\n" +
               "Created: %s", 
               patientId, patientName, age, bloodType, address,
               java.time.LocalDate.now());
    }

    @Action(description = "Get patient medical history")
    public String getPatientHistory(String patientId) {
        return String.format("Medical History for Patient %s:\n" +
               "=================================\n" +
               "Name: John Doe\n" +
               "Age: 45 years\n" +
               "Blood Type: O+\n" +
               "Allergies: Penicillin, Peanuts\n" +
               "Chronic Conditions: Type 2 Diabetes, Hypertension\n" +
               "Current Medications: Metformin 500mg, Lisinopril 10mg\n" +
               "Last Visit: 2026-01-15\n" +
               "Emergency Contact: Jane Doe (555-1234)", 
               patientId);
    }

    @Action(description = "Update patient information")
    public String updatePatientInfo(String patientId, String fieldName, String newValue) {
        return String.format("Patient record %s updated successfully.\n" +
               "Field Updated: %s\n" +
               "New Value: %s\n" +
               "Updated: %s\n" +
               "Status: CONFIRMED", 
               patientId, fieldName, newValue, java.time.LocalDateTime.now());
    }

    @Action(description = "Add medical note to patient record")
    public String addMedicalNote(String patientId, String noteType, String note) {
        return String.format("Medical note added to Patient %s:\n" +
               "Note Type: %s\n" +
               "Note: %s\n" +
               "Added By: Dr. Smith\n" +
               "Timestamp: %s\n" +
               "Note ID: NOTE-%d", 
               patientId, noteType, note, java.time.LocalDateTime.now(),
               System.currentTimeMillis() % 100000);
    }

    @Action(description = "Get patient vital signs history")
    public String getVitalSigns(String patientId) {
        return String.format("Vital Signs for Patient %s:\n" +
               "=================================\n" +
               "Blood Pressure: 120/80 mmHg\n" +
               "Heart Rate: 72 bpm\n" +
               "Temperature: 98.6°F (37°C)\n" +
               "Respiratory Rate: 16 breaths/min\n" +
               "Oxygen Saturation: 98%%\n" +
               "Weight: 175 lbs (79.4 kg)\n" +
               "Height: 5'10\" (178 cm)\n" +
               "BMI: 25.1 (Normal)\n" +
               "Last Updated: %s", 
               patientId, java.time.LocalDateTime.now());
    }

    @Action(description = "Search for patients by name or ID")
    public String searchPatients(String searchTerm) {
        return String.format("Patient Search Results for '%s':\n\n", searchTerm) +
               "1. PT-12345 - John Doe - Age 45 - Last Visit: 2026-01-15\n" +
               "2. PT-12346 - Jane Doe - Age 38 - Last Visit: 2026-01-20\n" +
               "3. PT-12347 - Bob Smith - Age 52 - Last Visit: 2026-01-28\n" +
               "Total Results: 3";
    }

    @Action(description = "Get patient immunization records")
    public String getImmunizationRecords(String patientId) {
        return String.format("Immunization Records for Patient %s:\n" +
               "=================================\n" +
               "COVID-19: Moderna (3 doses) - Last: 2025-09-15\n" +
               "Influenza: Annual - Last: 2025-10-01\n" +
               "Tdap: Booster - Last: 2023-03-15\n" +
               "Hepatitis B: Complete Series (3 doses)\n" +
               "MMR: Complete (2 doses)\n" +
               "Next Due: Influenza (2026-10-01)", 
               patientId);
    }
}
