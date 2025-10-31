package org.example.smartparkingsystem;

import org.example.smartparkingsystem.allocation.Slot;
import org.example.smartparkingsystem.allocation.SlotRepository;
import org.example.smartparkingsystem.entry.ParkingEntry;
import org.example.smartparkingsystem.entry.ParkingEntryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SmartparkingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartparkingSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner init(SlotRepository slotRepository) {
        return args -> {
           if (slotRepository.count() == 0) {
               slotRepository.save(new Slot(null, "A1", true, null));
               slotRepository.save(new Slot(null, "A2", true, null));
               slotRepository.save(new Slot(null, "B1", true, null));
           }
        };
    }

}
