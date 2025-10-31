package org.example.smartparkingsystem.allocation;

import org.example.smartparkingsystem.event.VehicleEnteredEvent;
import org.example.smartparkingsystem.event.VehicleExitedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class SlotAllocationService {

    private final SlotRepository slotRepository;

    public SlotAllocationService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @EventListener
    public void handleVehicleEntry(VehicleEnteredEvent event) {
        Slot slot = slotRepository.findFirstByAvailableTrue()
                .orElseThrow(() -> new RuntimeException("No available slots"));

        slot.setAvailable(false);
        slot.setVehicleNumber(event.vehicleNumber());
        slotRepository.save(slot);

        System.out.println("🅿️ Allocated Slot " + slot.getSlotCode() + " to vehicle " + event.vehicleNumber());
    }

    @EventListener
    public void handleVehicleExit(VehicleExitedEvent event) {
        slotRepository.findByVehicleNumber(event.vehicleNumber())
                .ifPresentOrElse(slot -> {
                    slot.setAvailable(true);
                    slot.setVehicleNumber(null);
                    slotRepository.save(slot);
                    System.out.println("✅ Slot " + slot.getSlotCode() + " released for vehicle " + event.vehicleNumber());
                }, () -> {
                    System.out.println("❌ No slot found for vehicle " + event.vehicleNumber());
                });
    }

}
