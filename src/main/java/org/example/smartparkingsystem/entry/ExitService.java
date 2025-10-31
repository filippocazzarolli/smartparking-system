package org.example.smartparkingsystem.entry;

import org.example.smartparkingsystem.event.VehicleExitedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExitService {
    private final ParkingEntryRepository parkingEntryRepository;
    private final ApplicationEventPublisher publisher;

    public ExitService(ParkingEntryRepository parkingEntryRepository, ApplicationEventPublisher publisher) {
        this.parkingEntryRepository = parkingEntryRepository;
        this.publisher = publisher;
    }

    public void vehicleExit(String vehicleNumber) {
        ParkingEntry entry = parkingEntryRepository.findByVehicleNumberAndActiveTrue(vehicleNumber)
                .orElseThrow(() -> new RuntimeException("No active entry found for vehicle " + vehicleNumber));

        entry.setExitTime(LocalDateTime.now());
        entry.setActive(false);
        parkingEntryRepository.save(entry);
        publisher.publishEvent(new VehicleExitedEvent(vehicleNumber, entry.getEntryTime(), entry.getExitTime()));

    }
}
