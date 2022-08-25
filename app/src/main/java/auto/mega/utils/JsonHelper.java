package auto.mega.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import auto.mega.models.ConfigOptions;
import auto.mega.models.Vehicle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class JsonHelper {

  private static final String PATH_POSTAL_COORDS_MAP = "src/main/resources/postalCoordMap.json";
  private static final String PATH_CONFIG_FILE = "src/main/resources/options.json";
  private static final String VEHICLE_OUTPUT_FILE_PATH = "src/main/resources/output/out.json";
  private static final String VEHICLE_OUTPUT_READABLE_FILE_PATH = "src/main/resources/output/outReadable.txt";

  private JsonHelper() {}
  
  public static ConfigOptions readConfigFile() {
    /* Getting options file data */
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonReader reader;
    try {
      reader = new JsonReader(new FileReader(PATH_CONFIG_FILE));
    } catch (FileNotFoundException e) {
      return null;
    }
    JsonObject optionsJsonObj = gson.fromJson(reader, JsonObject.class);

    return parseJsonIntoConfigOptions(optionsJsonObj);
  }

  public static List<Vehicle> readVehicleOutputJson() {
    /* Getting options file data */
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonReader reader;
    try {
      reader = new JsonReader(new FileReader(VEHICLE_OUTPUT_FILE_PATH));
    } catch (FileNotFoundException e) {
      return new ArrayList<>();
    }
    JsonArray vehicleListJsonArr = gson.fromJson(reader, JsonArray.class);

    return parseJsonIntoVehicleList(vehicleListJsonArr);
  }

  public static void writeVehicleOutputJson(List<Vehicle> vehicleList) {
    Gson gson = new GsonBuilder().
      disableHtmlEscaping().
      setPrettyPrinting().
      create();
      
    try (FileWriter writer = new FileWriter(VEHICLE_OUTPUT_FILE_PATH)) {
      writer.write(gson.toJson(vehicleList));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String vehicleListToJson(List<Vehicle> vehicleList) {
    Gson gson = new GsonBuilder().
    disableHtmlEscaping().
    setPrettyPrinting().
    create();
    
    return gson.toJson(vehicleList);
  }

  public static void writeVehicleListReadable(List<Vehicle> vehicleList) {      
    try (FileWriter writer = new FileWriter(VEHICLE_OUTPUT_READABLE_FILE_PATH)) {
      int printSeperationLineValue = (vehicleList.get(0).getPrice()/1000 * 1000) - 1;
      for (Vehicle vehicle : vehicleList) {

        if (vehicle.getPrice() > printSeperationLineValue) {
          printSeperationLineValue += 1000;
          writer.write("\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n");
        }

        writer.write(vehicle.toStringPretty());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static ConfigOptions parseJsonIntoConfigOptions(JsonObject optionsJsonObj) {

    /* VehicleBrandModels contains KV pair of a brand and list of models */
    JsonArray vehicleBrandModels = optionsJsonObj.getAsJsonArray("vehicleBrandModels");
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
      withMinYear(optionsJsonObj.get("minYear").getAsInt()).
      withMaxPrice(optionsJsonObj.get("maxPrice").getAsInt()).
      withMaxMileage(optionsJsonObj.get("maxMileage").getAsInt()).
      withDistance(optionsJsonObj.get("distance").getAsInt()).
      withPostalCode(optionsJsonObj.get("postalCode").getAsString()).
      withTransmission(optionsJsonObj.get("transmission").getAsString()).
      withIncludePrivateDealers(optionsJsonObj.get("includePrivateDealers").getAsBoolean()).
      build();
  }

  private static List<Vehicle> parseJsonIntoVehicleList(JsonArray vehicleListJsonArr) {
    /* VehicleBrandModels contains KV pair of a brand and list of models */
    ArrayList<Vehicle> vehicleList = new ArrayList<>();

    /* Looping through all brand objects */
    for (JsonElement vehicleJsonElement : vehicleListJsonArr) {
      JsonObject vehicleJsonObj = vehicleJsonElement.getAsJsonObject();
      vehicleList.add(new Vehicle.Builder().
        withLink(vehicleJsonObj.get("link") == null ? null : vehicleJsonObj.get("link").getAsString()).
        withBrand(vehicleJsonObj.get("brand") == null ? null : vehicleJsonObj.get("brand").getAsString()).
        withModel(vehicleJsonObj.get("model") == null ? null : vehicleJsonObj.get("model").getAsString()).
        withPrice(vehicleJsonObj.get("price") == null ? null : vehicleJsonObj.get("price").getAsInt()).
        withYear(vehicleJsonObj.get("year") == null ? null : vehicleJsonObj.get("year").getAsInt()).
        withMileage(vehicleJsonObj.get("mileage") == null ? null : vehicleJsonObj.get("mileage").getAsInt()).
        withDateScraped(vehicleJsonObj.get("dateScraped") == null ? null : vehicleJsonObj.get("dateScraped").getAsString()).
        withIsPrivateDealer(vehicleJsonObj.get("isPrivateDealer") == null ? null : vehicleJsonObj.get("isPrivateDealer").getAsBoolean()).
        withWebsite(vehicleJsonObj.get("website") == null ? null : vehicleJsonObj.get("website").getAsString()).
        build());
    }

    return vehicleList;
  }

  public static Pair<Double, Double> getCoordsFromPostal(String postalCode) {
    /* Getting options file data */
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonReader reader;
    try {
      reader = new JsonReader(new FileReader(PATH_POSTAL_COORDS_MAP));
    } catch (FileNotFoundException e) {
      return null;
    }
    JsonObject optionsJsonObj = gson.fromJson(reader, JsonObject.class);
    JsonObject postalCodeCoords = optionsJsonObj.get(postalCode.replace(" ", "").toUpperCase()).getAsJsonObject();
    if (postalCodeCoords == null) {
      /* Return toronto */
      return Pair.of(43.651070, -79.347015);
    }
    return Pair.of(postalCodeCoords.get("lat").getAsDouble(), postalCodeCoords.get("lon").getAsDouble());
  }
}
