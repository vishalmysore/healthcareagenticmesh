package org.example.diagnostics;

import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import org.springframework.stereotype.Service;

@Agent(groupName = "diagnosticsOperations")
@Service
public class DiagnosticsService {

    @Action(description = "Order laboratory tests for a patient")
    public String orderLabTests(String patientId, String testType, String urgency) {
        String labOrderId = "LAB-" + (System.currentTimeMillis() % 100000);
        return String.format("Laboratory test ordered successfully!\n" +
               "Lab Order ID: %s\n" +
               "Patient ID: %s\n" +
               "Test Type: %s\n" +
               "Urgency: %s\n" +
               "Ordered By: Dr. Smith\n" +
               "Status: PENDING\n" +
               "Expected Results: %s\n" +
               "Lab Location: Medical Center - Lab Wing, Floor 2", 
               labOrderId, patientId, testType, urgency,
               urgency.equalsIgnoreCase("URGENT") ? "24 hours" : "3-5 business days");
    }

    @Action(description = "Get laboratory test results")
    public String getLabResults(String labOrderId) {
        return String.format("Laboratory Test Results\n" +
               "Lab Order ID: %s\n" +
               "=================================\n" +
               "Test: Complete Blood Count (CBC)\n" +
               "Status: COMPLETED\n" +
               "Result Date: %s\n\n" +
               "Results:\n" +
               "  White Blood Cells: 7.5 K/uL (Normal: 4.5-11.0)\n" +
               "  Red Blood Cells: 4.8 M/uL (Normal: 4.5-5.5)\n" +
               "  Hemoglobin: 14.5 g/dL (Normal: 13.5-17.5)\n" +
               "  Platelets: 250 K/uL (Normal: 150-400)\n" +
               "  Hematocrit: 42%% (Normal: 38-50%%)\n\n" +
               "Overall: NORMAL RANGE\n" +
               "Reviewed By: Dr. Sarah Johnson\n" +
               "Notes: All values within normal limits", 
               labOrderId, java.time.LocalDate.now());
    }

    @Action(description = "Order medical imaging scan")
    public String orderImagingScan(String patientId, String scanType, String bodyPart, String indication) {
        String imagingOrderId = "IMG-" + (System.currentTimeMillis() % 100000);
        return String.format("Medical imaging scan ordered!\n" +
               "Imaging Order ID: %s\n" +
               "Patient ID: %s\n" +
               "Scan Type: %s\n" +
               "Body Part: %s\n" +
               "Clinical Indication: %s\n" +
               "Status: SCHEDULED\n" +
               "Scheduled Date: %s at 2:00 PM\n" +
               "Location: Radiology Department - Room 105\n" +
               "Preparation Instructions: No food 4 hours before scan", 
               imagingOrderId, patientId, scanType, bodyPart, indication,
               java.time.LocalDate.now().plusDays(2));
    }

    @Action(description = "Get imaging scan results")
    public String getImagingResults(String imagingOrderId) {
        return String.format("Medical Imaging Results\n" +
               "Imaging Order ID: %s\n" +
               "=================================\n" +
               "Scan Type: Chest X-Ray\n" +
               "Scan Date: %s\n" +
               "Status: COMPLETED\n\n" +
               "FINDINGS:\n" +
               "Lungs: Clear, no infiltrates or effusions\n" +
               "Heart: Normal size and contour\n" +
               "Mediastinum: Normal width\n" +
               "Bones: No acute fractures identified\n\n" +
               "IMPRESSION:\n" +
               "Normal chest radiograph. No acute cardiopulmonary disease.\n\n" +
               "Radiologist: Dr. Michael Chen, MD\n" +
               "Report Finalized: %s", 
               imagingOrderId, java.time.LocalDate.now(),
               java.time.LocalDateTime.now());
    }

    @Action(description = "Analyze diagnostic trends")
    public String analyzeDiagnosticTrends(String patientId, String diagnosticType) {
        return String.format("Diagnostic Trend Analysis for Patient %s\n" +
               "=================================\n" +
               "Analysis Type: %s\n" +
               "Period: Last 12 months\n\n" +
               "Trend Summary:\n" +
               "  Blood Glucose: Stable (95-110 mg/dL)\n" +
               "  Blood Pressure: Improving (now 120/80, was 140/90)\n" +
               "  Cholesterol: Decreased (now 180, was 220)\n" +
               "  HbA1c: Stable at 5.8%% (Normal)\n\n" +
               "Key Observations:\n" +
               "- Medication adherence showing positive results\n" +
               "- Lifestyle modifications effective\n" +
               "- Continue current treatment plan\n\n" +
               "Next Review: %s", 
               patientId, diagnosticType,
               java.time.LocalDate.now().plusMonths(3));
    }

    @Action(description = "Create diagnostic report")
    public String createDiagnosticReport(String patientId, String diagnosisType) {
        return String.format("Diagnostic Report Generated\n" +
               "Patient ID: %s\n" +
               "Report Type: %s\n" +
               "=================================\n" +
               "Report ID: DIAG-RPT-%d\n" +
               "Generated: %s\n\n" +
               "CLINICAL SUMMARY:\n" +
               "Chief Complaint: Annual physical examination\n" +
               "Diagnosis: Patient in good general health\n\n" +
               "DIAGNOSTIC FINDINGS:\n" +
               "- Laboratory: All values within normal limits\n" +
               "- Imaging: No abnormalities detected\n" +
               "- Physical Exam: Unremarkable\n\n" +
               "RECOMMENDATIONS:\n" +
               "- Continue healthy lifestyle\n" +
               "- Follow-up in 12 months\n" +
               "- Maintain current medications\n\n" +
               "Prepared By: Dr. Sarah Johnson, MD\n" +
               "Report Status: FINALIZED", 
               patientId, diagnosisType, System.currentTimeMillis() % 100000,
               java.time.LocalDateTime.now());
    }

    @Action(description = "Schedule follow-up diagnostic tests")
    public String scheduleFollowUpTests(String patientId, String previousTestId) {
        return String.format("Follow-up Diagnostic Tests Scheduled\n" +
               "Patient ID: %s\n" +
               "Previous Test ID: %s\n" +
               "=================================\n" +
               "Follow-up Test ID: LAB-%d\n" +
               "Scheduled Date: %s at 9:00 AM\n" +
               "Test Type: Repeat Complete Metabolic Panel\n" +
               "Reason: Monitor previous abnormal findings\n" +
               "Location: Medical Center - Lab Wing\n" +
               "Status: SCHEDULED\n" +
               "Preparation: Fasting required (12 hours)", 
               patientId, previousTestId, System.currentTimeMillis() % 100000,
               java.time.LocalDate.now().plusWeeks(2));
    }
}
