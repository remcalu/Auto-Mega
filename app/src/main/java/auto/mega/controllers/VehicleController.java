package auto.mega.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import auto.mega.models.Vehicle;
import auto.mega.parsers.ParserManager;
import auto.mega.respositories.VehicleRepository;

@RestController
public class VehicleController {

  VehicleController() {}

  @Autowired
  ParserManager parserManager;

  @Autowired
  VehicleRepository vehicleRepository;

	@GetMapping("/vehicle")
  public List<Vehicle> index(){
    return vehicleRepository.findAll();
  }

  @GetMapping("/vehicle/refetch")
  public boolean refetch(){
    for(Vehicle vehicle : parserManager.parseAllWebsites()) {
      vehicleRepository.save(vehicle);
    }
    return true;
  }

}