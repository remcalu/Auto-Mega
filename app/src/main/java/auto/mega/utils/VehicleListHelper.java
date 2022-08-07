package auto.mega.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

import auto.mega.models.Vehicle;

public class VehicleListHelper {

  private VehicleListHelper() {}

  /* Method that combines two vehicle lists and updates ad scraped time ads */
  public static Pair<List<Vehicle>, Integer> updateOldVehicleList(List<Vehicle> oldVehicleList, List<Vehicle> newVehicleList) { 

    ArrayList<Vehicle> updatedVehicleList = new ArrayList<>();
    int numNewCars = 0;

    for (Vehicle newVehicle : newVehicleList) {
      boolean foundCar = false;

      for (Vehicle oldVehicle : oldVehicleList) {
        if (comparisonString(oldVehicle).equals(comparisonString(newVehicle))) {
          foundCar = true;
          updatedVehicleList.add(oldVehicle);
          break;
        }
      }

      if (!foundCar) {
        updatedVehicleList.add(newVehicle);
        numNewCars++;
      }
    }

    return Pair.of(updatedVehicleList, numNewCars);
  }

  /* Method that combines two vehicle lists */
  public static List<Vehicle> mergeVehicleLists(List<Vehicle> vehicleList1, List<Vehicle> vehicleList2) { 
    return Stream.concat(vehicleList1.stream(), vehicleList2.stream()).collect(Collectors.toList());
  }

  /* Method that sorts for a vehicle list */
  public static List<Vehicle> sortVehicleList(List<Vehicle> unsortedVehicleList) { 

    ArrayList<Vehicle> sortedVehicleList = new ArrayList<>();
    sortedVehicleList.addAll(
      unsortedVehicleList.stream().
      sorted(Comparator.comparingInt(Vehicle::getYear).reversed()).
      sorted(Comparator.comparingInt(Vehicle::getMileage)).
      sorted(Comparator.comparingInt(Vehicle::getPrice)).
      toList()
    );

    return sortedVehicleList;
  }

  /* Method that removes all dupes for a vehicle list */
  public static List<Vehicle> dedupeVehicleList(List<Vehicle> dupedVehicleList) { 

    ArrayList<Vehicle> dedupedVehicleList = new ArrayList<>();
    dedupedVehicleList.addAll(
      dupedVehicleList.stream().
      collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(VehicleListHelper::comparisonString))))
    );
    
    return dedupedVehicleList;
  }

  private static String comparisonString(Vehicle v) {
    return v.getBrand() + "|" + 
    v.getModel() + "|" + 
    v.getMileage() + "|" + 
    v.getPrice() + '|' + 
    v.getYear();
  }
}
