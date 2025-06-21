package com.app.hospital.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.hospital.Entity.Billing;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BillingRepository extends JpaRepository<Billing, Long> {

    @Query("SELECT SUM(b.amount) FROM Billing b WHERE b.patient.patientId = :patientId")
    Integer findTotalAmountByPatientId(@Param("patientId") Long patientId);
}
