package auto.mega.parsers.carpages;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import auto.mega.models.ConfigOptions;
import auto.mega.models.Vehicle;
import auto.mega.parsers.RequestWebsiteParser;
import auto.mega.utils.JsonHelper;

@Component
public class CarpagesParser extends RequestWebsiteParser {

  private static final Logger logger = LogManager.getLogger(CarpagesParser.class);

  public static final String WEBSITE = "Carpages";
  public static final int REQUESTS_UNTIL_LONG_WAIT = 3;
  public static final int REQUEST_DELAY_TIME_SHORT = 20;
  public static final int REQUEST_DELAY_TIME_LONG = 2000;
  public static final int REQUEST_FAILURE_RETRY_WAIT = 60000;
  
  public ArrayList<Vehicle> parseWebsite(ConfigOptions searchOptions) throws InterruptedException {
    ArrayList<Vehicle> allVehicles = new ArrayList<>();

    /* Setting some vehicle attributes that are already known */
    String adDateScraped = getCurrentDateTime();
    Long adInstantScraped = getCurrentInstant();

    /* Getting search configuration options from file */
    HashMap<String, Object> urlParamsMap = createUrlParamMap(searchOptions);

    /* Looping through all vehicles found in the config file */
    ArrayList<String> allVehicleBrands = new ArrayList<>(searchOptions.getVehicleBrandModels().keySet());
    ArrayList<String> allVehicleModels = new ArrayList<>();
    for (String vehicleBrand : allVehicleBrands) {
      allVehicleModels.addAll(searchOptions.getVehicleBrandModels().get(vehicleBrand));
    }
    urlParamsMap.put("brands", allVehicleBrands);
    urlParamsMap.put("models", allVehicleModels);

    Elements adHTMLContainers = new Elements();
    for (int i = 1; i < 50; i++) {
      logger.info("CP: Parsing vehicle page: {}", i);

      urlParamsMap.put("pageNum", i);
      String currentURL = urlConstructor(urlParamsMap);

      /* Add artificial wait times to circumvent DOS protection */
      Document doc = extractWebsiteResponse(currentURL, ".main-container", REQUEST_DELAY_TIME_SHORT, REQUEST_FAILURE_RETRY_WAIT);
      if (doc == null) {
        continue;
      }
      adHTMLContainers.addAll(doc.select(".media.soft.push-none"));

      /* Exit when no further pages */
      if (doc.select("a[title='Next Page']").isEmpty()) {
        break;
      }
    }

    /* Looping through all containers that contain a vehicle */
    adHTMLContainers.stream()
      .forEach(adHTMLContainer -> {
        Vehicle newVehicle = createVehicle(adHTMLContainer, allVehicleBrands, allVehicleModels, adDateScraped, adInstantScraped);
        if (newVehicle != null) {
          allVehicles.add(newVehicle);
        }
      });

    logger.info("CP: Done parsing Carpages");
    return allVehicles;
  }

  /** Constructs a URL for a specific website to narrow down search operations for a vehicle
   * @param params the map containing key value pairs of various paramaters for URL construction
   * @return       the constructed URL
  */
  protected String urlConstructor(Map<String, Object> params) {
    ArrayList<String> brands = checkArrayListWithString(params.get("brands"));
    ArrayList<String> models = checkArrayListWithString(params.get("models"));
    int minYear = (int) params.get("minYear");
    int maxPrice = (int) params.get("maxPrice");
    int maxMileage = (int) params.get("maxMileage");
    int distance = (int) params.get("distance");
    int pageNum = (int) params.get("pageNum");
    String postalCode = (String) params.get("postalCode");
    String transmission = (String) params.get("transmission");

    /* Some cleaning up and post processing */
    StringBuilder brandsString = new StringBuilder();
    for (int i = 0; i < brands.size(); i++) {
      String brand = brands.get(i);
      brandsString.append(brand + "%7c");
    }

    StringBuilder modelsString = new StringBuilder();
    for (int i = 0; i < models.size(); i++) {
      String model = models.get(i);
      if (model.contains("mazda")) {
        model = model.replace(" ", "");
      }
      
      if (model.contains("series")) {
        model = model.replace(" ", "-");
        modelsString.insert(0, model + "%7c");
      } else {
        modelsString.append(model + "%7c");
      }
    }

    String transmissionStr = StringUtils.EMPTY;
    if (transmission.equals("Automatic")) {
      transmissionStr = "&transmissiontype_id=1";
    } else if (transmission.equals("Manual")) {
      transmissionStr = "&transmissiontype_id=2";
    }

    Pair<Double, Double> postalCodeCoords = JsonHelper.getCoordsFromPostal(postalCode);
    double lattitude = postalCodeCoords.getLeft();
    double longitude = postalCodeCoords.getRight();

    return "https://www.carpages.ca/used-cars/search/?odometer_amount_start=10000&odometer_amount_end=" + 
      maxMileage + "&year_start=" + 
      minYear + "&price_amount_start=1000&price_amount_end=" +
      maxPrice + "&make_name=" + 
      brandsString + "&model_name=" + 
      modelsString + 
      transmissionStr + "&search_radius=" + 
      distance + "&with_prices_only=1&ll=" + 
      lattitude + "," + 
      longitude + "&p=" + 
      pageNum;
  }

  /** Creates a vehicle through various previously parsed data and new data that will be parsed from an HTML element
   * @param adHTMLContainer the HTML element that contains useful data for a vehicle ad
   * @param adDateScraped   the date at which the ad was retrieved
   * @return                the vehicle object populated by the HTML element
  */
  private Vehicle createVehicle(Element adHTMLContainer, ArrayList<String> allVehicleBrands, ArrayList<String> allVehicleModels, String adDateScraped, Long adInstantScraped) {
    try {
      /* Getting the HTML Element that holds most of the required information */
      Element adInfoContainer = adHTMLContainer.select(".l-column.l-column--large-8").first();
      
      /* Getting the link */
      String adLink = CarpagesHelper.extractLinkFromAdContainer(adInfoContainer);

      /* Getting the year */
      Integer adYear = CarpagesHelper.extractYearFromAdContainer(adInfoContainer);

      /* Getting the brand */
      String adBrand = CarpagesHelper.extractBrandFromAdContainer(adInfoContainer, allVehicleBrands);

      /* Getting the model */
      String adModel = CarpagesHelper.extractModelFromAdContainer(adInfoContainer, allVehicleModels);

      /* Getting the price */
      Integer adPrice = CarpagesHelper.extractPriceFromAdContainer(adInfoContainer);

      /* Getting the mileage */
      Integer adMileage = CarpagesHelper.extractMileageFromAdContainer(adInfoContainer);

      /* Getting the dealer type */
      Boolean adIsPrivateDealer = false;

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
