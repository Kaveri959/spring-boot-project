package com.app.hospital.Services;

import com.app.hospital.Entity.Doctor;
import com.app.hospital.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepo;

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepo.save(doctor);
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public Doctor updateDoctor(Long id, Doctor updated) {
        Doctor existing = getDoctorById(id);
        existing.setName(updated.getName());
        existing.setSpecialization(updated.getSpecialization());
        existing.setPhoneNumber(updated.getPhoneNumber());
        existing.setDepartment(updated.getDepartment());
        return doctorRepo.save(existing);
    }

    public void deleteDoctor(Long id) {
        doctorRepo.deleteById(id);
    }

    public List<Doctor> getDoctorsByDepartment(String dept) {
        return doctorRepo.findByDepartment(dept);
    }
}
