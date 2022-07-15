package auto.mega.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import auto.mega.models.ConfigOptions;
import auto.mega.models.Vehicle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class UtilMethods {
  
  private static UtilMethods singleInstance = null;

  public static UtilMethods getInstance()
  {
    if (singleInstance == null) {
      singleInstance = new UtilMethods();
    }
    return singleInstance;
  }

  public ConfigOptions parseConfigFile() {

    /* Getting options file data */
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonReader reader;
    try {
      reader = new JsonReader(new FileReader(Constants.PATH_CONFIG_FILE));
    } catch (FileNotFoundException e) {
      return null;
    }
    JsonObject options = gson.fromJson(reader, JsonObject.class);

    return parseJsonIntoConfigOptions(options);
  }

  public void createCarOutputJson(List<Vehicle> vehicleList) {
    Gson gson = new GsonBuilder().
      disableHtmlEscaping().
      setPrettyPrinting().
      create();
      
    try (FileWriter writer = new FileWriter("src/main/resources/output/out.json")) {
      writer.write(gson.toJson(vehicleList));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private ConfigOptions parseJsonIntoConfigOptions(JsonObject options) {
    /* VehicleBrandModels contains KV pair of a brand and list of models */
    JsonArray vehicleBrandModels = options.getAsJsonArray("vehicleBrandModels");
    LinkedHashMap<String, ArrayList<String>> vehicleBrandModelsParsed = new LinkedHashMap<>();

    /* Looping through all brand objects */
    for (JsonElement vehicleBrand : vehicleBrandModels) {
      String vehicleBrandString = vehicleBrand.getAsJsonObject().keySet().iterator().next();
      JsonArray vehicleBrandArray = vehicleBrand.getAsJsonObject().get(vehicleBrandString).getAsJsonArray();

      /* Looping through all model */
      ArrayList<String> vehicleModelStringArray = new ArrayList<>();
      for (JsonElement vehicleModel : vehicleBrandArray) {
        vehicleModelStringArray.add(vehicleModel.getAsString());
      }
      vehicleBrandModelsParsed.put(vehicleBrandString, vehicleModelStringArray);
    }

    return new ConfigOptions.Builder().
      withVehicleBrandModels(vehicleBrandModelsParsed).
      withMinYear(options.get("minYear").getAsInt()).
      withMaxPrice(options.get("maxPrice").getAsInt()).
      withMaxMileage(options.get("maxMileage").getAsInt()).
      withDistanceFromPostalCode(options.get("distanceFromPostalCode").getAsInt()).
      withPostalCode(options.get("postalCode").getAsString()).
      withTransmission(options.get("transmission").getAsString()).
      withIncludePrivateDealers(options.get("includePrivateDealers").getAsBoolean()).
      build();
  }
}
