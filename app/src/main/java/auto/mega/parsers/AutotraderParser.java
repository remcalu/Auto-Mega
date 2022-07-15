package auto.mega.parsers;

import java.util.Map;
import java.util.Set;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import auto.mega.models.ConfigOptions;
import auto.mega.models.Vehicle;
import auto.mega.utils.UtilMethods;

import com.google.gson.JsonObject;

public class AutotraderParser implements WebsiteParser {

  public static final String WEBSITE = "Autotrader";
  public static final int REQUEST_DELAY_TIME_SHORT = 20;
  public static final int REQUEST_DELAY_TIME_LONG = 8000;

  public ArrayList<Vehicle> parseWebsite(JsonObject options) throws InterruptedException {
    ArrayList<Vehicle> allVehicles = new ArrayList<>();
    int numRequests = 0;

    /* Setting some vehicle attributes that are already known */
    String adDateScraped = new SimpleDateFormat("yyyy-MM-dd HH:mm").
      format(new Date(System.currentTimeMillis()));

    /* Getting search configuration options from file */
    ConfigOptions searchOptions = UtilMethods.getInstance().parseConfigFile();
    HashMap<String, Object> urlParamsMap = new HashMap<>();
    urlParamsMap.put("minYear", searchOptions.getMinYear());
    urlParamsMap.put("maxPrice", searchOptions.getMaxPrice());
    urlParamsMap.put("maxMileage", searchOptions.getMaxMileage());
    urlParamsMap.put("distanceFromPostalCode", searchOptions.getDistanceFromPostalCode());
    urlParamsMap.put("postalCode", searchOptions.getPostalCode());
    urlParamsMap.put("transmission", searchOptions.getTransmission());
    urlParamsMap.put("includePrivateDealers", searchOptions.getIncludePrivateDealers());

    /* Looping through all vehicles found in the config file */
    Set<String> allVehicleBrands = searchOptions.getVehicleBrandModels().keySet();
    for (String vehicleBrand : allVehicleBrands) {

      ArrayList<String> allVehicleModelsForBrand = searchOptions.getVehicleBrandModels().get(vehicleBrand);
      for (String vehicleModel : allVehicleModelsForBrand) {
        System.out.println("Parsing vehicles of type: " + vehicleBrand + " " + vehicleModel);
        urlParamsMap.put("brand", vehicleBrand);
        urlParamsMap.put("model", vehicleModel);

        /* Constructing URL from configuration paramaters */
        String currentURL = urlConstructor(urlParamsMap);

        /* Getting URL response body */
        System.out.println(currentURL + "\n");

        /* Add artificial wait times to circumvent DOS protection */
        int waitTime = calculateWaitTime(++numRequests);
        Document doc = extractWebsiteResponse(currentURL, waitTime);
        if(doc == null) {
          continue;
        }

        /* Performing no further parsing if no vehicles are found at that URL */
        int numVehiclesFound = Integer.parseInt(doc.select("#sbCount").text());
        if(numVehiclesFound == 0) {
          continue;
        }

        /* Looping through all containers that contain a vehicle */
        Elements adHTMLContainers = doc.select(".result-item");
        for (Element adHTMLContainer : adHTMLContainers) {
          allVehicles.add(createVehicle(adHTMLContainer, vehicleBrand, vehicleModel, adDateScraped));
        }
      }
    }
    return(allVehicles);
  }

  /** Constructs a URL for a specific website to narrow down search operations for a vehicle
   * @param params the map containing key value pairs of various paramaters for URL construction
   * @return       the constructed URL
  */
  private String urlConstructor(Map<String, Object> params) {
    String brand = (String) params.get("brand");
    String model = (String) params.get("model");
    int minYear = (int) params.get("minYear");
    int maxPrice = (int) params.get("maxPrice");
    int maxMileage = (int) params.get("maxMileage");
    int distanceFromPostalCode = (int) params.get("distanceFromPostalCode");
    String postalCode = (String) params.get("postalCode");
    String transmission = (String) params.get("transmission");
    boolean includePrivateDealers = (boolean) params.get("includePrivateDealers");

    /* Some cleaning up and post processing */
    String privateDealer = StringUtils.EMPTY;
    if (!includePrivateDealers) {
      privateDealer = "adtype=Dealer";
    }
    model = model.replace(" ", "%20");

    return("https://www.autotrader.ca/cars/" + 
      brand + "/" +  
      model + "/?rcp=1000&rcs=0&srt=4&yRng=" + 
      minYear + "%2C&pRng=%2C" + 
      maxPrice + "&oRng=%2C" + 
      maxMileage + "&prx=" + 
      distanceFromPostalCode + "&loc=" + 
      postalCode + "&trans=" + 
      transmission + "%2COther%2FDon%27t%20Know&hprc=True&wcp=True&sts=New-Used&" + 
      privateDealer + "&inMarket=advancedSearch");
  }

  /** Performs an HTTP request through Jsoup to the website, and returns the response
   * @param currentURL the URL that will have an HTTP request performed on it
   * @return           the HTTP response
  */
  private Document extractWebsiteResponse(String currentURL, int waitTime) {
    boolean longSleepAttempted = false;
    int passedWaitTime = waitTime;

    /* Send an HTTP request to the URL, a second attempt will be made in 30s if the website DOS protection kicks in */
    while(!longSleepAttempted) {
      try {
        /* Toggles if the first request failed */
        if (waitTime != passedWaitTime) {
          longSleepAttempted = true;
        }

        /* Wait a bit before sending a request, then send it */
        Thread.sleep(waitTime);
        Document doc = Jsoup.connect(currentURL).
          timeout(3000).
          header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36").
          get();

        /* Check if the website DOS protection kicks in, if it does then wait, and send a request again */
        boolean isBodyIdPresent = !doc.body().attr("id").isEmpty();
        if (!isBodyIdPresent) {
          System.out.println("WAITING");
          waitTime = 40000;
        } else {
          System.out.println("GOOD");
          return(doc);
        }
      } catch (IOException e) {} 
      catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    System.out.println("BAD");
    return null;
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
    Element adInfoContainer = adHTMLContainer.select(".result-title").first();
    String adLink = "https://www.autotrader.ca" + adInfoContainer.attr("href");

    /* Getting the brand */
    String adBrand = StringUtils.capitalize(vehicleBrand);

    /* Getting the model */
    String adModel = StringUtils.capitalize(vehicleModel);

    /* Getting the price */
    Integer adPrice = Integer.parseInt(adHTMLContainer.select(".price-amount").first().text().
      replace(",", "").
      replace("$", ""));

    /* Getting the year */
    Integer adYear = Integer.parseInt(adInfoContainer.text().
      split(" ")[0]);

    /* Getting the mileage */
    Integer adMileage = 0;
    if(adHTMLContainer.select(".kms").text().split(" ").length == 3) {
      adMileage = Integer.parseInt(adHTMLContainer.select(".kms").text().
        split(" ")[1].
        replace(",", ""));
    }

    /* Getting the dealer type */
    Boolean adIsPrivateDealer = false;
    if (!adHTMLContainer.select(".private-car-en").isEmpty() || !adHTMLContainer.select(".svg_privateBadge").isEmpty()) {
      adIsPrivateDealer = true;
    }

    return new Vehicle.Builder().
      withLink(adLink).
      withBrand(adBrand).
      withModel(adModel).
      withPrice(adPrice).
      withYear(adYear).
      withMileage(adMileage).
      withDateScraped(adDateScraped).
      withIsPrivateDealer(adIsPrivateDealer).
      withWebsite(WEBSITE).
      build();
  }

  /** Calculates when a longer period of time should be waited to circumvent DOS protection
   * @param numRequests the total number of requests that have been made
   * @return            the amount of time in miliseconds to wait
  */
  private int calculateWaitTime(int numRequests) {
    int waitTime = REQUEST_DELAY_TIME_SHORT;
    if (numRequests % 3 == 0) {
      waitTime = REQUEST_DELAY_TIME_LONG;
    }
    return(waitTime);
  }
}
