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
import auto.mega.parsers.RequestWebsiteParser;

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
        try {
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
          int numVehiclesFound = Integer.parseInt(doc.select("#sbCount").text().replace(",", ""));
          if (numVehiclesFound == 0) {
            continue;
          }


          /* Looping through all containers that contain a vehicle */
          Elements adHTMLContainers = doc.select(".result-item");
          adHTMLContainers.stream()
            .forEach(adHTMLContainer -> {
              Vehicle newVehicle = createVehicle(adHTMLContainer, vehicleBrand, vehicleModel, adDateScraped, adInstantScraped);
              if (newVehicle != null) {
                allVehicles.add(newVehicle);
              }
            });
        } catch(Exception e) {
          logger.info("AT: Error parsing page", e);
        }
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
    Integer distance = (Integer) params.get("distance");
    String postalCode = (String) params.get("postalCode");
    String transmission = (String) params.get("transmission");
    Boolean includePrivateDealers = (Boolean) params.get("includePrivateDealers");

    /* Add extra parsing for mazdas */
    if (model.contains("mazda")) {
      model = model.replace(" ", "");
    } else {
      model = model.replace(" ", "%20");
    }
  
    /* Convert transmission to a form usable in the URL */
    if (transmission.equals("Automatic")) {
      transmission = "&transmission=2";
    } else if (transmission.equals("Manual")) {
      transmission = "&transmission=1";
    }

    /* Getting link info for if private dealers should be included */
    String privateDealer = "";
    if (Boolean.FALSE.equals(includePrivateDealers)) {
      privateDealer = "&adtype=Dealer";
    }

    return "https://www.autotrader.ca/cars/" + 
      brand + "/" +  
      model + "/?rcp=1000&rcs=0&srt=4&yRng=" + 
      minYear + "%2C&pRng=%2C" + 
      maxPrice + "&oRng=%2C" + 
      maxMileage + "&prx=" + 
      distance + "&loc=" + 
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
    try {

      /* Getting the HTML Element that holds most of the required information */
      Element adInfoContainer = adHTMLContainer.select(".result-title").first();
      
      /* Getting the link */
      String adLink = AutotraderHelper.extractLinkFromAdContainer(adInfoContainer);
      
      /* Getting the brand */
      String adBrand = StringUtils.capitalize(vehicleBrand);

      /* Getting the model */
      String adModel = StringUtils.capitalize(vehicleModel);

      /* Getting the price */
      Integer adPrice = AutotraderHelper.extractPriceFromAdContainer(adHTMLContainer);

      /* Getting the year */
      Integer adYear = AutotraderHelper.extractYearFromAdContainer(adInfoContainer);
      
      /* Getting the mileage */
      Integer adMileage = AutotraderHelper.extractMileageFromAdContainer(adHTMLContainer);

      /* Getting the dealer type */
      Boolean adIsPrivateDealer = AutotraderHelper.extractDealerFromAdContainer(adHTMLContainer);
      
      /* Getting the vehicle image */
      String adImageLink = getImageUrlFromModel(adModel);
      
      return new Vehicle.Builder()
        .withTransmission("Automatic")
        .withLink(adLink)
        .withImageLink(adImageLink)
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
    } catch (Exception e) {
      logger.error("AT: Failed to create a vehicle", e);
      return null;
    }
  }
}