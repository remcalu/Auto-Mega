package auto.mega;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.tuple.Pair;

import auto.mega.models.ConfigOptions;
import auto.mega.models.Vehicle;
import auto.mega.parsers.AutotraderParser;
import auto.mega.parsers.CarpagesParser;
import auto.mega.parsers.KijijiParser;
import auto.mega.utils.FileHelper;
import auto.mega.utils.VehicleListHelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

  public static void main(String[] args) throws InterruptedException, ExecutionException {

    SpringApplication.run(Main.class, args);
    
    ConfigOptions options = FileHelper.readConfigFile();

    /* Getting parsers */
    AutotraderParser autoTraderParser = new AutotraderParser();
    KijijiParser kijijiParser = new KijijiParser();
    CarpagesParser carpagesParser = new CarpagesParser();

    /* Getting new vehicle lists concurrently */
    CompletableFuture<Object> completableFuture = 
      CompletableFuture.supplyAsync(() -> {
        try {
          return autoTraderParser.parseWebsite(options);
        } catch (InterruptedException e) {
          e.printStackTrace();
          Thread.currentThread().interrupt();
        }
        return null;
      }).thenCombine(
      CompletableFuture.supplyAsync(() -> {
        try {
          return kijijiParser.parseWebsite(options);
        } catch (InterruptedException e) {
          e.printStackTrace();
          Thread.currentThread().interrupt();
        }
        return null;
      }).thenCombine(
      CompletableFuture.supplyAsync(() -> {
        try {
          return carpagesParser.parseWebsite(options);
        } catch (InterruptedException e) {
          e.printStackTrace();
          Thread.currentThread().interrupt();
        }
        return null;
      }), VehicleListHelper::mergeVehicleLists), VehicleListHelper::mergeVehicleLists);

    /* Getting old vehicle list */
    ArrayList<Vehicle> oldVehicles = (ArrayList<Vehicle>) FileHelper.readVehicleOutputJson();

    /* Populate new vehicle list with result of completeableFuture */
    ArrayList<Vehicle> newVehicles = new ArrayList<>();
    ArrayList<?> objectList = (ArrayList<?>) completableFuture.get();
    for (Object x : objectList) {
      newVehicles.add((Vehicle) x);
    }

    /* Filter list */
    Pair<List<Vehicle>, Integer> updatedVehiclesPair = VehicleListHelper.updateOldVehicleList(oldVehicles, newVehicles);
    ArrayList<Vehicle> updatedVehicles = (ArrayList<Vehicle>) updatedVehiclesPair.getLeft();
    updatedVehicles = (ArrayList<Vehicle>) VehicleListHelper.dedupeVehicleList(updatedVehicles);
    updatedVehicles = (ArrayList<Vehicle>) VehicleListHelper.sortVehicleList(updatedVehicles);

    /* Writing new data to files */
    FileHelper.writeVehicleOutputJson(updatedVehicles);
    FileHelper.writeVehicleListReadable(updatedVehicles);
  }
}
