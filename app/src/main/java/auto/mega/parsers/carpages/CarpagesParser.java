package auto.mega.parsers.carpages;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    for (int i = 1; i < 10; i++) {
      logger.info("CP: Parsing vehicle page: {}/{}", i, 9);

      urlParamsMap.put("pageNum", i);
      String currentURL = urlConstructor(urlParamsMap);

      /* Add artificial wait times to circumvent DOS protection */
      Document doc = extractWebsiteResponse(currentURL, ".main-container", REQUEST_DELAY_TIME_SHORT, REQUEST_FAILURE_RETRY_WAIT);
      if (doc == null) {
        continue;
      }

      /* Looping through all containers that contain a vehicle */
      adHTMLContainers.addAll(doc.select(".media.soft.push-none"));
    }

    for (Element adHTMLContainer : adHTMLContainers) {
      allVehicles.add(createVehicle(adHTMLContainer, allVehicleBrands, allVehicleModels, adDateScraped, adInstantScraped));
    }

    logger.info("Done parsing Carpages");
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
    int distanceFromPostalCode = (int) params.get("distanceFromPostalCode");
    int pageNum = (int) params.get("pageNum");
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

    return "https://www.carpages.ca/used-cars/search/?odometer_amount_start=10000&odometer_amount_end=" + 
      maxMileage + "&year_start=" + 
      minYear + "&price_amount_start=1000&price_amount_end=" +
      maxPrice + "&make_name=" + 
      brandsString + "&model_name=" + 
      modelsString + 
      transmissionStr + "&search_radius=" + 
      distanceFromPostalCode + "&with_prices_only=1&ll=" + "43.415281000000000" + "," + "-80.473944000000000"  + "&p=" + 
      pageNum;
  }

  /** Creates a vehicle through various previously parsed data and new data that will be parsed from an HTML element
   * @param adHTMLContainer the HTML element that contains useful data for a vehicle ad
   * @param adDateScraped   the date at which the ad was retrieved
   * @return                the vehicle object populated by the HTML element
  */
  private Vehicle createVehicle(Element adHTMLContainer, ArrayList<String> allVehicleBrands, ArrayList<String> allVehicleModels, String adDateScraped, Long adInstantScraped) {
    
    /* Getting the link */
    Element adInfoContainer = adHTMLContainer.select(".l-column.l-column--large-8").first();
    String adLink = "https://www.carpages.ca/" + adInfoContainer.select("a").attr("href");

    /* Getting container for year, brand, and model */
    String adYearBrandModelInfoContainer = adInfoContainer.select("a").attr("title");

    /* Getting the year */
    Pattern pattern = Pattern.compile("(\\d{4})");
    Matcher matcher = pattern.matcher(adYearBrandModelInfoContainer);
    Integer adYear = -1;
    if (matcher.find()) {
      adYear = Integer.parseInt(matcher.group());
    }

    /* Getting the brand */
    StringBuilder brandsRegex = new StringBuilder();
    brandsRegex.append("\\b(");
    brandsRegex.append(String.join("|", allVehicleBrands.toArray(new CharSequence[0])));
    brandsRegex.append(")");

    pattern = Pattern.compile(brandsRegex.toString(), Pattern.CASE_INSENSITIVE);
    matcher = pattern.matcher(adYearBrandModelInfoContainer);
    String adBrand = "";
    if (matcher.find()) {
      adBrand = StringUtils.capitalize(matcher.group().toLowerCase());
    }

    /* Getting the model */
    StringBuilder modelsRegex = new StringBuilder();
    modelsRegex.append("\\b(");
    modelsRegex.append(String.join("|", allVehicleModels.toArray(new CharSequence[0])));
    modelsRegex.append(")");
    String modelsRegexString = modelsRegex.toString().replace("mazda 6", "mazda 6|mazda6").replace("mazda 3", "mazda 3|mazda3");
    
    pattern = Pattern.compile(modelsRegexString, Pattern.CASE_INSENSITIVE);
    matcher = pattern.matcher(adYearBrandModelInfoContainer);
    String adModel = null;
    if (matcher.find()) {
      adModel = StringUtils.capitalize(matcher.group().toLowerCase().replace(" ", ""));
    }

    /* Getting the price */
    pattern = Pattern.compile("(\\d{1,2}\\,\\d{3})");
    matcher = pattern.matcher(adInfoContainer.select(".delta").text());
    Integer adPrice = -1;
    if (matcher.find()) {
      adPrice = Integer.parseInt(matcher.group().replace(",", ""));
    }

    /* Getting the mileage */
    StringBuilder mileageBuilder = new StringBuilder();
    Elements mileagePieces = adInfoContainer.select(".l-row.soft-half--top").first().child(1).select(".number");
    for (Element mileagePiece : mileagePieces) {
      if (!",".equals(mileagePiece.text())) {
        mileageBuilder.append(mileagePiece.text());
      }
    }
    Integer adMileage = -1;
    if (!mileageBuilder.toString().isEmpty()) {
      adMileage = Integer.parseInt(mileageBuilder.toString());
    }

    /* Getting the dealer type */
    Boolean adIsPrivateDealer = false;

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
