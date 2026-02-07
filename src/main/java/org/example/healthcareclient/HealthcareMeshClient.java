package org.example.healthcareclient;

import io.github.vishalmysore.mesh.AgentCatalog;
import io.github.vishalmysore.mesh.AgenticMesh;
import lombok.extern.java.Log;

/**
 * Healthcare Mesh Client demonstrating integration of multiple healthcare domain agents
 */
@Log
public class HealthcareMeshClient {
    public static void main(String[] args) {
        log.info("Initializing Healthcare Agentic Mesh...");
        
        AgentCatalog agentCatalog = new AgentCatalog();
        
        // Add all healthcare domain agents to the mesh
        agentCatalog.addAgent("http://localhost:8871/"); // Patient Records Server
        agentCatalog.addAgent("http://localhost:8872/"); // Appointments Server
        agentCatalog.addAgent("http://localhost:8873/"); // Diagnostics Server
        agentCatalog.addAgent("http://localhost:8874/"); // Billing Server
        
        log.info("Healthcare Mesh initialized with 4 specialized agents");
        
        // Example: Complex healthcare workflow
        System.out.println("\n=== Healthcare Mesh Demo ===\n");
        
        // 1. Get patient information
        String patientInfo = agentCatalog.processQuery("Get medical history for patient ID PT-12345")
            .getTextResult();
        System.out.println("Patient Information:\n" + patientInfo);
        
        // 2. Schedule appointment
        String appointmentResult = agentCatalog.processQuery(
            "Schedule appointment for patient PT-12345 with Dr. Johnson for cardiology consultation on February 10, 2026")
            .getTextResult();
        System.out.println("\nAppointment Scheduling:\n" + appointmentResult);
        
        // 3. Order diagnostic tests
        String diagnosticResult = agentCatalog.processQuery(
            "Order complete blood count lab test for patient PT-12345, mark as urgent")
            .getTextResult();
        System.out.println("\nDiagnostic Order:\n" + diagnosticResult);
        
        // 4. Generate invoice
        String billingResult = agentCatalog.processQuery(
            "Generate invoice for patient PT-12345 for office visit consultation, amount $150")
            .getTextResult();
        System.out.println("\nBilling Invoice:\n" + billingResult);
        
        // Complex cross-domain query
        String complexQuery = agentCatalog.processQuery(
            "For patient PT-12345, get their medical history, check upcoming appointments, " +
            "review recent lab results, and show current account balance")
            .getTextResult();
        System.out.println("\nComplex Query Result:\n" + complexQuery);
        
        log.info("Healthcare Mesh demo with AgentCatalog completed");

        // Create AgenticMesh for pipeline processing
        AgenticMesh agenticMesh = new AgenticMesh(agentCatalog);
        
        System.out.println("\n=== AgenticMesh Pipeline Demo ===\n");

        String pipelineResult = agenticMesh.pipeLineMesh(
            "For patient PT-12345, get their medical history, check upcoming appointments, " +
            "review recent lab results, and show current account balance")
            .getTextResult();
        System.out.println("\nPipeline Result:\n" + pipelineResult);

        log.info("Healthcare Mesh demo with AgenticMesh completed");
    }
}
