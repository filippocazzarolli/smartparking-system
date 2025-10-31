package org.example.smartparkingsystem.notification;

import org.example.smartparkingsystem.event.VehicleEnteredEvent;
import org.example.smartparkingsystem.event.VehicleExitedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @EventListener
    public void notifyOnVehicleEntry(VehicleEnteredEvent event) {
        System.out.println("📩 Notification: Vehicle " + event.vehicleNumber() +
                " entered at " + event.entryTime() + ". Welcome!");
    }

    @EventListener
    public void notifyOnVehicleExit(VehicleExitedEvent event) {
        System.out.println("📩 Notification: Vehicle " + event.vehicleNumber() + " has exited. Thank you for visiting!");
    }
}
