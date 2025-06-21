package com.app.hospital.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hospital.Entity.Appointment;
import com.app.hospital.Repository.AppointmentRepository;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepo.save(appointment);
    }

    public Appointment getAppointment(Long id) {
        return appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    public List<Appointment> getAllAppointmentsWithDetails() {
        return appointmentRepo.findAllAppointmentsWithDetails();
    }

    public Appointment updateAppointment(Long id, Appointment updated) {
        Appointment existing = getAppointment(id);
        existing.setAppointmentDate(updated.getAppointmentDate());
        existing.setStatus(updated.getStatus());
        existing.setPatient(updated.getPatient());
        existing.setDoctor(updated.getDoctor());
        return appointmentRepo.save(existing);
    }

    public void deleteAppointment(Long id) {
        appointmentRepo.deleteById(id);
    }
}
