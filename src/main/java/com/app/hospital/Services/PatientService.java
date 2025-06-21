package com.app.hospital.Services;

import com.app.hospital.Entity.Patient;
import com.app.hospital.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepo;

    public Patient createPatient(Patient patient) {
        return patientRepo.save(patient);
    }

    public Patient getPatientById(Long id) {
        return patientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Patient existing = getPatientById(id);
        existing.setName(updatedPatient.getName());
        existing.setPhoneNumber(updatedPatient.getPhoneNumber());
        existing.setAge(updatedPatient.getAge());
        existing.setGender(updatedPatient.getGender());
        return patientRepo.save(existing);
    }

    public void deletePatient(Long id) {
        patientRepo.deleteById(id);
    }

    public List<Patient> getPatientsOlderThan(int age) {
        return patientRepo.findPatientsOlderThan(age);
    }

    public Page<Patient> getPagedPatients(Pageable pageable) {
        return patientRepo.findAll(pageable);
    }
}
