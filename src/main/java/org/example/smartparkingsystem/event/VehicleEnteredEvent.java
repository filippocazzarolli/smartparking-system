package org.example.smartparkingsystem.event;

import java.time.LocalDateTime;

public record VehicleEnteredEvent(
        String vehicleNumber,
        LocalDateTime entryTime
) {
}
