package auto.mega.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import auto.mega.models.Vehicle;

public class VehicleFilter {
  
  public List<Vehicle> filterVehicleList(List<Vehicle> unfilteredVehicleList) { 

    ArrayList<Vehicle> filteredVehicleList = new ArrayList<>();
    filteredVehicleList.addAll(
      unfilteredVehicleList.stream().
      collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(this::filterDupes)))).
      stream().
      sorted(Comparator.comparingInt(Vehicle::getYear).reversed()).
      sorted(Comparator.comparingInt(Vehicle::getMileage)).
      sorted(Comparator.comparingInt(Vehicle::getPrice)).
      toList()

    );
    
    for (Vehicle curVehicle : filteredVehicleList) {
      System.out.print(curVehicle.toStringPretty());
    }
    System.out.println(unfilteredVehicleList.size());
    System.out.println(filteredVehicleList.size());
    return(filteredVehicleList);
  }

  private String filterDupes(Vehicle v) {
    return v.getBrand() + "-" + 
    v.getModel() + "-" + 
    v.getMileage() + "-" + 
    v.getPrice() + '-' + 
    v.getYear();
  }
}
