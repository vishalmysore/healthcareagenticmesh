# Healthcare Agentic Mesh: Comprehensive Healthcare Domain Implementation

## Overview

This project implements an Agentic Mesh for healthcare domain systems, demonstrating how multiple specialized AI agents work together to create a comprehensive healthcare platform. The mesh consists of four core healthcare components, each operating as an independent server while maintaining the ability to collaborate.

## Server Components

### 1. Patient Records Server (Port 8871)
**Purpose**: Complete patient information management

**Capabilities**:
- Create and manage patient records
- Retrieve medical history
- Update patient information
- Add medical notes
- Get vital signs history
- Search patients
- Manage immunization records

**Example Usage**:
```java
@Action(description = "Create a new patient record")
public String createPatientRecord(String patientName, int age, String bloodType, String address)
```

### 2. Appointments Server (Port 8872)
**Purpose**: Medical appointment scheduling and management

**Capabilities**:
- Schedule new appointments
- Get appointment details
- Cancel appointments
- Reschedule appointments
- Check available time slots
- View upcoming appointments
- Patient check-in
- Send appointment reminders

**Example Usage**:
```java
@Action(description = "Schedule a new medical appointment")
public String scheduleAppointment(String patientId, String doctorName, String appointmentType, String preferredDate)
```

### 3. Diagnostics Server (Port 8873)
**Purpose**: Medical diagnostics and laboratory operations

**Capabilities**:
- Order laboratory tests
- Get lab results
- Order medical imaging scans
- Retrieve imaging results
- Analyze diagnostic trends
- Create diagnostic reports
- Schedule follow-up tests

**Example Usage**:
```java
@Action(description = "Order laboratory tests for a patient")
public String orderLabTests(String patientId, String testType, String urgency)
```

### 4. Billing Server (Port 8874)
**Purpose**: Medical billing and insurance management

**Capabilities**:
- Generate invoices
- Process payments
- Submit insurance claims
- Track claim status
- Get account balance
- Set up payment plans
- Generate billing statements
- Verify insurance coverage

**Example Usage**:
```java
@Action(description = "Generate invoice for medical services")
public String generateInvoice(String patientId, String serviceType, double amount)
```

## Architecture

### Technology Stack
- **Framework**: Spring Boot 3.2.4
- **Java Version**: 17
- **Protocols**: MCP (Model Context Protocol), A2A (Agent-to-Agent)
- **Communication**: JSON-RPC 2.0
- **Port Range**: 8871-8874

### Server Configuration
Each server runs independently with:
- Dedicated port assignment
- Spring profile-based configuration
- Domain-specific service layer
- Tools4AI integration

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Spring Boot 3.x

### Building the Project
```bash
mvn clean compile
```

### Starting Individual Servers

**Patient Records Server**:
```bash
java -cp target/classes org.example.patientrecords.PatientRecordsServer
```

**Appointments Server**:
```bash
java -cp target/classes org.example.appointments.AppointmentsServer
```

**Diagnostics Server**:
```bash
java -cp target/classes org.example.diagnostics.DiagnosticsServer
```

**Billing Server**:
```bash
java -cp target/classes org.example.billing.BillingServer
```

## Using the Healthcare Mesh

### Mesh Client Example
```java
AgentCatalog agentCatalog = new AgentCatalog();

// Connect all healthcare agents
agentCatalog.addAgent("http://localhost:8871/"); // Patient Records
agentCatalog.addAgent("http://localhost:8872/"); // Appointments
agentCatalog.addAgent("http://localhost:8873/"); // Diagnostics
agentCatalog.addAgent("http://localhost:8874/"); // Billing

// Execute complex healthcare workflow
String result = agentCatalog.processQuery(
    "Schedule appointment for patient PT-12345, order lab tests, and generate invoice"
).getTextResult();
```

## API Testing

### Tool Discovery
```bash
curl -H "Content-Type: application/json" `
-d '{"jsonrpc":"2.0","method":"tools/list","params":{},"id":1}' `
http://localhost:8871/
```

### Creating a Patient Record
```bash
curl -H "Content-Type: application/json" `
-d '{
    "jsonrpc": "2.0",
    "method": "tools/call",
    "params": {
        "name": "createPatientRecord",
        "arguments": {
            "provideAllValuesInPlainEnglish": "{\"patientName\":\"John Doe\",\"age\":45,\"bloodType\":\"O+\",\"address\":\"123 Main St\"}"
        }
    },
    "id": 2
}' `
http://localhost:8871/
```

### Scheduling an Appointment
```bash
curl -H "Content-Type: application/json" `
-d '{
    "jsonrpc": "2.0",
    "method": "tools/call",
    "params": {
        "name": "scheduleAppointment",
        "arguments": {
            "provideAllValuesInPlainEnglish": "{\"patientId\":\"PT-12345\",\"doctorName\":\"Johnson\",\"appointmentType\":\"Cardiology\",\"preferredDate\":\"2026-02-10\"}"
        }
    },
    "id": 3
}' `
http://localhost:8872/
```

## Use Cases

### 1. Patient Onboarding
```
1. Create patient record
2. Schedule initial appointment
3. Order baseline diagnostic tests
4. Set up billing and insurance verification
```

### 2. Appointment Workflow
```
- Patient schedules appointment
- System checks doctor availability
- Sends confirmation and reminder
- Patient checks in on appointment day
- Generates invoice after visit
```

### 3. Diagnostic Workflow
```
- Doctor orders lab tests
- Patient visits lab
- Lab processes tests
- Results uploaded to system
- Doctor reviews and adds notes
- Billing generates invoice
```

### 4. Billing and Insurance
```
- Service rendered
- Invoice generated
- Insurance claim submitted
- Claim processed
- Patient billed for copay
- Payment plan offered if needed
```

## Healthcare Workflows Supported

- **Patient Registration**: Demographics → Medical history → Insurance verification
- **Appointment Management**: Scheduling → Reminders → Check-in → Visit documentation
- **Diagnostics**: Test ordering → Sample collection → Results → Review
- **Billing**: Service documentation → Invoice generation → Insurance claims → Payment processing

## Security & Compliance

- HIPAA compliance for patient data protection
- Secure authentication and authorization
- Encrypted data transmission
- Audit logging for all patient data access
- Role-based access control (RBAC)
- Data retention policies

## Future Enhancements

- Prescription management agent
- Emergency room triage agent
- Telemedicine integration agent
- Health analytics and reporting agent
- Medical device integration agent
- Clinical decision support agent

## Project Structure
```
healthcareagenticmesh/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── example/
│   │   │           ├── patientrecords/
│   │   │           │   ├── PatientRecordsServer.java
│   │   │           │   └── PatientRecordsService.java
│   │   │           ├── appointments/
│   │   │           │   ├── AppointmentsServer.java
│   │   │           │   └── AppointmentsService.java
│   │   │           ├── diagnostics/
│   │   │           │   ├── DiagnosticsServer.java
│   │   │           │   └── DiagnosticsService.java
│   │   │           ├── billing/
│   │   │           │   ├── BillingServer.java
│   │   │           │   └── BillingService.java
│   │   │           └── healthcareclient/
│   │   │               └── HealthcareMeshClient.java
│   │   └── resources/
│   │       ├── application-patientrecords.properties
│   │       ├── application-appointments.properties
│   │       ├── application-diagnostics.properties
│   │       ├── application-billing.properties
│   │       ├── tools4ai_patientrecords.properties
│   │       ├── tools4ai_appointments.properties
│   │       ├── tools4ai_diagnostics.properties
│   │       └── tools4ai_billing.properties
├── pom.xml
└── README.md
```

## License

MIT License - See LICENSE file for details

## Contact

For questions or contributions, please refer to the main project documentation.
