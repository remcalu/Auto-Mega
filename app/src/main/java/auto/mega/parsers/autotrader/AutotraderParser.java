package auto.mega.parsers.autotrader;

import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import auto.mega.models.ConfigOptions;
import auto.mega.models.Vehicle;
import auto.mega.parsers.ObjectProcessor;
import auto.mega.parsers.RequestWebsiteParser;
import auto.mega.parsers.ObjectProcessor.Identifier;

@Component
public class AutotraderParser extends RequestWebsiteParser {

  private static final Logger logger = LogManager.getLogger(AutotraderParser.class);

  public static final String WEBSITE = "Autotrader";
  public static final int REQUESTS_UNTIL_LONG_WAIT = 3;
  public static final int REQUEST_DELAY_TIME_SHORT = 20;
  public static final int REQUEST_DELAY_TIME_LONG = 8000;
  public static final int REQUEST_FAILURE_RETRY_WAIT = 40000;

  public ArrayList<Vehicle> parseWebsite(ConfigOptions searchOptions) throws InterruptedException {
    ArrayList<Vehicle> allVehicles = new ArrayList<>();
    int numRequests = 0;

    /* Setting some vehicle attributes that are already known */
    String adDateScraped = getCurrentDateTime();
    Long adInstantScraped = getCurrentInstant();

    /* Getting search configuration options from file */
    HashMap<String, Object> urlParamsMap = createUrlParamMap(searchOptions);

    /* Looping through all vehicles found in the config file */
    Set<String> allVehicleBrands = searchOptions.getVehicleBrandModels().keySet();
    for (String vehicleBrand : allVehicleBrands) {
      urlParamsMap.put("brand", vehicleBrand);
      
      ArrayList<String> allVehicleModelsForBrand = searchOptions.getVehicleBrandModels().get(vehicleBrand);
      for (String vehicleModel : allVehicleModelsForBrand) {
        logger.info("AT: Parsing vehicles of type: {} {}", vehicleBrand, vehicleModel);

        urlParamsMap.put("model", vehicleModel);

        /* Constructing URL from configuration paramaters */
        String currentURL = urlConstructor(urlParamsMap);

        /* Add artificial wait times to circumvent DOS protection */
        int waitTime = calculateWaitTime(++numRequests, REQUESTS_UNTIL_LONG_WAIT, REQUEST_DELAY_TIME_SHORT, REQUEST_DELAY_TIME_LONG);
        Document doc = extractWebsiteResponse(currentURL, "#en-ca", waitTime, REQUEST_FAILURE_RETRY_WAIT);
        if (doc == null) {
          continue;
        }

        /* Performing no further parsing if no vehicles are found at that URL */
        int numVehiclesFound = Integer.parseInt(doc.select("#sbCount").text());
        if (numVehiclesFound == 0) {
          continue;
        }

        /* Looping through all containers that contain a vehicle */
        Elements adHTMLContainers = doc.select(".result-item");
        adHTMLContainers.stream()
          .forEach(adHTMLContainer -> allVehicles.add(createVehicle(adHTMLContainer, vehicleBrand, vehicleModel, adDateScraped, adInstantScraped)));
      }
    }

    logger.info("AT: Done parsing Autotrader");
    return allVehicles;
  }

  /** Constructs a URL for a specific website to narrow down search operations for a vehicle
   * @param params the map containing key value pairs of various paramaters for URL construction
   * @return       the constructed URL
  */
  protected String urlConstructor(Map<String, Object> params) {
    String brand = (String) params.get("brand");
    String model = (String) params.get("model");
    Integer minYear = (Integer) params.get("minYear");
    Integer maxPrice = (Integer) params.get("maxPrice");
    Integer maxMileage = (Integer) params.get("maxMileage");
    Integer distanceFromPostalCode = (Integer) params.get("distanceFromPostalCode");
    String postalCode = (String) params.get("postalCode");
    String transmission = (String) params.get("transmission");
    Boolean includePrivateDealers = (Boolean) params.get("includePrivateDealers");

    /* Some cleaning up and post processing */
    model = ObjectProcessor.processToString(Identifier.AT_STR_URL_MAZDA,  model);
    transmission = ObjectProcessor.processToString(Identifier.AT_STR_URL_TRANSMISSION, transmission);
    String privateDealer = ObjectProcessor.processToString(Identifier.AT_STR_URL_PRIV_DEALERS, includePrivateDealers);    

    return "https://www.autotrader.ca/cars/" + 
      brand + "/" +  
      model + "/?rcp=1000&rcs=0&srt=4&yRng=" + 
      minYear + "%2C&pRng=%2C" + 
      maxPrice + "&oRng=%2C" + 
      maxMileage + "&prx=" + 
      distanceFromPostalCode + "&loc=" + 
      postalCode + "&trans=" + 
      transmission + "%2COther%2FDon%27t%20Know&hprc=True&wcp=True&sts=New-Used" + 
      privateDealer + "&inMarket=advancedSearch";
  }

  /** Creates a vehicle through various previously parsed data and new data that will be parsed from an HTML element
   * @param adHTMLContainer the HTML element that contains useful data for a vehicle ad
   * @param vehicleBrand    the brand of the vehicle that is being parsed
   * @param vehicleModel    the model of the vehicle that is being parsed
   * @param adDateScraped   the date at which the ad was retrieved
   * @return                the vehicle object populated by the HTML element
  */
  private Vehicle createVehicle(Element adHTMLContainer, String vehicleBrand, String vehicleModel, String adDateScraped, Long adInstantScraped) {
    
    /* Getting the link */
    Element adInfoContainer = adHTMLContainer.select(".result-title").first();
    String adLink = "https://www.autotrader.ca" + adInfoContainer.attr("href");

    /* Getting the brand */
    String adBrand = StringUtils.capitalize(vehicleBrand);

    /* Getting the model */
    String adModel = StringUtils.capitalize(vehicleModel);

    /* Getting the price */
    Integer adPrice = ObjectProcessor.processToInt(Identifier.AT_STR_AD_PRICE, adHTMLContainer.select(".price-amount").first().text());

    /* Getting the year */
    Integer adYear = ObjectProcessor.processToInt(Identifier.AT_STR_AD_YEAR, adInfoContainer.text());
    
    /* Getting the mileage */
    Integer adMileage = ObjectProcessor.processToInt(Identifier.AT_STR_AD_MILEAGE, adHTMLContainer.select(".kms").text());

    /* Getting the dealer type */
    Boolean adIsPrivateDealer = ObjectProcessor.processToBoolean(Identifier.AT_STR_AD_MILEAGE, adHTMLContainer);

    return new Vehicle.Builder()
      .withLink(adLink)
      .withBrand(adBrand)
      .withModel(adModel)
      .withPrice(adPrice)
      .withYear(adYear)
      .withMileage(adMileage)
      .withDateScraped(adDateScraped)
      .withInstantScraped(adInstantScraped)
      .withIsPrivateDealer(adIsPrivateDealer)
      .withWebsite(WEBSITE)
      .build();
  }
}