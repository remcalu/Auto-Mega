package auto.mega.controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

  private static final Logger logger = LogManager.getLogger(VehicleController.class);

  VehicleController() {}

  @Autowired
  ParserManager parserManager;

  @Autowired
  VehicleRepository vehicleRepository;

	@GetMapping("/api/vehicles")
  public List<Vehicle> vehicles() {
    String requestIdentifier = getRandomNumStr();

    logger.info("{} - Received from\tGET '/api/vehicles'", requestIdentifier);
    List<Vehicle> allVehicles = vehicleRepository.findAll();
    logger.info("{} - Responded from\tGET '/api/vehicles'", requestIdentifier);

    return allVehicles;
  }

  @GetMapping("/api/vehicles/count")
  public int count() {
    String requestIdentifier = getRandomNumStr();

    logger.info("{} - Received from\tGET '/api/vehicles/count'", requestIdentifier);
    int vehicleCount = vehicleRepository.findAll().size();
    logger.info("{} - Responded from\tGET '/api/vehicles/count'", requestIdentifier);
    return vehicleCount;
  }

  @GetMapping("/api/vehicles/clear")
  public boolean clear() {
    String requestIdentifier = getRandomNumStr();

    logger.info("{} - Received from\tGET '/api/vehicles/clear'", requestIdentifier);
    vehicleRepository.deleteAllInBatch();
    logger.info("{} - Responded from\tGET '/api/vehicles/clear'", requestIdentifier);
    return true;
  }

  @PostMapping("/api/vehicles/refetch")
  public List<String> refetch(@RequestBody String request) {
    String requestIdentifier = getRandomNumStr();

    try {      
      logger.info("{} - Received from\tPOST '/api/vehicles/refetch'", requestIdentifier);
      List<String> response = ControllerHelper.verifyConfigOptionsRequest(request);
      if (!response.isEmpty()) {
        logger.info("{} - Responded from\tPOST '/api/vehicles/refetch': Error(s)", requestIdentifier);
        return response;
      }
      
      vehicleRepository.deleteAllInBatch();
      ConfigOptions options = ControllerHelper.getConfigOptionsFromRequest(request);
      List<Vehicle> allVehicles = parserManager.parseAllWebsites(options);
      for (Vehicle vehicle : allVehicles) {
        vehicleRepository.save(vehicle);
      }
      logger.info("{} - Responded from\tPOST '/api/vehicles/refetch': Populated", requestIdentifier);
      return response;
    } catch (Exception e) {
      e.printStackTrace();
    }
    logger.info("{} - Responded from\tPOST '/api/vehicles/refetch': Empty", requestIdentifier);
    return new ArrayList<>();
  }

  public static String getRandomNumStr() {
    Random random = new Random();
    
    Integer randomNum = random.nextInt(900000) + 100000;
    return randomNum.toString();
  }
}