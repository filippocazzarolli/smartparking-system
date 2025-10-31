package org.example.smartparkingsystem.entry;

import org.example.smartparkingsystem.event.VehicleEnteredEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EntryService {

    private final ParkingEntryRepository parkingEntryRepository;
    private final ApplicationEventPublisher publisher;

    public EntryService(
            ParkingEntryRepository parkingEntryRepository,
            ApplicationEventPublisher publisher
    ) {
        this.parkingEntryRepository = parkingEntryRepository;
        this.publisher = publisher;
    }

    public void vehicleEntry(String vehicleNumber) {
        ParkingEntry entry = new ParkingEntry(null, vehicleNumber, LocalDateTime.now(), null, true);
        parkingEntryRepository.save(entry);

        // publish an event
        publisher.publishEvent(new VehicleEnteredEvent(vehicleNumber, entry.getEntryTime()));
    }

}
