package auto.mega.parsers;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import auto.mega.models.ConfigOptions;

public abstract class WebsiteParser {

  /** Constructs a URL for a specific website to narrow down search operations for a vehicle
   * @param params the map containing key value pairs of various paramaters for URL construction
   * @return       the constructed URL
  */
  protected abstract String urlConstructor(Map<String, Object> params);

  /** Constructs a map for parameters to be passed for creating URLs
   * @param searchOptions the map containing key value pairs of various paramaters for URL construction
   * @return              the map containing the parameters for searching
  */
  protected HashMap<String, Object> createUrlParamMap(ConfigOptions searchOptions) {
    HashMap<String, Object> urlParamMap = new HashMap<>();
      urlParamMap.put("minYear", searchOptions.getMinYear());
      urlParamMap.put("maxPrice", searchOptions.getMaxPrice());
      urlParamMap.put("maxMileage", searchOptions.getMaxMileage());
      urlParamMap.put("distanceFromPostalCode", searchOptions.getDistanceFromPostalCode());
      urlParamMap.put("postalCode", searchOptions.getPostalCode());
      urlParamMap.put("transmission", searchOptions.getTransmission());
      urlParamMap.put("includePrivateDealers", searchOptions.getIncludePrivateDealers());
    return urlParamMap;
  }

  /** Calculates when a longer period of time should be waited to circumvent DOS protection
   * @param numRequests           the total number of requests that have been made
   * @param requestsUntilLongWait the number of short delays until a long delay should happen
   * @param waitTime              the generic delay that usually occurs
   * @param longWaitTime          the longer delay that periodically occurs
   * @return                      the amount of time in miliseconds to wait
  */
  protected int calculateWaitTime(int numRequests, int requestsUntilLongWait, int waitTime, int longWaitTime) {
    if (numRequests % requestsUntilLongWait == 0) {
      waitTime = longWaitTime;
    }
    return waitTime;
  }

  /** Gets the current date and time as a string
   * @return the string of the current date and time
  */
  protected String getCurrentDateTime() {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm")
      .format(new Date(System.currentTimeMillis()));
  }

  /** Gets the current time as epoch time 
   * @return the long of the current time as epoch time
  */
  protected Long getCurrentInstant() {
    return Instant.now().toEpochMilli();
  }

  /** Checks if an object is an ArrayList<String>
   * @param object the object that will be checked
   * @return       the ArrayList<String> that has been checked
  */
  protected ArrayList<String> checkArrayListWithString(Object object) {
    ArrayList<String> checkedObjects = new ArrayList<>();
    ArrayList<?> uncheckedObjects;
    uncheckedObjects = (ArrayList<?>) object;
    for (Object x : uncheckedObjects) {
      checkedObjects.add((String) x);
    }
    return checkedObjects;
  }
}
