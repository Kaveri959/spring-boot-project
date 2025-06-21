package com.app.hospital.Controller;          // ⬅︎ use lowercase “controller”

import com.app.hospital.Entity.Patient;
import com.app.hospital.Services.PatientService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;   // ⬅︎ add import
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@SecurityRequirement(name = "BearerAuth")        // class‑level path
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    /** 🔐 only admins */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping                       // ✅ NO extra “/api/patients”
    public ResponseEntity<Patient> create(@RequestBody Patient p) {
        return new ResponseEntity<>(patientService.createPatient(p),
                                    HttpStatus.CREATED);
    }

    /** 🔓 Any logged‑in user can fetch one patient */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<Patient> get(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    /** 🔓 Any logged‑in user can fetch all patients */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<Patient> getAll() {
        return patientService.getAllPatients();
    }

    /** 🔐 Only admins can update */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable Long id, @RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.updatePatient(id, patient));
    }

    /** 🔐 Only admins can delete */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    /** 🔓 Any logged‑in user */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/older-than/{age}")
    public List<Patient> getPatientsOlderThan(@PathVariable int age) {
        return patientService.getPatientsOlderThan(age);
    }

    /** 🔓 Any logged‑in user can page & sort */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/page")
    public Page<Patient> getPatientsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "patientId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return patientService.getPagedPatients(pageable);
    }
}
