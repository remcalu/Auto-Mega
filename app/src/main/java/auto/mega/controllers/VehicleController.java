package auto.mega.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import auto.mega.models.ConfigOptions;
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

	@GetMapping("/api/vehicles")
  public List<Vehicle> vehicles() {
    return vehicleRepository.findAll();
  }

  @GetMapping("/api/vehicles/count")
  public int count() {
    return vehicleRepository.findAll().size();
  }

  @PostMapping("/api/vehicles/refetch")
  public boolean refetch(@RequestBody String request) {
    vehicleRepository.deleteAllInBatch();
    ConfigOptions options = ControllerHelper.getConfigOptionsFromRequest(request);

    System.out.println(options);
    
    List<Vehicle> allVehicles = parserManager.parseAllWebsites(options);
    for (Vehicle vehicle : allVehicles) {
      vehicleRepository.save(vehicle);
    }
    return true;
  }

}