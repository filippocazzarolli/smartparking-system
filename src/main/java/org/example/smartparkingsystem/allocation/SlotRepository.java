package org.example.smartparkingsystem.allocation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    Optional<Slot> findFirstByAvailableTrue();

    Optional<Slot> findByVehicleNumber(String vehicleNumber);
}
