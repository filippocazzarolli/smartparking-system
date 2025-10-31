package org.example.smartparkingsystem.entry;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking")
public class EntryController {

    private final EntryService entryService;
    private final ExitService exitService;

    public EntryController(EntryService entryService, ExitService exitService) {
        this.entryService = entryService;
        this.exitService = exitService;
    }

    @PostMapping("/entry")
    public ResponseEntity<String> entry(@RequestParam String vehicleNumber) {
        entryService.vehicleEntry(vehicleNumber);
        return ResponseEntity.ok("Vehicle entered: " + vehicleNumber);
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exit(@RequestParam String vehicleNumber) {
        exitService.vehicleExit(vehicleNumber);
        return ResponseEntity.ok("Vehicle exited: " + vehicleNumber);
    }
}