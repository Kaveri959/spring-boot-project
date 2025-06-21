package com.app.hospital.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hospital.Entity.Billing;
import com.app.hospital.Repository.BillingRepository;

import java.util.List;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepo;

    public Billing createBill(Billing bill) {
        return billingRepo.save(bill);
    }

    public Billing getBillById(Long id) {
        return billingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing record not found"));
    }

    public List<Billing> getAllBills() {
        return billingRepo.findAll();
    }

    public Billing updateBill(Long id, Billing updated) {
        Billing existing = getBillById(id);
        existing.setAmount(updated.getAmount());
        existing.setBillDate(updated.getBillDate());
        existing.setPaymentmethod(updated.getPaymentmethod());
        existing.setPatient(updated.getPatient());
        return billingRepo.save(existing);
    }

    public void deleteBill(Long id) {
        billingRepo.deleteById(id);
    }

    public Integer getTotalBilledAmountForPatient(Long patientId) {
        return billingRepo.findTotalAmountByPatientId(patientId);
    }
}
