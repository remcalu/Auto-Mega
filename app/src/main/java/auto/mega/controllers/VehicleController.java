package auto.mega.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

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
  public String refetch(@RequestBody String request) {
    String verifyParams = ControllerHelper.verifyConfigOptionsRequest(request);
    if (verifyParams.isBlank()) {
      verifyParams = "Success";
    }
    
    Gson gson = new Gson();
    String response = gson.toJson(verifyParams);
    if ("Success".equals(verifyParams)) {
      vehicleRepository.deleteAllInBatch();
      ConfigOptions options = ControllerHelper.getConfigOptionsFromRequest(request);
      List<Vehicle> allVehicles = parserManager.parseAllWebsites(options);
      for (Vehicle vehicle : allVehicles) {
        vehicleRepository.save(vehicle);
      }
      return response;
    }
    return response;
  }

}