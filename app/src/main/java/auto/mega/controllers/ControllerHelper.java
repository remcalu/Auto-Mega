package auto.mega.controllers;

import auto.mega.models.ConfigOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ControllerHelper {
  
  private ControllerHelper() {}

  public static List<String> verifyConfigOptionsRequest(String requestJsonString) {
    Gson gson = new Gson();
    JsonObject configOptionsJson = gson.fromJson(requestJsonString, JsonObject.class);
    
    ArrayList<String> errors = new ArrayList<>();
    String postalCode = configOptionsJson.get("postalCode").getAsString();
    if (!postalCode.matches("^[A-Za-z]\\d[A-Za-z][ -]?\\d[A-Za-z]\\d$")) {
      errors.add("Invalid postal code");
    }

    JsonArray vehiclesArray = configOptionsJson.get("vehicles").getAsJsonArray();
    if (vehiclesArray.isEmpty()) {
      errors.add("Choose at least one vehicle");
    }

    JsonArray vendorTypeArray = configOptionsJson.get("vendorType").getAsJsonArray();
    if (vendorTypeArray.isEmpty()) {
      errors.add("Choose at least one vendor type");
    }

    JsonArray transmissionArray = configOptionsJson.get("transmission").getAsJsonArray();
    if (transmissionArray.isEmpty()) {
      errors.add("Choose at least one transmission type");
    }

    return errors;
  }

  public static ConfigOptions getConfigOptionsFromRequest(String requestJsonString) {
    Gson gson = new Gson();
    JsonObject configOptionsJson = gson.fromJson(requestJsonString, JsonObject.class);

    String postalCode = configOptionsJson.get("postalCode").getAsString();
    int distance = configOptionsJson.get("distance").getAsInt();
    int maxPrice = configOptionsJson.get("maxPrice").getAsInt();
    int maxMileage = configOptionsJson.get("maxMileage").getAsInt();
    int minYear = configOptionsJson.get("minYear").getAsInt();

    Map<String, ArrayList<String>> vehicleBrandModels = getVehiclesFromJson(configOptionsJson);
    boolean includePrivateDealers = getIncludePrivateDealersFromJson(configOptionsJson);
    String transmission = getTransmission(configOptionsJson);

    return new ConfigOptions.Builder()
      .withVehicleBrandModels(vehicleBrandModels)
      .withIncludePrivateDealers(includePrivateDealers)
      .withPostalCode(postalCode)
      .withDistance(distance)
      .withMaxPrice(maxPrice)
      .withMaxMileage(maxMileage)
      .withMinYear(minYear)
      .withTransmission(transmission)
      .build();
  }

  private static Map<String, ArrayList<String>> getVehiclesFromJson(JsonObject configOptionsJson) {
    HashMap<String, ArrayList<String>> vehicleBrandModels = new HashMap<>();
    JsonArray vehiclesArray = configOptionsJson.get("vehicles").getAsJsonArray();
    for (JsonElement vehicleElement : vehiclesArray) {
      String vehicleElementString = vehicleElement.getAsString();
      String[] splitVehicleWords = vehicleElementString.split(" ");
      populateVehiclesFromSplit(splitVehicleWords, vehicleBrandModels);
    }
    return vehicleBrandModels;
  }

  private static void populateVehiclesFromSplit(String[] splitVehicleWords, Map<String, ArrayList<String>> vehicleBrandModels) {
    String brand = splitVehicleWords[0].toLowerCase();
    String model = splitVehicleWords[1].toLowerCase();

    if (!vehicleBrandModels.containsKey(brand)) {
      vehicleBrandModels.put(brand, new ArrayList<>());
    }

    if ("3".equals(model)) {
      model = "mazda 3";
    } else if ("6".equals(model)) {
      model = "mazda 6";
    }
    vehicleBrandModels.get(brand).add(model.toLowerCase());
  }

  private static boolean getIncludePrivateDealersFromJson(JsonObject configOptionsJson) {
    JsonArray vendorTypeArray = configOptionsJson.get("vendorType").getAsJsonArray();
    for (JsonElement vendorType : vendorTypeArray) {
      if ("Private".equalsIgnoreCase(vendorType.getAsString())) {
        return true;
      }
    }
    return false;
  }

  private static String getTransmission(JsonObject configOptionsJson) {
    JsonArray transmissionArray = configOptionsJson.get("transmission").getAsJsonArray();
    for (JsonElement transmission : transmissionArray) {
      return transmission.getAsString();
    }
    return "";
  }
}
