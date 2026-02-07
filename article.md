# Building a Healthcare Agentic Mesh with Java: Transform Your EHR, Appointment, and Billing Systems into Intelligent AI Agents

## Introduction: The Future of Healthcare IT is Agentic

The healthcare industry is experiencing a digital transformation, and **Agentic Mesh Architecture** represents the cutting edge of this evolution. This guide demonstrates how to transform your existing healthcare services—Electronic Health Records (EHR), appointment scheduling, diagnostics, and billing—into intelligent, autonomous agents that collaborate seamlessly using Java, Spring Boot, and the A2A Java library.

**What You'll Learn:**
- Converting existing healthcare code into intelligent agents with minimal changes (just annotations!)
- Building an agentic mesh with Java 17 and Spring Boot 3.2.4
- Implementing complex medical workflows using agent orchestration
- Real-world use cases: patient intake, appointment scheduling, lab ordering, and billing automation

### Core Concepts Defined

Before diving in, here are the key technologies you'll use throughout:

- **MCP (Model Context Protocol)**: Standardized JSON-RPC 2.0 protocol for agent discovery and communication. Agents expose their capabilities via `tools/list` and execute actions via `tools/call` endpoints.

- **A2A (Agent-to-Agent Protocol)**: Extension of MCP enabling direct agent-to-agent messaging without centralized orchestration. Part of the A2A Java library.

- **AgentCatalog**: Java class that discovers agents, analyzes natural language queries, routes requests to appropriate agents, and aggregates results. Think of it as your intelligent load balancer.

- **AgenticMesh**: Advanced orchestration layer built on AgentCatalog. Handles multi-step workflows with sequential execution, context preservation, and dynamic routing based on intermediate results.

- **Tools4AI**: Annotation framework (`@Agent`, `@Action`) that makes any Spring service discoverable as an MCP-compliant agent without boilerplate code.

**Keywords**: Agentic Mesh, Healthcare AI, Java Agents, A2A Protocol, MCP, Spring Boot Agents, Electronic Health Records, Healthcare Automation, Medical Billing AI, Appointment Scheduling AI, Diagnostic Systems, Tools4AI, AgentCatalog, AgenticMesh

---

## Table of Contents

1. [What is an Agentic Mesh?](#what-is-an-agentic-mesh)
2. [Healthcare Agentic Mesh Architecture](#healthcare-agentic-mesh-architecture)
3. [Technology Stack](#technology-stack)
4. [Four Core Healthcare Agents](#four-core-healthcare-agents)
5. [Setting Up Your Healthcare Agentic Mesh](#setting-up-your-healthcare-agentic-mesh)
6. [Agent Implementation Deep Dive](#agent-implementation-deep-dive)
7. [Using AgentCatalog for Healthcare Workflows](#using-agentcatalog-for-healthcare-workflows)
8. [Using AgenticMesh for Complex Orchestration](#using-agenticmesh-for-complex-orchestration)
9. [Transform Your Existing Healthcare Code](#transform-your-existing-healthcare-code)
10. [Real-World Healthcare Workflows](#real-world-healthcare-workflows)
11. [Advanced Agent Communication Patterns](#advanced-agent-communication-patterns)
12. [Security and HIPAA Compliance](#security-and-hipaa-compliance)
13. [Testing and Validation](#testing-and-validation)
14. [Performance Optimization](#performance-optimization)
15. [Deployment Strategies](#deployment-strategies)
16. [Conclusion](#conclusion)

---

## What is an Agentic Mesh?

An **Agentic Mesh** is a distributed architecture where multiple specialized AI agents work together to accomplish complex tasks. Unlike traditional monolithic healthcare systems, an agentic mesh allows:

- **Autonomous Operation**: Each agent operates independently with its own domain expertise
- **Dynamic Collaboration**: Agents discover and communicate with each other automatically
- **Intelligent Routing**: Queries are routed to the appropriate agents based on intent
- **Scalability**: Add or remove agents without disrupting the entire system
- **Resilience**: If one agent fails, others continue to function

### Why Healthcare Needs Agentic Architecture

Healthcare systems are inherently complex with multiple specialized domains:
- **Patient Records**: Managing demographics, medical history, and vital signs
- **Appointments**: Scheduling, reminders, and visit coordination
- **Diagnostics**: Lab tests, imaging, and results management
- **Billing**: Insurance claims, payments, and revenue cycle management

Traditional tightly-coupled systems struggle with this complexity. An agentic mesh provides natural domain boundaries while enabling seamless collaboration.

---

## Healthcare Agentic Mesh Architecture

Our healthcare agentic mesh consists of **four specialized agents**, each running as an independent Spring Boot server:

```
┌─────────────────────────────────────────────────────────────┐
│                    Healthcare Mesh Client                    │
│              (AgentCatalog + AgenticMesh)                    │
└───────────────────────┬─────────────────────────────────────┘
                        │
        ┌───────────────┼───────────────┬──────────────┐
        │               │               │              │
        ▼               ▼               ▼              ▼
┌───────────────┐ ┌───────────┐ ┌──────────────┐ ┌─────────┐
│ Patient       │ │Appointments│ │ Diagnostics  │ │ Billing │
│ Records       │ │  Agent     │ │    Agent     │ │  Agent  │
│ Agent         │ │            │ │              │ │         │
│ Port 8871     │ │ Port 8872  │ │  Port 8873   │ │Port 8874│
└───────────────┘ └───────────┘ └──────────────┘ └─────────┘
```

### Communication Protocol

All agents communicate using the [MCP](#core-concepts-defined) and [A2A](#core-concepts-defined) protocols defined earlier, with JSON-RPC 2.0 messages over HTTP/REST.

### Patient Journey Example: Sarah's Annual Checkup

Let's follow a real patient scenario that demonstrates how the mesh works:

**Scenario**: Sarah Johnson, age 45, schedules her annual physical. Here's how the agentic mesh handles her complete visit:

1. **Intake (Patient Records Agent)**: Sarah's demographic information is captured, including her blood type (O+), address, and medical history. Her vital signs are recorded: BP 120/80, HR 72, Temp 98.6°F.

2. **Scheduling (Appointments Agent)**: The system checks Dr. Smith's availability on February 15, 2026, finds an open slot, schedules the appointment, and sends Sarah a confirmation with a reminder.

3. **Diagnostics (Diagnostics Agent)**: Dr. Smith orders routine labs (Complete Blood Count, Lipid Panel) and the system tracks the lab order LAB-12345 through collection and results.

4. **Billing (Billing Agent)**: After the visit, an invoice is generated for $350 (consultation $200 + labs $150). An insurance claim is automatically submitted to Sarah's provider (Blue Cross) for $300, leaving Sarah with a $50 copay.

**Key Insight**: Each agent operates independently (Sarah's appointment can be scheduled even if billing systems are temporarily down), yet they collaborate seamlessly when needed (scheduling can query patient records to verify Sarah exists before booking).

Throughout this guide, we'll reference Sarah's journey to show how each component works.

---

## Technology Stack

### Core Technologies

```xml
<!-- Spring Boot 3.2.4 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>3.2.4</version>
</dependency>

<!-- A2A Java Library for Agent Orchestration -->
<dependency>
    <groupId>io.github.vishalmysore</groupId>
    <artifactId>a2a-java</artifactId>
    <version>1.0.7</version>
</dependency>

<!-- Tools4AI for Agent Annotations -->
<dependency>
    <groupId>com.t4a.api</groupId>
    <artifactId>tools4ai</artifactId>
    <version>1.1.9.7</version>
</dependency>

<dependency>
    <groupId>com.t4a.processor</groupId>
    <artifactId>tools4ai-annotations</artifactId>
    <version>0.0.2</version>
</dependency>
```

### Key Components

| Component | Version | Purpose |
|-----------|---------|---------|
| Java | 17 | Programming language |
| Spring Boot | 3.2.4 | Web framework and agent servers |
| A2A Java | 1.0.7 | AgentCatalog and AgenticMesh orchestration |
| Tools4AI | 1.1.9.7 | @Agent and @Action annotations |
| Maven | 3.6+ | Build tool |

---

## Four Core Healthcare Agents

### 1. Patient Records Agent (Port 8871)

**Domain**: Electronic Health Records (EHR), patient demographics, medical history

**Maps to**: Your existing Patient/EHR microservice, Epic integration layer, or FHIR server facade

**Server Implementation**:

```java
package org.example.patientrecords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PatientRecordsServer {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "patientrecords");
        SpringApplication.run(PatientRecordsServer.class, args);
    }
}
```

**Service Implementation with Tools4AI**:

```java
package org.example.patientrecords;

import com.t4a.annotations.Agent;
import com.t4a.annotations.Action;
import org.springframework.stereotype.Service;
import java.util.*;

@Agent(
    groupName = "patientRecordsOperations",
    description = "Manages patient records, medical history, and health information"
)
@Service
public class PatientRecordsService {

    // DEMO ONLY: In a real system, replace with JPA repositories backed by PostgreSQL/MySQL
    // Consider: FHIR R4 Patient resource format for interoperability
    private Map<String, Map<String, Object>> patientDatabase = new HashMap<>();
    private Map<String, List<String>> medicalHistory = new HashMap<>();
    private Map<String, List<Map<String, Object>>> vitalSigns = new HashMap<>();

    @Action(description = "Create a new patient record with demographics")
    public String createPatientRecord(
        String patientName, 
        int age, 
        String bloodType, 
        String address
    ) {
        String patientId = "PT-" + System.currentTimeMillis();
        Map<String, Object> patient = new HashMap<>();
        patient.put("id", patientId);
        patient.put("name", patientName);
        patient.put("age", age);
        patient.put("bloodType", bloodType);
        patient.put("address", address);
        patient.put("createdDate", new Date().toString());
        
        patientDatabase.put(patientId, patient);
        medicalHistory.put(patientId, new ArrayList<>());
        vitalSigns.put(patientId, new ArrayList<>());
        
        return "Patient record created: " + patientId + 
               " | Name: " + patientName + 
               " | Blood Type: " + bloodType;
    }

    @Action(description = "Retrieve complete medical history for a patient")
    public String getMedicalHistory(String patientId) {
        if (!patientDatabase.containsKey(patientId)) {
            return "Error: Patient " + patientId + " not found";
        }
        
        List<String> history = medicalHistory.getOrDefault(patientId, new ArrayList<>());
        Map<String, Object> patient = patientDatabase.get(patientId);
        
        return "Medical History for " + patient.get("name") + 
               " (" + patientId + "):\n" + String.join("\n", history);
    }

    @Action(description = "Add vital signs reading for a patient")
    public String addVitalSigns(
        String patientId,
        int heartRate,
        String bloodPressure,
        double temperature,
        int respiratoryRate
    ) {
        if (!patientDatabase.containsKey(patientId)) {
            return "Error: Patient not found";
        }
        
        Map<String, Object> vitals = new HashMap<>();
        vitals.put("timestamp", new Date().toString());
        vitals.put("heartRate", heartRate);
        vitals.put("bloodPressure", bloodPressure);
        vitals.put("temperature", temperature);
        vitals.put("respiratoryRate", respiratoryRate);
        
        vitalSigns.get(patientId).add(vitals);
        
        return "Vital signs recorded for " + patientId + 
               " | HR: " + heartRate + 
               " | BP: " + bloodPressure + 
               " | Temp: " + temperature + "°F";
    }

    @Action(description = "Update patient demographics and information")
    public String updatePatientInfo(
        String patientId,
        String address,
        String phoneNumber,
        String emergencyContact
    ) {
        if (!patientDatabase.containsKey(patientId)) {
            return "Error: Patient not found";
        }
        
        Map<String, Object> patient = patientDatabase.get(patientId);
        patient.put("address", address);
        patient.put("phoneNumber", phoneNumber);
        patient.put("emergencyContact", emergencyContact);
        patient.put("lastUpdated", new Date().toString());
        
        return "Patient information updated for " + patientId;
    }
}
```

**Configuration** (`application-patientrecords.properties`):

```properties
server.port=8871
spring.application.name=patient-records-agent
management.endpoints.web.exposure.include=health,info
```

---

### 2. Appointments Agent (Port 8872)

**Domain**: Appointment scheduling, calendar management, reminders

**Maps to**: Your current Scheduling microservice, calendar integration (Google Calendar, Outlook), or practice management system

**Service Implementation**:

```java
package org.example.appointments;

import com.t4a.annotations.Agent;
import com.t4a.annotations.Action;
import org.springframework.stereotype.Service;
import java.util.*;

@Agent(
    groupName = "appointmentsOperations",
    description = "Manages medical appointments, scheduling, and visit coordination"
)
@Service
public class AppointmentsService {

    private Map<String, Map<String, Object>> appointments = new HashMap<>();
    private Map<String, List<String>> doctorSchedule = new HashMap<>();

    @Action(description = "Schedule a new medical appointment")
    public String scheduleAppointment(
        String patientId,
        String doctorName,
        String appointmentType,
        String preferredDate
    ) {
        String appointmentId = "APT-" + System.currentTimeMillis();
        
        Map<String, Object> appointment = new HashMap<>();
        appointment.put("id", appointmentId);
        appointment.put("patientId", patientId);
        appointment.put("doctorName", doctorName);
        appointment.put("type", appointmentType);
        appointment.put("date", preferredDate);
        appointment.put("status", "SCHEDULED");
        appointment.put("createdAt", new Date().toString());
        
        appointments.put(appointmentId, appointment);
        
        // Add to doctor's schedule
        doctorSchedule.computeIfAbsent(doctorName, k -> new ArrayList<>())
                      .add(appointmentId);
        
        return "Appointment scheduled: " + appointmentId + 
               " | Doctor: " + doctorName + 
               " | Type: " + appointmentType + 
               " | Date: " + preferredDate;
    }

    @Action(description = "Check available time slots for a doctor")
    public String checkAvailability(String doctorName, String date) {
        List<String> doctorAppointments = doctorSchedule.getOrDefault(
            doctorName, 
            new ArrayList<>()
        );
        
        long appointmentsOnDate = doctorAppointments.stream()
            .map(appointments::get)
            .filter(apt -> apt.get("date").equals(date))
            .count();
        
        int availableSlots = 10 - (int) appointmentsOnDate;
        
        return "Doctor " + doctorName + " availability on " + date + 
               ": " + availableSlots + " slots available";
    }

    @Action(description = "Reschedule an existing appointment")
    public String rescheduleAppointment(
        String appointmentId,
        String newDate,
        String newTime
    ) {
        if (!appointments.containsKey(appointmentId)) {
            return "Error: Appointment not found";
        }
        
        Map<String, Object> appointment = appointments.get(appointmentId);
        String oldDate = (String) appointment.get("date");
        
        appointment.put("date", newDate);
        appointment.put("time", newTime);
        appointment.put("status", "RESCHEDULED");
        appointment.put("updatedAt", new Date().toString());
        
        return "Appointment " + appointmentId + 
               " rescheduled from " + oldDate + 
               " to " + newDate + " at " + newTime;
    }

    @Action(description = "Send appointment reminder to patient")
    public String sendReminder(String appointmentId) {
        if (!appointments.containsKey(appointmentId)) {
            return "Error: Appointment not found";
        }
        
        Map<String, Object> appointment = appointments.get(appointmentId);
        
        return "Reminder sent for appointment " + appointmentId + 
               " | Patient: " + appointment.get("patientId") + 
               " | Doctor: " + appointment.get("doctorName") + 
               " | Date: " + appointment.get("date");
    }
}
```

---

### 3. Diagnostics Agent (Port 8873)

**Domain**: Laboratory tests, medical imaging, diagnostic reports

**Maps to**: Your Lab Information System (LIS), PACS integration, or diagnostic order management service

**Service Implementation**:

```java
package org.example.diagnostics;

import com.t4a.annotations.Agent;
import com.t4a.annotations.Action;
import org.springframework.stereotype.Service;
import java.util.*;

@Agent(
    groupName = "diagnosticsOperations",
    description = "Manages lab tests, medical imaging, and diagnostic procedures"
)
@Service
public class DiagnosticsService {

    // DEMO ONLY: In a real system, replace with:
    // - Lab orders → HL7 v2.x/FHIR ServiceRequest to external lab systems
    // - Imaging → DICOM integration with PACS/RIS via message queues (RabbitMQ/Kafka)
    // - Results → Event-driven async processing when labs send HL7 ORU messages
    private Map<String, Map<String, Object>> labOrders = new HashMap<>();
    private Map<String, Map<String, Object>> imagingOrders = new HashMap<>();
    private Map<String, List<Map<String, Object>>> testResults = new HashMap<>();

    @Action(description = "Order laboratory tests for a patient")
    public String orderLabTests(
        String patientId,
        String testType,
        String urgency
    ) {
        String orderId = "LAB-" + System.currentTimeMillis();
        
        Map<String, Object> order = new HashMap<>();
        order.put("orderId", orderId);
        order.put("patientId", patientId);
        order.put("testType", testType);
        order.put("urgency", urgency);
        order.put("status", "ORDERED");
        order.put("orderedDate", new Date().toString());
        
        labOrders.put(orderId, order);
        
        return "Lab test ordered: " + orderId + 
               " | Patient: " + patientId + 
               " | Test: " + testType + 
               " | Urgency: " + urgency;
    }

    @Action(description = "Get lab test results for a patient")
    public String getLabResults(String patientId, String orderId) {
        if (!labOrders.containsKey(orderId)) {
            return "Error: Lab order not found";
        }
        
        Map<String, Object> order = labOrders.get(orderId);
        
        // Simulate results
        Map<String, Object> results = new HashMap<>();
        results.put("orderId", orderId);
        results.put("status", "COMPLETED");
        results.put("resultDate", new Date().toString());
        results.put("findings", "Results within normal range");
        
        testResults.computeIfAbsent(patientId, k -> new ArrayList<>()).add(results);
        
        return "Lab Results for " + orderId + 
               " | Patient: " + patientId + 
               " | Test: " + order.get("testType") + 
               " | Status: COMPLETED | Findings: Normal range";
    }

    @Action(description = "Order medical imaging scan")
    public String orderImagingScan(
        String patientId,
        String scanType,
        String bodyPart,
        String reason
    ) {
        String orderId = "IMG-" + System.currentTimeMillis();
        
        Map<String, Object> order = new HashMap<>();
        order.put("orderId", orderId);
        order.put("patientId", patientId);
        order.put("scanType", scanType);
        order.put("bodyPart", bodyPart);
        order.put("reason", reason);
        order.put("status", "SCHEDULED");
        order.put("orderedDate", new Date().toString());
        
        imagingOrders.put(orderId, order);
        
        return "Imaging scan ordered: " + orderId + 
               " | Patient: " + patientId + 
               " | Type: " + scanType + 
               " | Body Part: " + bodyPart;
    }

    @Action(description = "Create comprehensive diagnostic report")
    public String createDiagnosticReport(
        String patientId,
        String diagnosis,
        String recommendations
    ) {
        String reportId = "RPT-" + System.currentTimeMillis();
        
        List<Map<String, Object>> patientResults = testResults.getOrDefault(
            patientId,
            new ArrayList<>()
        );
        
        StringBuilder report = new StringBuilder();
        report.append("Diagnostic Report ").append(reportId).append("\n");
        report.append("Patient: ").append(patientId).append("\n");
        report.append("Date: ").append(new Date()).append("\n");
        report.append("Diagnosis: ").append(diagnosis).append("\n");
        report.append("Test Results Count: ").append(patientResults.size()).append("\n");
        report.append("Recommendations: ").append(recommendations);
        
        return report.toString();
    }
}
```

---

### 4. Billing Agent (Port 8874)

**Domain**: Medical billing, insurance claims, payment processing

**Maps to**: Your Revenue Cycle Management (RCM) system, claims clearinghouse integration, or payment gateway service

**Service Implementation**:

```java
package org.example.billing;

import com.t4a.annotations.Agent;
import com.t4a.annotations.Action;
import org.springframework.stereotype.Service;
import java.util.*;

@Agent(
    groupName = "billingOperations",
    description = "Manages medical billing, insurance claims, and payment processing"
)
@Service
public class BillingService {

    // DEMO ONLY: In a real system, integrate with:
    // - Claims → EDI 837 files to clearinghouses (Availity, Change Healthcare)
    // - Payments → Payment processors (Stripe, Square) with PCI DSS compliance
    // - Invoices → ERP systems with proper accounting/audit trails
    private Map<String, Map<String, Object>> invoices = new HashMap<>();
    private Map<String, Map<String, Object>> insuranceClaims = new HashMap<>();
    private Map<String, List<Map<String, Object>>> paymentHistory = new HashMap<>();

    @Action(description = "Generate invoice for medical services")
    public String generateInvoice(
        String patientId,
        String serviceType,
        double amount
    ) {
        String invoiceId = "INV-" + System.currentTimeMillis();
        
        Map<String, Object> invoice = new HashMap<>();
        invoice.put("invoiceId", invoiceId);
        invoice.put("patientId", patientId);
        invoice.put("serviceType", serviceType);
        invoice.put("amount", amount);
        invoice.put("status", "PENDING");
        invoice.put("generatedDate", new Date().toString());
        invoice.put("dueDate", new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000).toString());
        
        invoices.put(invoiceId, invoice);
        
        return "Invoice generated: " + invoiceId + 
               " | Patient: " + patientId + 
               " | Service: " + serviceType + 
               " | Amount: $" + amount;
    }

    @Action(description = "Process patient payment")
    public String processPayment(
        String invoiceId,
        double paymentAmount,
        String paymentMethod
    ) {
        if (!invoices.containsKey(invoiceId)) {
            return "Error: Invoice not found";
        }
        
        Map<String, Object> invoice = invoices.get(invoiceId);
        String patientId = (String) invoice.get("patientId");
        double totalAmount = (double) invoice.get("amount");
        
        Map<String, Object> payment = new HashMap<>();
        payment.put("paymentId", "PAY-" + System.currentTimeMillis());
        payment.put("invoiceId", invoiceId);
        payment.put("amount", paymentAmount);
        payment.put("method", paymentMethod);
        payment.put("date", new Date().toString());
        
        paymentHistory.computeIfAbsent(patientId, k -> new ArrayList<>()).add(payment);
        
        if (paymentAmount >= totalAmount) {
            invoice.put("status", "PAID");
        } else {
            invoice.put("status", "PARTIALLY_PAID");
        }
        
        return "Payment processed: " + payment.get("paymentId") + 
               " | Invoice: " + invoiceId + 
               " | Amount: $" + paymentAmount + 
               " | Method: " + paymentMethod;
    }

    @Action(description = "Submit insurance claim")
    public String submitInsuranceClaim(
        String patientId,
        String insuranceProvider,
        String claimType,
        double claimAmount
    ) {
        String claimId = "CLM-" + System.currentTimeMillis();
        
        Map<String, Object> claim = new HashMap<>();
        claim.put("claimId", claimId);
        claim.put("patientId", patientId);
        claim.put("provider", insuranceProvider);
        claim.put("type", claimType);
        claim.put("amount", claimAmount);
        claim.put("status", "SUBMITTED");
        claim.put("submittedDate", new Date().toString());
        
        insuranceClaims.put(claimId, claim);
        
        return "Insurance claim submitted: " + claimId + 
               " | Patient: " + patientId + 
               " | Provider: " + insuranceProvider + 
               " | Amount: $" + claimAmount;
    }

    @Action(description = "Check insurance claim status")
    public String getClaimStatus(String claimId) {
        if (!insuranceClaims.containsKey(claimId)) {
            return "Error: Claim not found";
        }
        
        Map<String, Object> claim = insuranceClaims.get(claimId);
        
        return "Claim Status: " + claimId + 
               " | Patient: " + claim.get("patientId") + 
               " | Provider: " + claim.get("provider") + 
               " | Status: " + claim.get("status") + 
               " | Amount: $" + claim.get("amount");
    }

    @Action(description = "Get patient account balance")
    public String getAccountBalance(String patientId) {
        double totalBilled = 0.0;
        double totalPaid = 0.0;
        
        // Calculate total billed
        for (Map<String, Object> invoice : invoices.values()) {
            if (invoice.get("patientId").equals(patientId)) {
                totalBilled += (double) invoice.get("amount");
            }
        }
        
        // Calculate total paid
        List<Map<String, Object>> payments = paymentHistory.getOrDefault(
            patientId,
            new ArrayList<>()
        );
        for (Map<String, Object> payment : payments) {
            totalPaid += (double) payment.get("amount");
        }
        
        double balance = totalBilled - totalPaid;
        
        return "Account Balance for " + patientId + 
               " | Total Billed: $" + totalBilled + 
               " | Total Paid: $" + totalPaid + 
               " | Balance Due: $" + balance;
    }
}
```

---

## Setting Up Your Healthcare Agentic Mesh

### Step 1: Project Structure

```
healthcareagenticmesh/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/org/example/
│   │   │   ├── patientrecords/
│   │   │   │   ├── PatientRecordsServer.java
│   │   │   │   └── PatientRecordsService.java
│   │   │   ├── appointments/
│   │   │   │   ├── AppointmentsServer.java
│   │   │   │   └── AppointmentsService.java
│   │   │   ├── diagnostics/
│   │   │   │   ├── DiagnosticsServer.java
│   │   │   │   └── DiagnosticsService.java
│   │   │   ├── billing/
│   │   │   │   ├── BillingServer.java
│   │   │   │   └── BillingService.java
│   │   │   └── healthcareclient/
│   │   │       └── HealthcareMeshClient.java
│   │   └── resources/
│   │       ├── application-patientrecords.properties
│   │       ├── application-appointments.properties
│   │       ├── application-diagnostics.properties
│   │       ├── application-billing.properties
│   │       ├── tools4ai_patientrecords.properties
│   │       ├── tools4ai_appointments.properties
│   │       ├── tools4ai_diagnostics.properties
│   │       └── tools4ai_billing.properties
```

### Step 2: Maven Configuration

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>io.github.vishalmysore</groupId>
    <artifactId>healthcare-agentic-mesh</artifactId>
    <version>1.0.0</version>
    
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <spring-boot.version>3.2.4</spring-boot.version>
    </properties>
    
    <dependencies>
        <!-- Spring Boot Starter Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>${spring-boot.version}</version>
        </dependency>
        
        <!-- A2A Java Library -->
        <dependency>
            <groupId>io.github.vishalmysore</groupId>
            <artifactId>a2a-java</artifactId>
            <version>1.0.7</version>
        </dependency>
        
        <!-- Tools4AI -->
        <dependency>
            <groupId>com.t4a.api</groupId>
            <artifactId>tools4ai</artifactId>
            <version>1.1.9.7</version>
        </dependency>
        
        <dependency>
            <groupId>com.t4a.processor</groupId>
            <artifactId>tools4ai-annotations</artifactId>
            <version>0.0.2</version>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring-boot.version}</version>
                <configuration>
                    <skip>true</skip>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### Step 3: Build the Project

```bash
mvn clean compile
```

### Step 4: Start All Agents

**Terminal 1 - Patient Records**:
```bash
java -cp target/classes org.example.patientrecords.PatientRecordsServer
```

**Terminal 2 - Appointments**:
```bash
java -cp target/classes org.example.appointments.AppointmentsServer
```

**Terminal 3 - Diagnostics**:
```bash
java -cp target/classes org.example.diagnostics.DiagnosticsServer
```

**Terminal 4 - Billing**:
```bash
java -cp target/classes org.example.billing.BillingServer
```

---

## Using AgentCatalog for Healthcare Workflows

The **AgentCatalog** is the foundation of agent orchestration in the A2A Java library. It discovers available agents, routes queries intelligently, and coordinates multi-agent workflows.

### Basic AgentCatalog Usage

```java
package org.example.healthcareclient;

import com.t4a.api.AgentCatalog;
import com.t4a.predict.Result;

public class HealthcareMeshClient {
    public static void main(String[] args) throws Exception {
        // Step 1: Create AgentCatalog
        AgentCatalog agentCatalog = new AgentCatalog();
        
        // Step 2: Register all healthcare agents
        agentCatalog.addAgent("http://localhost:8871/"); // Patient Records
        agentCatalog.addAgent("http://localhost:8872/"); // Appointments
        agentCatalog.addAgent("http://localhost:8873/"); // Diagnostics
        agentCatalog.addAgent("http://localhost:8874/"); // Billing
        
        System.out.println("=== Healthcare Agentic Mesh Initialized ===");
        System.out.println("Connected Agents: 4");
        System.out.println("- Patient Records Agent (Port 8871)");
        System.out.println("- Appointments Agent (Port 8872)");
        System.out.println("- Diagnostics Agent (Port 8873)");
        System.out.println("- Billing Agent (Port 8874)\n");
        
        // Step 3: Execute healthcare workflows
        patientIntakeWorkflow(agentCatalog);
        appointmentSchedulingWorkflow(agentCatalog);
        diagnosticWorkflow(agentCatalog);
        billingWorkflow(agentCatalog);
    }
    
    private static void patientIntakeWorkflow(AgentCatalog catalog) throws Exception {
        System.out.println("\n=== Patient Intake Workflow ===");
        
        // Create new patient record
        Result result1 = catalog.processQuery(
            "Create a new patient record for Sarah Johnson, age 45, " +
            "blood type O+, address 123 Main Street"
        );
        System.out.println(result1.getTextResult());
        
        // Add vital signs
        Result result2 = catalog.processQuery(
            "Add vital signs for patient PT-123: heart rate 72, " +
            "blood pressure 120/80, temperature 98.6, respiratory rate 16"
        );
        System.out.println(result2.getTextResult());
    }
    
    private static void appointmentSchedulingWorkflow(AgentCatalog catalog) throws Exception {
        System.out.println("\n=== Appointment Scheduling Workflow ===");
        
        // Check doctor availability
        Result result1 = catalog.processQuery(
            "Check availability for Dr. Smith on 2026-02-15"
        );
        System.out.println(result1.getTextResult());
        
        // Schedule appointment
        Result result2 = catalog.processQuery(
            "Schedule appointment for patient PT-123 with Dr. Smith, " +
            "type Cardiology, preferred date 2026-02-15"
        );
        System.out.println(result2.getTextResult());
        
        // Send reminder
        Result result3 = catalog.processQuery(
            "Send appointment reminder for APT-456"
        );
        System.out.println(result3.getTextResult());
    }
    
    private static void diagnosticWorkflow(AgentCatalog catalog) throws Exception {
        System.out.println("\n=== Diagnostic Workflow ===");
        
        // Order lab tests
        Result result1 = catalog.processQuery(
            "Order lab tests for patient PT-123, test type Complete Blood Count, urgency ROUTINE"
        );
        System.out.println(result1.getTextResult());
        
        // Order imaging
        Result result2 = catalog.processQuery(
            "Order imaging scan for patient PT-123, scan type MRI, " +
            "body part Brain, reason headache evaluation"
        );
        System.out.println(result2.getTextResult());
        
        // Get lab results
        Result result3 = catalog.processQuery(
            "Get lab results for patient PT-123, order LAB-789"
        );
        System.out.println(result3.getTextResult());
    }
    
    private static void billingWorkflow(AgentCatalog catalog) throws Exception {
        System.out.println("\n=== Billing Workflow ===");
        
        // Generate invoice
        Result result1 = catalog.processQuery(
            "Generate invoice for patient PT-123, service type Consultation, amount 250.00"
        );
        System.out.println(result1.getTextResult());
        
        // Submit insurance claim
        Result result2 = catalog.processQuery(
            "Submit insurance claim for patient PT-123, " +
            "provider Blue Cross, claim type Medical, amount 200.00"
        );
        System.out.println(result2.getTextResult());
        
        // Check account balance
        Result result3 = catalog.processQuery(
            "Get account balance for patient PT-123"
        );
        System.out.println(result3.getTextResult());
    }
}
```

### How AgentCatalog Works

1. **Agent Discovery**: When you call `addAgent()`, [AgentCatalog](#core-concepts-defined) connects to the agent and retrieves its capabilities using the `tools/list` JSON-RPC method

2. **Intent Analysis**: When you call `processQuery()`, AgentCatalog analyzes the natural language query to understand the user's intent

3. **Agent Selection**: Based on the intent, AgentCatalog selects the appropriate agent(s) with the required capabilities

4. **Action Invocation**: AgentCatalog calls the selected agent's action using the `tools/call` JSON-RPC method

5. **Result Aggregation**: If multiple agents are involved, AgentCatalog aggregates the results and returns a unified response

### Request Flow Diagram

Here's how a request flows through the system:

```
┌─────────────────────────────────────────────────────────────────┐
│  Human User / Upstream LLM Agent                                │
│  Query: "Schedule checkup for Sarah, order blood work"          │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│  Mesh Client (Your Application)                                 │
│  ┌──────────────────┐  ┌─────────────────┐                     │
│  │  AgentCatalog    │  │  AgenticMesh     │                     │
│  │  - Discovery     │  │  - Orchestration │                     │
│  │  - Routing       │  │  - Pipelines     │                     │
│  │  - Aggregation   │  │  - Context       │                     │
│  └──────────────────┘  └─────────────────┘                     │
└────────────────────────────┬────────────────────────────────────┘
                             │
                 Intent Analysis & Routing
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
        ▼                    ▼                    ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│ Patient      │    │ Appointments │    │ Diagnostics  │
│ Records      │    │   Agent      │    │    Agent     │
│ Agent        │    │              │    │              │
│ Port 8871    │    │  Port 8872   │    │  Port 8873   │
│              │    │              │    │              │
│ Actions:     │    │ Actions:     │    │ Actions:     │
│ • createPat..│    │ • schedule...│    │ • orderLab...│
│ • getMedHist │    │ • checkAvail │    │ • getResults │
└──────────────┘    └──────────────┘    └──────────────┘

ROUTING DECISIONS:
1. "Schedule checkup for Sarah" → Appointments Agent
   - First checks Patient Records to validate Sarah exists
2. "order blood work" → Diagnostics Agent
   - Receives Sarah's patient ID from previous step
3. Results aggregated and returned to client
```

**Routing Intelligence**: [AgentCatalog](#core-concepts-defined) uses the action descriptions from each agent's `@Action` annotations to match queries. For example:
- "Schedule" keyword → routes to Appointments Agent's `scheduleAppointment` action
- "blood work" or "lab" → routes to Diagnostics Agent's `orderLabTests` action
- "invoice" or "payment" → routes to Billing Agent's `generateInvoice` or `processPayment` actions

---

## Using AgenticMesh for Complex Orchestration

While [AgentCatalog](#core-concepts-defined) is great for single-agent queries, [AgenticMesh](#core-concepts-defined) excels at complex multi-agent workflows that require coordination, pipelining, and dynamic agent collaboration.

### AgenticMesh Implementation

```java
package org.example.healthcareclient;

import com.t4a.api.AgentCatalog;
import com.t4a.api.AgenticMesh;
import com.t4a.predict.Result;

public class AdvancedHealthcareMeshClient {
    public static void main(String[] args) throws Exception {
        // Initialize AgentCatalog
        AgentCatalog catalog = new AgentCatalog();
        catalog.addAgent("http://localhost:8871/");
        catalog.addAgent("http://localhost:8872/");
        catalog.addAgent("http://localhost:8873/");
        catalog.addAgent("http://localhost:8874/");
        
        // Create AgenticMesh for complex workflows
        AgenticMesh mesh = new AgenticMesh(catalog);
        
        // Execute complex healthcare workflows
        completePatientVisitWorkflow(mesh);
        annualPhysicalWorkflow(mesh);
        emergencyAdmissionWorkflow(mesh);
    }
    
    private static void completePatientVisitWorkflow(AgenticMesh mesh) throws Exception {
        System.out.println("\n=== Complete Patient Visit Workflow (AgenticMesh) ===");
        
        String workflow = """
            1. Create patient record for Michael Chen, age 52, blood type A+, address 456 Oak Ave
            2. Schedule appointment with Dr. Williams for General Checkup on 2026-02-20
            3. Order routine lab tests: Complete Blood Count and Lipid Panel
            4. Add vital signs: heart rate 78, blood pressure 135/85, temperature 98.4, respiratory rate 18
            5. Generate invoice for consultation $200 and lab tests $150
            6. Submit insurance claim to Aetna for $300
            """;
        
        Result result = mesh.pipeLineMesh(workflow);
        System.out.println("Workflow Result:");
        System.out.println(result.getTextResult());
    }
    
    private static void annualPhysicalWorkflow(AgenticMesh mesh) throws Exception {
        System.out.println("\n=== Annual Physical Examination Workflow ===");
        
        String workflow = """
            1. Check availability for Dr. Martinez on 2026-03-01
            2. Schedule annual physical appointment for patient PT-789
            3. Order comprehensive lab panel: CBC, CMP, Lipid Panel, HbA1c
            4. Order EKG and chest X-ray
            5. Send appointment reminder one week in advance
            6. Generate invoice for annual physical package $500
            """;
        
        Result result = mesh.pipeLineMesh(workflow);
        System.out.println("Annual Physical Scheduled:");
        System.out.println(result.getTextResult());
    }
    
    private static void emergencyAdmissionWorkflow(AgenticMesh mesh) throws Exception {
        System.out.println("\n=== Emergency Admission Workflow ===");
        
        String workflow = """
            1. Create emergency patient record for Jane Doe, age 35, blood type B-, address Unknown
            2. Add critical vital signs: heart rate 120, blood pressure 90/60, temperature 101.2, respiratory rate 24
            3. Order STAT lab tests: CBC, BMP, Troponin, urgent
            4. Order emergency CT scan of abdomen, reason acute abdominal pain
            5. Get medical history for patient
            6. Generate emergency visit invoice $2500
            7. Verify insurance coverage and submit claim
            """;
        
        Result result = mesh.pipeLineMesh(workflow);
        System.out.println("Emergency Admission Processed:");
        System.out.println(result.getTextResult());
    }
}
```

### AgenticMesh Pipeline Features

- **Sequential Execution**: Steps execute in order with output from previous steps available to subsequent steps
- **Error Handling**: If a step fails, the mesh can retry or route to alternative agents
- **Context Preservation**: Patient IDs and other identifiers are automatically carried through the workflow
- **Dynamic Routing**: Based on intermediate results, the mesh can choose different execution paths

---

## Transform Your Existing Healthcare Code

One of the most powerful aspects of this architecture is how easily you can convert existing healthcare Java services into intelligent agents.

### Before: Traditional Service

```java
package com.hospital.services;

import org.springframework.stereotype.Service;

@Service
public class TraditionalPatientService {
    
    private Map<String, Patient> patients = new HashMap<>();
    
    public Patient createPatient(String name, int age, String bloodType) {
        Patient patient = new Patient();
        patient.setId("PT-" + System.currentTimeMillis());
        patient.setName(name);
        patient.setAge(age);
        patient.setBloodType(bloodType);
        
        patients.put(patient.getId(), patient);
        return patient;
    }
    
    public Patient getPatient(String patientId) {
        return patients.get(patientId);
    }
    
    public void updatePatient(String patientId, String address, String phone) {
        Patient patient = patients.get(patientId);
        if (patient != null) {
            patient.setAddress(address);
            patient.setPhone(phone);
        }
    }
    
    public List<Patient> searchPatients(String searchTerm) {
        return patients.values().stream()
            .filter(p -> p.getName().contains(searchTerm))
            .collect(Collectors.toList());
    }
}
```

### After: Agentic Service (Just Add Annotations!)

```java
package com.hospital.services;

import com.t4a.annotations.Agent;
import com.t4a.annotations.Action;
import org.springframework.stereotype.Service;

@Agent(
    groupName = "patientOperations",
    description = "Manages patient records and demographics"
)
@Service
public class AgenticPatientService {
    
    private Map<String, Patient> patients = new HashMap<>();
    
    @Action(description = "Create a new patient record")
    public String createPatient(String name, int age, String bloodType) {
        Patient patient = new Patient();
        patient.setId("PT-" + System.currentTimeMillis());
        patient.setName(name);
        patient.setAge(age);
        patient.setBloodType(bloodType);
        
        patients.put(patient.getId(), patient);
        
        return "Patient created: " + patient.getId() + 
               " | Name: " + name + 
               " | Blood Type: " + bloodType;
    }
    
    @Action(description = "Retrieve patient information by ID")
    public String getPatient(String patientId) {
        Patient patient = patients.get(patientId);
        if (patient == null) {
            return "Error: Patient not found";
        }
        return "Patient: " + patient.getName() + 
               " | Age: " + patient.getAge() + 
               " | Blood Type: " + patient.getBloodType();
    }
    
    @Action(description = "Update patient contact information")
    public String updatePatient(String patientId, String address, String phone) {
        Patient patient = patients.get(patientId);
        if (patient != null) {
            patient.setAddress(address);
            patient.setPhone(phone);
            return "Patient updated: " + patientId;
        }
        return "Error: Patient not found";
    }
    
    @Action(description = "Search for patients by name")
    public String searchPatients(String searchTerm) {
        List<Patient> found = patients.values().stream()
            .filter(p -> p.getName().contains(searchTerm))
            .collect(Collectors.toList());
        
        return "Found " + found.size() + " patients matching '" + searchTerm + "'";
    }
}
```

### Key Changes:

1. **Add `@Agent` annotation** to the class with a descriptive group name
2. **Add `@Action` annotation** to each method you want to expose as an agent action
3. **Change return types to String** for simplicity (or use complex types with JSON serialization)
4. **Add descriptive messages** to return values to improve agent-to-agent communication

That's it! Your existing business logic remains unchanged. The annotations make your service discoverable and callable by other agents in the mesh.

---

## Real-World Healthcare Workflows

### Workflow 1: New Patient Onboarding

```java
public static void newPatientOnboarding(AgenticMesh mesh) throws Exception {
    String workflow = """
        1. Create patient record: Name=Robert Taylor, Age=38, BloodType=AB+, Address=789 Elm St
        2. Schedule initial consultation with Dr. Peterson on 2026-02-25
        3. Order baseline lab tests: CBC, CMP, TSH, Vitamin D
        4. Add initial vital signs: HR=68, BP=118/76, Temp=98.6, RR=14
        5. Verify insurance coverage with United Healthcare
        6. Generate welcome packet invoice $50
        7. Send appointment confirmation and reminder
        """;
    
    Result result = mesh.pipeLineMesh(workflow);
    System.out.println(result.getTextResult());
}
```

**Output**:
```
Patient created: PT-1738789234
Appointment scheduled: APT-1738789235 with Dr. Peterson
Lab tests ordered: LAB-1738789236
Vital signs recorded successfully
Insurance verified: Active coverage
Invoice generated: INV-1738789237 - $50
Reminder sent to patient
```

### Workflow 2: Chronic Disease Management

```java
public static void diabetesManagement(AgenticMesh mesh) throws Exception {
    String workflow = """
        1. Retrieve medical history for patient PT-12345
        2. Add vital signs with glucose reading: HR=74, BP=128/82, Temp=98.4, Glucose=145
        3. Order HbA1c and fasting glucose tests, urgency ROUTINE
        4. Schedule follow-up with Dr. Endocrinologist in 3 months
        5. Create diagnostic report: Diagnosis=Type 2 Diabetes Management, Recommendations=Continue metformin, diet modification
        6. Generate invoice for endocrinology visit $300
        """;
    
    Result result = mesh.pipeLineMesh(workflow);
    System.out.println(result.getTextResult());
}
```

### Workflow 3: Surgical Pre-Op Preparation

```java
public static void preOpWorkflow(AgenticMesh mesh) throws Exception {
    String workflow = """
        1. Create pre-op assessment for patient PT-67890
        2. Order pre-op lab panel: CBC, CMP, PT/INR, Type and Screen
        3. Order EKG and chest X-ray for clearance
        4. Schedule pre-op consultation with anesthesiologist
        5. Verify surgical procedure insurance pre-authorization
        6. Generate pre-op invoice $1500
        7. Send pre-op instructions and fasting guidelines
        """;
    
    Result result = mesh.pipeLineMesh(workflow);
    System.out.println(result.getTextResult());
}
```

### Workflow 4: Post-Discharge Follow-Up

```java
public static void postDischargeFollowUp(AgenticMesh mesh) throws Exception {
    String workflow = """
        1. Retrieve discharge summary for patient PT-54321
        2. Schedule 7-day post-discharge follow-up with primary care
        3. Order follow-up lab tests based on discharge recommendations
        4. Check prescription refill needs
        5. Generate post-discharge billing statement
        6. Submit final insurance claim for hospital stay
        7. Send patient satisfaction survey
        """;
    
    Result result = mesh.pipeLineMesh(workflow);
    System.out.println(result.getTextResult());
}
```

---

## Advanced Agent Communication Patterns

### Pattern 1: Agent-to-Agent Direct Communication

```java
// Appointments Agent calls Patient Records Agent directly
@Action(description = "Schedule appointment with patient validation")
public String scheduleWithValidation(String patientId, String doctorName, String date) {
    // Call Patient Records Agent to validate patient exists
    AgentCatalog catalog = new AgentCatalog();
    catalog.addAgent("http://localhost:8871/");
    
    Result validation = catalog.processQuery("Get medical history for " + patientId);
    
    if (validation.getTextResult().contains("Error")) {
        return "Cannot schedule: Patient not found";
    }
    
    // Proceed with scheduling
    return scheduleAppointment(patientId, doctorName, "General", date);
}
```

### Pattern 2: Event-Driven Agent Notifications

```java
// Diagnostics Agent notifies Billing Agent when tests complete
@Action(description = "Complete lab test and trigger billing")
public String completeLabTestWithBilling(String patientId, String orderId) {
    // Complete the test
    String testResult = getLabResults(patientId, orderId);
    
    // Notify Billing Agent
    AgentCatalog catalog = new AgentCatalog();
    catalog.addAgent("http://localhost:8874/");
    
    catalog.processQuery(
        "Generate invoice for patient " + patientId + 
        ", service type Lab Tests, amount 250.00"
    );
    
    return testResult + " | Billing automatically generated";
}
```

### Pattern 3: Conditional Agent Routing

```java
// Patient Records Agent routes to different agents based on urgency
@Action(description = "Add vital signs with automatic routing")
public String addVitalSignsWithRouting(
    String patientId,
    int heartRate,
    String bloodPressure,
    double temperature
) {
    String result = addVitalSigns(patientId, heartRate, bloodPressure, temperature, 16);
    
    // Check for abnormal vital signs
    if (heartRate > 100 || temperature > 100.4) {
        AgentCatalog catalog = new AgentCatalog();
        catalog.addAgent("http://localhost:8872/"); // Appointments
        
        // Schedule urgent appointment
        catalog.processQuery(
            "Schedule URGENT appointment for patient " + patientId + 
            " with on-call physician today"
        );
        
        result += " | ALERT: Urgent appointment scheduled due to abnormal vitals";
    }
    
    return result;
}
```

---

## Security and HIPAA Compliance

### Authentication and Authorization

```java
@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/health").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt());
        
        return http.build();
    }
}
```

### Audit Logging

```java
@Aspect
@Component
public class AuditAspect {
    
    @Around("@annotation(com.t4a.annotations.Action)")
    public Object auditAction(ProceedingJoinPoint joinPoint) throws Throwable {
        String actionName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        // Log access
        AuditLog log = new AuditLog();
        log.setAction(actionName);
        log.setTimestamp(new Date());
        log.setUserId(SecurityContextHolder.getContext().getAuthentication().getName());
        log.setParameters(Arrays.toString(args));
        
        auditRepository.save(log);
        
        return joinPoint.proceed();
    }
}
```

### Data Encryption

```java
@Service
public class EncryptionService {
    
    @Value("${encryption.key}")
    private String encryptionKey;
    
    public String encryptPHI(String data) {
        // Use AES-256 encryption for Protected Health Information
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKey key = new SecretKeySpec(encryptionKey.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }
    
    public String decryptPHI(String encryptedData) {
        // Decrypt PHI for authorized access
        // Implementation details...
    }
}
```

---

## Testing and Validation

### Unit Testing Individual Agents

```java
@SpringBootTest
@ActiveProfiles("test")
public class PatientRecordsServiceTest {
    
    @Autowired
    private PatientRecordsService service;
    
    @Test
    public void testCreatePatientRecord() {
        String result = service.createPatientRecord(
            "Test Patient",
            45,
            "O+",
            "123 Test St"
        );
        
        assertThat(result).contains("Patient record created");
        assertThat(result).contains("Test Patient");
        assertThat(result).contains("O+");
    }
    
    @Test
    public void testAddVitalSigns() {
        // First create patient
        String createResult = service.createPatientRecord(
            "Jane Doe",
            30,
            "A+",
            "456 Test Ave"
        );
        
        String patientId = extractPatientId(createResult);
        
        // Add vital signs
        String vitalResult = service.addVitalSigns(
            patientId,
            72,
            "120/80",
            98.6,
            16
        );
        
        assertThat(vitalResult).contains("Vital signs recorded");
        assertThat(vitalResult).contains("HR: 72");
    }
}
```

### Integration Testing Agent Mesh

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AgenticMeshIntegrationTest {
    
    @Test
    public void testCompletePatientWorkflow() throws Exception {
        AgentCatalog catalog = new AgentCatalog();
        catalog.addAgent("http://localhost:8871/");
        catalog.addAgent("http://localhost:8872/");
        catalog.addAgent("http://localhost:8873/");
        catalog.addAgent("http://localhost:8874/");
        
        AgenticMesh mesh = new AgenticMesh(catalog);
        
        String workflow = """
            1. Create patient record for Integration Test, age 40, blood type B+, address Test City
            2. Schedule appointment with Dr. Test on 2026-03-01
            3. Order lab tests: CBC, urgency ROUTINE
            4. Generate invoice for consultation $200
            """;
        
        Result result = mesh.pipeLineMesh(workflow);
        
        assertThat(result.getTextResult()).contains("Patient record created");
        assertThat(result.getTextResult()).contains("Appointment scheduled");
        assertThat(result.getTextResult()).contains("Lab test ordered");
        assertThat(result.getTextResult()).contains("Invoice generated");
    }
}
```

### Load Testing

```java
@Test
public void testConcurrentAgentRequests() throws Exception {
    AgentCatalog catalog = new AgentCatalog();
    catalog.addAgent("http://localhost:8871/");
    
    ExecutorService executor = Executors.newFixedThreadPool(50);
    List<Future<Result>> futures = new ArrayList<>();
    
    // Submit 1000 concurrent requests
    for (int i = 0; i < 1000; i++) {
        final int index = i;
        futures.add(executor.submit(() -> 
            catalog.processQuery("Create patient record for Test-" + index)
        ));
    }
    
    // Wait for all to complete
    for (Future<Result> future : futures) {
        Result result = future.get();
        assertThat(result.getTextResult()).contains("Patient record created");
    }
    
    executor.shutdown();
}
```

---

## Performance Optimization

### Caching Agent Responses

```java
@Service
public class CachedPatientRecordsService extends PatientRecordsService {
    
    private LoadingCache<String, String> patientCache;
    
    public CachedPatientRecordsService() {
        this.patientCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String patientId) {
                    return getMedicalHistory(patientId);
                }
            });
    }
    
    @Override
    public String getMedicalHistory(String patientId) {
        try {
            return patientCache.get(patientId);
        } catch (ExecutionException e) {
            return super.getMedicalHistory(patientId);
        }
    }
}
```

### Connection Pooling for Agent Communication

```java
@Configuration
public class AgentConnectionPoolConfig {
    
    @Bean
    public RestTemplate agentRestTemplate() {
        HttpComponentsClientHttpRequestFactory factory = 
            new HttpComponentsClientHttpRequestFactory();
        
        // Configure connection pool
        PoolingHttpClientConnectionManager connManager = 
            new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(200);
        connManager.setDefaultMaxPerRoute(20);
        
        CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(connManager)
            .build();
        
        factory.setHttpClient(httpClient);
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(10000);
        
        return new RestTemplate(factory);
    }
}
```

### Asynchronous Agent Calls

```java
@Service
public class AsyncAgentService {
    
    @Async
    public CompletableFuture<String> createPatientAsync(
        String name,
        int age,
        String bloodType
    ) {
        AgentCatalog catalog = new AgentCatalog();
        catalog.addAgent("http://localhost:8871/");
        
        Result result = catalog.processQuery(
            "Create patient record for " + name + ", age " + age + 
            ", blood type " + bloodType
        );
        
        return CompletableFuture.completedFuture(result.getTextResult());
    }
    
    public void processBatchPatients(List<PatientData> patients) {
        List<CompletableFuture<String>> futures = patients.stream()
            .map(p -> createPatientAsync(p.getName(), p.getAge(), p.getBloodType()))
            .collect(Collectors.toList());
        
        // Wait for all to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
```

---

## Deployment Strategies

### Docker Containerization

```dockerfile
# Dockerfile for each agent
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/classes /app/classes
COPY src/main/resources /app/resources

# Patient Records Agent
EXPOSE 8871

CMD ["java", "-cp", "/app/classes:/app/resources", \
     "org.example.patientrecords.PatientRecordsServer"]
```

### Docker Compose for Full Mesh

```yaml
version: '3.8'

services:
  patient-records:
    build: .
    command: java -cp /app/classes org.example.patientrecords.PatientRecordsServer
    ports:
      - "8871:8871"
    environment:
      - SPRING_PROFILES_ACTIVE=patientrecords
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8871/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3

  appointments:
    build: .
    command: java -cp /app/classes org.example.appointments.AppointmentsServer
    ports:
      - "8872:8872"
    environment:
      - SPRING_PROFILES_ACTIVE=appointments
    depends_on:
      - patient-records

  diagnostics:
    build: .
    command: java -cp /app/classes org.example.diagnostics.DiagnosticsServer
    ports:
      - "8873:8873"
    environment:
      - SPRING_PROFILES_ACTIVE=diagnostics
    depends_on:
      - patient-records

  billing:
    build: .
    command: java -cp /app/classes org.example.billing.BillingServer
    ports:
      - "8874:8874"
    environment:
      - SPRING_PROFILES_ACTIVE=billing
    depends_on:
      - patient-records

networks:
  healthcare-mesh:
    driver: bridge
```

### Kubernetes Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: patient-records-agent
spec:
  replicas: 3
  selector:
    matchLabels:
      app: patient-records
  template:
    metadata:
      labels:
        app: patient-records
    spec:
      containers:
      - name: patient-records
        image: healthcare-mesh/patient-records:latest
        ports:
        - containerPort: 8871
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "patientrecords"
        resources:
          requests:
            memory: "512Mi"
            cpu: "500m"
          limits:
            memory: "1Gi"
            cpu: "1000m"
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8871
          initialDelaySeconds: 30
          periodSeconds: 10
---
apiVersion: v1
kind: Service
metadata:
  name: patient-records-service
spec:
  selector:
    app: patient-records
  ports:
  - protocol: TCP
    port: 8871
    targetPort: 8871
  type: LoadBalancer
```

---

## Deployment Considerations

If you plan to deploy a healthcare agentic mesh beyond this demo, here are important areas to consider:

### Security & HIPAA Compliance
- [ ] **Authentication**: Implement OAuth 2.0/OIDC for all agent endpoints
- [ ] **Authorization**: Role-based access control (RBAC) with least-privilege principles
- [ ] **Encryption**: TLS 1.3 for all agent-to-agent communication
- [ ] **Data at Rest**: AES-256 encryption for PHI in databases
- [ ] **Audit Logging**: Comprehensive logging of all PHI access with user IDs, timestamps, and actions
- [ ] **HIPAA BAA**: Execute Business Associate Agreements with all third-party service providers
- [ ] **Data Retention**: Implement HIPAA-compliant retention policies (minimum 6 years)
- [ ] **Access Controls**: Multi-factor authentication (MFA) for all administrative access

### Observability & Monitoring
- [ ] **Distributed Tracing**: Implement OpenTelemetry or Jaeger to trace requests across agents
- [ ] **Metrics**: Expose Prometheus/Micrometer metrics for each agent (request latency, error rates, throughput)
- [ ] **Health Checks**: Implement `/actuator/health` endpoints for all agents with deep health checks
- [ ] **Log Aggregation**: Centralize logs using ELK Stack, Splunk, or CloudWatch
- [ ] **Alerting**: Set up alerts for agent failures, high latency (>2s), or error rate spikes (>5%)
- [ ] **Dashboard**: Create Grafana/Kibana dashboards showing mesh health and request flows
- [ ] **SLA Monitoring**: Track 99.9% uptime targets for each agent

### Failure Modes & Resilience
- [ ] **Circuit Breakers**: Implement Resilience4j circuit breakers for agent-to-agent calls
- [ ] **Timeouts**: Set appropriate timeouts (5s connect, 30s read) to prevent cascade failures
- [ ] **Retry Logic**: Exponential backoff with jitter for transient failures
- [ ] **Graceful Degradation**: Each agent should function independently if others are down
- [ ] **Bulkheads**: Isolate thread pools per agent to prevent resource exhaustion
- [ ] **Rate Limiting**: Implement rate limiting (e.g., 100 req/s per agent) to prevent overload
- [ ] **Disaster Recovery**: Automated backups with 4-hour RPO and 1-hour RTO
- [ ] **Chaos Engineering**: Run regular GameDays to test failure scenarios

### Rollout Plan
- [ ] **Canary Deployment**: Deploy to 5% of traffic, monitor for 24h, then gradually increase
- [ ] **Feature Flags**: Use flags (LaunchDarkly, Unleash) to enable agents gradually
- [ ] **Shadow Mode**: Run new agents alongside existing systems, comparing outputs without impacting users
- [ ] **Rollback Plan**: Document rollback procedures with <5 minute MTTR
- [ ] **Database Migration**: Use Flyway/Liquibase for versioned schema changes
- [ ] **Blue-Green Deployment**: Maintain two identical environments for zero-downtime deploys
- [ ] **Load Testing**: Validate each agent handles 10x expected peak load
- [ ] **User Acceptance Testing**: Get sign-off from clinical staff before GA

### Infrastructure & Performance
- [ ] **Database**: Replace in-memory Maps with PostgreSQL + HikariCP connection pooling
- [ ] **Caching**: Implement Redis for frequently accessed patient records (1-hour TTL)
- [ ] **Message Queues**: Use RabbitMQ/Kafka for async operations (lab results, billing events)
- [ ] **API Gateway**: Deploy Kong/Istio for unified auth, rate limiting, and routing
- [ ] **Service Mesh**: Consider Istio/Linkerd for mTLS, observability, and traffic management
- [ ] **Auto-scaling**: Configure horizontal pod autoscaling based on CPU (80%) and memory (85%)
- [ ] **CDN**: Use CloudFront/Akamai for static content and API response caching

### Compliance & Documentation
- [ ] **API Documentation**: Generate OpenAPI/Swagger docs for each agent
- [ ] **Runbooks**: Document common failure scenarios and remediation steps
- [ ] **Architecture Decision Records**: Maintain ADRs for all major design decisions
- [ ] **Penetration Testing**: Annual security audits by certified HIPAA auditors
- [ ] **Training**: Train operations team on mesh architecture and troubleshooting
- [ ] **Change Management**: Implement formal change approval process

---

## Conclusion

Building a healthcare agentic mesh with Java provides a cutting-edge approach to modernizing healthcare IT infrastructure. By leveraging **Spring Boot 3.2.4**, **A2A Java Library**, and **Tools4AI annotations**, you can:

✅ **Transform existing Java healthcare services** into intelligent agents with minimal code changes
✅ **Enable autonomous agent collaboration** across Patient Records, Appointments, Diagnostics, and Billing
✅ **Implement complex medical workflows** using AgentCatalog and AgenticMesh
✅ **Scale horizontally** by adding new specialized agents
✅ **Maintain HIPAA compliance** with proper security measures
✅ **Deploy flexibly** using Docker, Kubernetes, or traditional infrastructure

