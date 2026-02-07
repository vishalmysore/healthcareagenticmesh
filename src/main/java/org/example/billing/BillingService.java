package org.example.billing;

import com.t4a.annotations.Action;
import com.t4a.annotations.Agent;
import org.springframework.stereotype.Service;

@Agent(groupName = "billingOperations")
@Service
public class BillingService {

    @Action(description = "Generate invoice for medical services")
    public String generateInvoice(String patientId, String serviceType, double amount) {
        String invoiceId = "INV-" + (System.currentTimeMillis() % 100000);
        double tax = amount * 0.08;
        double totalAmount = amount + tax;
        
        return String.format("Invoice Generated Successfully!\n" +
               "Invoice ID: %s\n" +
               "Patient ID: %s\n" +
               "=================================\n" +
               "Service Type: %s\n" +
               "Service Amount: $%.2f\n" +
               "Tax (8%%): $%.2f\n" +
               "Total Amount: $%.2f\n" +
               "Invoice Date: %s\n" +
               "Due Date: %s\n" +
               "Status: PENDING PAYMENT\n" +
               "Payment Methods: Cash, Credit Card, Insurance", 
               invoiceId, patientId, serviceType, amount, tax, totalAmount,
               java.time.LocalDate.now(),
               java.time.LocalDate.now().plusDays(30));
    }

    @Action(description = "Process patient payment")
    public String processPayment(String invoiceId, double amount, String paymentMethod) {
        String paymentId = "PAY-" + (System.currentTimeMillis() % 100000);
        return String.format("Payment Processed Successfully!\n" +
               "Payment ID: %s\n" +
               "Invoice ID: %s\n" +
               "=================================\n" +
               "Payment Amount: $%.2f\n" +
               "Payment Method: %s\n" +
               "Payment Date: %s\n" +
               "Status: COMPLETED\n" +
               "Confirmation Number: %s\n" +
               "Receipt sent to patient's email\n" +
               "Remaining Balance: $0.00", 
               paymentId, invoiceId, amount, paymentMethod,
               java.time.LocalDateTime.now(), paymentId);
    }

    @Action(description = "Submit insurance claim")
    public String submitInsuranceClaim(String patientId, String insuranceProvider, String serviceCode, double claimAmount) {
        String claimId = "CLM-" + (System.currentTimeMillis() % 100000);
        return String.format("Insurance Claim Submitted!\n" +
               "Claim ID: %s\n" +
               "Patient ID: %s\n" +
               "=================================\n" +
               "Insurance Provider: %s\n" +
               "Service Code: %s\n" +
               "Claim Amount: $%.2f\n" +
               "Submission Date: %s\n" +
               "Status: SUBMITTED\n" +
               "Expected Processing: 7-14 business days\n" +
               "You will be notified once the claim is processed", 
               claimId, patientId, insuranceProvider, serviceCode, claimAmount,
               java.time.LocalDate.now());
    }

    @Action(description = "Get insurance claim status")
    public String getClaimStatus(String claimId) {
        return String.format("Insurance Claim Status\n" +
               "Claim ID: %s\n" +
               "=================================\n" +
               "Status: APPROVED\n" +
               "Claim Amount: $1,500.00\n" +
               "Approved Amount: $1,350.00\n" +
               "Patient Responsibility: $150.00 (Copay)\n" +
               "Insurance Payment: $1,350.00\n" +
               "Submission Date: %s\n" +
               "Approval Date: %s\n" +
               "Payment Expected: Within 5 business days\n" +
               "EOB (Explanation of Benefits) sent to patient", 
               claimId,
               java.time.LocalDate.now().minusDays(10),
               java.time.LocalDate.now().minusDays(3));
    }

    @Action(description = "Get patient account balance")
    public String getAccountBalance(String patientId) {
        return String.format("Account Balance for Patient %s\n" +
               "=================================\n" +
               "Current Balance: $450.00\n" +
               "Insurance Pending: $1,350.00\n" +
               "Total Outstanding: $1,800.00\n\n" +
               "Recent Charges:\n" +
               "  Office Visit (02/01/26): $150.00 - PAID\n" +
               "  Lab Work (02/05/26): $300.00 - PENDING\n" +
               "  X-Ray (02/10/26): $450.00 - PENDING\n\n" +
               "Payment History:\n" +
               "  01/15/26: $150.00 (Office Visit) - PAID\n" +
               "Last Payment: $150.00 on %s", 
               patientId, java.time.LocalDate.now().minusDays(15));
    }

    @Action(description = "Set up payment plan")
    public String setupPaymentPlan(String patientId, double totalAmount, int numberOfMonths) {
        String planId = "PLAN-" + (System.currentTimeMillis() % 100000);
        double monthlyPayment = totalAmount / numberOfMonths;
        
        return String.format("Payment Plan Created!\n" +
               "Plan ID: %s\n" +
               "Patient ID: %s\n" +
               "=================================\n" +
               "Total Amount: $%.2f\n" +
               "Plan Duration: %d months\n" +
               "Monthly Payment: $%.2f\n" +
               "First Payment Due: %s\n" +
               "Payment Method: Auto-debit from checking account\n" +
               "Status: ACTIVE\n" +
               "No interest charges applied\n" +
               "Payment reminders will be sent 5 days before each due date", 
               planId, patientId, totalAmount, numberOfMonths, monthlyPayment,
               java.time.LocalDate.now().plusDays(30));
    }

    @Action(description = "Generate billing statement")
    public String generateStatement(String patientId, String statementPeriod) {
        return String.format("Billing Statement\n" +
               "Patient ID: %s\n" +
               "Statement Period: %s\n" +
               "=================================\n" +
               "Generated: %s\n\n" +
               "Previous Balance: $0.00\n" +
               "New Charges: $900.00\n" +
               "Insurance Payments: ($1,350.00)\n" +
               "Patient Payments: ($150.00)\n" +
               "Adjustments: ($50.00)\n" +
               "Current Balance: $450.00\n\n" +
               "Itemized Charges:\n" +
               "  Office Visit: $150.00\n" +
               "  Laboratory Tests: $300.00\n" +
               "  X-Ray Imaging: $450.00\n\n" +
               "Amount Due: $450.00\n" +
               "Due Date: %s\n" +
               "Pay Online: www.medicalcenter.com/pay", 
               patientId, statementPeriod, java.time.LocalDate.now(),
               java.time.LocalDate.now().plusDays(30));
    }

    @Action(description = "Verify insurance coverage")
    public String verifyInsurance(String patientId, String insuranceProvider, String policyNumber) {
        return String.format("Insurance Verification Results\n" +
               "Patient ID: %s\n" +
               "=================================\n" +
               "Insurance Provider: %s\n" +
               "Policy Number: %s\n" +
               "Status: ACTIVE\n" +
               "Verification Date: %s\n\n" +
               "Coverage Details:\n" +
               "  Effective Date: 2025-01-01\n" +
               "  Expiration Date: 2026-12-31\n" +
               "  Deductible: $1,000 (Met: $750)\n" +
               "  Out-of-Pocket Max: $5,000 (Met: $2,100)\n" +
               "  Copay - Office Visit: $30\n" +
               "  Copay - Specialist: $50\n" +
               "  Copay - ER: $250\n" +
               "  Coinsurance: 20%% after deductible\n\n" +
               "Status: VERIFIED - Coverage Active", 
               patientId, insuranceProvider, policyNumber,
               java.time.LocalDate.now());
    }
}
