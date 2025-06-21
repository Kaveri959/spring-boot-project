package com.app.hospital.Controller;

import com.app.hospital.Entity.Billing;
import com.app.hospital.Services.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billings")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PostMapping
    public ResponseEntity<Billing> create(@RequestBody Billing bill) {
        return new ResponseEntity<>(billingService.createBill(bill), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Billing> get(@PathVariable Long id) {
        return ResponseEntity.ok(billingService.getBillById(id));
    }

    @GetMapping
    public List<Billing> getAll() {
        return billingService.getAllBills();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Billing> update(@PathVariable Long id, @RequestBody Billing bill) {
        return ResponseEntity.ok(billingService.updateBill(id, bill));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        billingService.deleteBill(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total/{patientId}")
    public ResponseEntity<Integer> getTotalAmount(@PathVariable Long patientId) {
        return ResponseEntity.ok(billingService.getTotalBilledAmountForPatient(patientId));
    }
}
