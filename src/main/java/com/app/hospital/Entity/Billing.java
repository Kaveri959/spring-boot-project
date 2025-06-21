package com.app.hospital.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Billing {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    private int amount;
    private String billDate;
    private String paymentmethod;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    private Patient patient;
}
