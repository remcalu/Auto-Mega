package auto.mega.parsers;

import auto.mega.models.Vehicle;
import com.google.gson.JsonObject;

import java.util.List;

public interface WebsiteParser {
  
  /** Parse a website for vehicles
   * @param options the JSON object that contains options found in a config file for website parsing
   * @return        the list of vehicles
  */
  public List<Vehicle> parseWebsite(JsonObject options) throws InterruptedException;

}
