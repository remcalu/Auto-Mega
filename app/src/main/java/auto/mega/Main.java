package auto.mega;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import auto.mega.models.Vehicle;
import auto.mega.parsers.AutotraderParser;
import auto.mega.utils.Constants;
import auto.mega.utils.UtilMethods;
import auto.mega.utils.VehicleFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class Main {

  public static void main(String[] args) throws InterruptedException {

    /* Getting options file data */
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonReader reader;
    try {
      reader = new JsonReader(new FileReader(Constants.PATH_CONFIG_FILE));
    } catch (FileNotFoundException e) {
      return;
    }
    JsonObject options = gson.fromJson(reader, JsonObject.class);
    
    AutotraderParser parser = new AutotraderParser();
    ArrayList<Vehicle> allVehicles = parser.parseWebsite(options);
    VehicleFilter filter = new VehicleFilter();
    
    ArrayList<Vehicle> allVehiclesFiltered = (ArrayList<Vehicle>) filter.filterVehicleList(allVehicles);

    UtilMethods.getInstance().createCarOutputJson(allVehiclesFiltered);
  }
}
