package auto.mega.controllers;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

  @GetMapping("/api/vehicles/clear")
  public boolean clear() {
    vehicleRepository.deleteAllInBatch();
    return true;
  }

  @PostMapping("/api/vehicles/refetch")
  public List<String> refetch(@RequestBody String request) {
    try {      
      List<String> response = ControllerHelper.verifyConfigOptionsRequest(request);
      if (!response.isEmpty()) {
        return response;
      }
      
      vehicleRepository.deleteAllInBatch();
      ConfigOptions options = ControllerHelper.getConfigOptionsFromRequest(request);
      List<Vehicle> allVehicles = parserManager.parseAllWebsites(options);
      for (Vehicle vehicle : allVehicles) {
        vehicleRepository.save(vehicle);
      }
      return response;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

}