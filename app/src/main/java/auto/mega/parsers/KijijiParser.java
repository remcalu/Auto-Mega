package auto.mega.parsers;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element; 
import org.jsoup.select.Elements;

import auto.mega.models.ConfigOptions;
import auto.mega.models.Vehicle;

public class KijijiParser extends RequestWebsiteParser {

  public static final String WEBSITE = "Kijiji";
  public static final int REQUEST_DELAY_TIME_SHORT = 20;
  public static final int REQUEST_FAILURE_RETRY_WAIT = 120000;
  
  public ArrayList<Vehicle> parseWebsite(ConfigOptions searchOptions) throws InterruptedException {
    ArrayList<Vehicle> allVehicles = new ArrayList<>();

    /* Setting some vehicle attributes that are already known */
    String adDateScraped = getCurrentDateTime();

    /* Getting search configuration options from file */
    HashMap<String, Object> urlParamsMap = createUrlParamMap(searchOptions);

    /* Looping through all vehicles found in the config file */
    Set<String> allVehicleBrands = searchOptions.getVehicleBrandModels().keySet();
    for (String vehicleBrand : allVehicleBrands) {
    urlParamsMap.put("brand", vehicleBrand);

      ArrayList<String> allVehicleModelsForBrand = searchOptions.getVehicleBrandModels().get(vehicleBrand);
      for (String vehicleModel : allVehicleModelsForBrand) {
        System.out.println("KI --> Parsing vehicles of type: " + vehicleBrand + " " + vehicleModel);
        urlParamsMap.put("model", vehicleModel);

        /* Constructing URL from configuration paramaters */
        String currentURL = urlConstructor(urlParamsMap);

        /* Add artificial wait times to circumvent DOS protection */
        Document doc = extractWebsiteResponse(currentURL, "#MainContainer", REQUEST_DELAY_TIME_SHORT, REQUEST_FAILURE_RETRY_WAIT);
        if (doc == null || calcNumVehiclesFound(doc) == 0) {
          continue;
        }

        /* Looping through all containers that contain a vehicle */
        Element mainContainer = doc.select("main").first().firstElementChild();
        while (mainContainer.nextElementSibling() != null) {
          mainContainer = mainContainer.nextElementSibling();
        }

        Elements adHTMLContainers = mainContainer.children();
        adHTMLContainers.removeIf(e -> !e.hasAttr("data-listing-id") || !e.select(".kijiji-autos-logo").isEmpty());
        for (Element adHTMLContainer : adHTMLContainers) {
          allVehicles.add(createVehicle(adHTMLContainer, vehicleBrand, vehicleModel, adDateScraped));
        }
      }
    }
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
    String privateDealer = StringUtils.EMPTY;
    if (Boolean.FALSE.equals(includePrivateDealers)) {
      privateDealer = "&for-sale-by=delr";
    }
    
    String transmissionStr = postProcessString(transmission);
    model = model.replace(" ", "-");

    return "https://www.kijiji.ca/b-cars-trucks/kitchener-waterloo/vehicles/" +
      brand + "-" + 
      model + "-" +
      minYear + "__9999/k0c174l1700212a54a1000054a68a49?ll&transmission=2&address=" +
      postalCode + "&ad=offering&price=2000__" + 
      maxPrice + "&kilometers=1000__" + 
      maxMileage + "&radius=" + 
      distanceFromPostalCode +
      transmissionStr +
      privateDealer;
  }

  /** Creates a vehicle through various previously parsed data and new data that will be parsed from an HTML element
   * @param adHTMLContainer the HTML element that contains useful data for a vehicle ad
   * @param vehicleBrand    the brand of the vehicle that is being parsed
   * @param vehicleModel    the model of the vehicle that is being parsed
   * @param adDateScraped   the date at which the ad was retrieved
   * @return                the vehicle object populated by the HTML element
  */
  private Vehicle createVehicle(Element adHTMLContainer, String vehicleBrand, String vehicleModel, String adDateScraped) {
    
    /* Getting the link */
    Element adInfoContainer = adHTMLContainer.select(".info-container").first();
    String adLink = "https://www.kijiji.ca" + adInfoContainer.select(".title").attr("href");

    /* Getting the year */
    Pattern pattern = Pattern.compile("(\\d{4})");
    Matcher matcher = pattern.matcher(adInfoContainer.select(".title").text());
    Integer adYear = -1;
    if (matcher.find()) {
      adYear = Integer.parseInt(matcher.group());
    }

    /* Getting the brand */
    String adBrand = StringUtils.capitalize(vehicleBrand);

    /* Getting the model */
    String adModel = StringUtils.capitalize(vehicleModel);

    /* Getting the price */
    pattern = Pattern.compile("(\\d{1,2}\\,\\d{3})");
    matcher = pattern.matcher(adInfoContainer.select(".price").text());
    Integer adPrice = -1;
    if (matcher.find()) {
      adPrice = Integer.parseInt(matcher.group().replace(",", ""));
    }

    /* Getting the mileage */
    pattern = Pattern.compile("(\\d{1,3}\\,\\d{3})");
    matcher = pattern.matcher(adInfoContainer.select(".description .details").text());
    Integer adMileage = -1;
    if (matcher.find()) {
      adMileage = Integer.parseInt(matcher.group().replace(",", ""));
    }

    /* Getting the dealer type */
    Boolean adIsPrivateDealer = adInfoContainer.select(".price .dealer-logo").isEmpty();

    return new Vehicle.Builder()
      .withLink(adLink)
      .withBrand(adBrand)
      .withModel(adModel)
      .withPrice(adPrice)
      .withYear(adYear)
      .withMileage(adMileage)
      .withDateScraped(adDateScraped)
      .withIsPrivateDealer(adIsPrivateDealer)
      .withWebsite(WEBSITE)
      .build();
  }

  private int calcNumVehiclesFound(Document doc) { 
    return Integer.parseInt(doc.select(".titlecount").text()
      .replace("(", "")
      .replace(")", ""));
  }

  protected String postProcessString(String rawString) {
    String postProcessedString = StringUtils.EMPTY;
    if (rawString.equals("Automatic")) {
      postProcessedString = "&transmission=2";
    } else if (rawString.equals("Manual")) {
      postProcessedString = "&transmission=1";
    }
    return postProcessedString;
  }
}
