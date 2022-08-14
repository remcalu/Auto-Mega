package auto.mega.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import auto.mega.models.ConfigOptions;
import auto.mega.models.Vehicle;
import auto.mega.parsers.autotrader.AutotraderParser;
import auto.mega.parsers.carpages.CarpagesParser;
import auto.mega.parsers.kijiji.KijijiParser;
import auto.mega.utils.Errors;
import auto.mega.utils.JsonHelper;
import auto.mega.utils.VehicleListHelper;

@Component
public class ParserManager {
  @Autowired
  AutotraderParser autotraderParser;

  @Autowired
  CarpagesParser carpagesParser;

  @Autowired
  KijijiParser kijijiParser;

  public List<Vehicle> parseAllWebsites() {
    ArrayList<Vehicle> allVehicles = new ArrayList<>();

    try {
      ConfigOptions options = JsonHelper.readConfigFile();

      CompletableFuture<Object> completableFuture = 
        CompletableFuture.supplyAsync(() -> {
          try {
            return autotraderParser.parseWebsite(options);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
          return null;
        }).thenCombine(
        CompletableFuture.supplyAsync(() -> {
          try {
            return kijijiParser.parseWebsite(options);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
          return null;
        }).thenCombine(
        CompletableFuture.supplyAsync(() -> {
          try {
            return carpagesParser.parseWebsite(options);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
          return null;
        }), VehicleListHelper::mergeVehicleLists), VehicleListHelper::mergeVehicleLists);

      /* Populate new vehicle list with result of completeableFuture */
      ArrayList<?> objectList = (ArrayList<?>) completableFuture.get();
      for (Object x : objectList) {
        allVehicles.add((Vehicle) x);
      }

      return allVehicles;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Errors.INTERRUPTED_THREAD);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Errors.UNEXPECTED_ERROR);
    }
  }

  public List<Vehicle> carpagesVehicles() {
    List<Vehicle> allVehicles;

    try {
      ConfigOptions options = JsonHelper.readConfigFile();
      allVehicles = carpagesParser.parseWebsite(options);
      return allVehicles;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Errors.INTERRUPTED_THREAD);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Errors.UNEXPECTED_ERROR);
    }
	}

  public List<Vehicle> autotraderVehicles() {
    List<Vehicle> allVehicles;

    try {
      ConfigOptions options = JsonHelper.readConfigFile();
      allVehicles = autotraderParser.parseWebsite(options);
      return allVehicles;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Errors.INTERRUPTED_THREAD);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Errors.UNEXPECTED_ERROR);
    }
	}

  	public List<Vehicle> kijijiVehicles() {
    List<Vehicle> allVehicles;

    try {
      ConfigOptions options = JsonHelper.readConfigFile();
      allVehicles = kijijiParser.parseWebsite(options);
      return allVehicles;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Errors.INTERRUPTED_THREAD);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Errors.UNEXPECTED_ERROR);
    }
	}
}
