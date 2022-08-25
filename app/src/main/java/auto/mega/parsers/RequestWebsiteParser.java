package auto.mega.parsers;

import auto.mega.models.ConfigOptions;
import auto.mega.models.Vehicle;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class RequestWebsiteParser extends WebsiteParser {
  public static final int REQUEST_TIMEOUT_TIME_MS = 5000;
  private static final String HEADER_USER_AGENT_KEY = "user-agent";
  private static final String HEADER_USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36";
  
  /** Calculates when a longer period of time should be waited to circumvent DOS protection
   * @param numRequests           the total number of requests that have been made
   * @param requestsUntilLongWait the number of short delays until a long delay should happen
   * @param waitTime              the generic delay that usually occurs
   * @param longWaitTime          the longer delay that periodically occurs
   * @return                      the amount of time in miliseconds to wait
  */
  public boolean pingWebsite(String url, String elementToCheck, String proxyIp, Integer proxyPort) {
    Document doc = getWebsiteResponse(url, proxyIp, proxyPort);
    if (doc == null) {
      return false;
    }

    return !doc.select(elementToCheck).isEmpty();
  }
  
  /** Parse a website for vehicles
   * @param searchOptions the JSON object that contains options found in a config file for website parsing
   * @return              the list of vehicles
  */
  public abstract List<Vehicle> parseWebsite(ConfigOptions searchOptions) throws InterruptedException;

  /** Performs an HTTP request through Jsoup to the website, and returns the response
   * @param url            the URL that will have an HTTP request performed on it
   * @param elementToCheck the element to check for to determinate that a request was successful, use CSS selector
   * @param waitTime       the generic delay that usually occurs
   * @param longWaitTime   the longer delay that periodically occurs
   * @return               the HTTP response in the response in the form of an HTML document
  */
  protected Document extractWebsiteResponse(String url, String elementToCheck, int waitTime, int longWaitTime) {
    boolean longSleepAttempted = false;
    int passedWaitTime = waitTime;

    /* Send an HTTP request to the URL, a second attempt will be made in 30s if the website DOS protection kicks in */
    while (!longSleepAttempted) {
      try {
        /* Toggles if the first request failed */
        if (waitTime != passedWaitTime) {
          longSleepAttempted = true;
        }

        /* Wait a bit before sending a request, then send it */
        Thread.sleep(waitTime);
        Document doc = getWebsiteResponse(url, null, null);
        if (doc == null) {
          return null;
        }

        /* Check if the website DOS protection kicks in, if it does then wait, and send a request again */
        boolean isElementPresent = !doc.select(elementToCheck).isEmpty();
        if (!isElementPresent) {
          waitTime = longWaitTime;
        } else {
          return doc;
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    return null;
  }

  /** Gets the website response in the form of a Document 
   * @param url       the URL that will have an HTTP request performed on it
   * @param proxyIp   the proxy ip that will be used to make the request
   * @param proxyPort the proxy port that will be used to make the request
   * @return          the HTTP response in the response in the form of an HTML document
  */
  private Document getWebsiteResponse(String url, String proxyIp, Integer proxyPort) {
    try {
      if (proxyIp != null && proxyPort != null) {
        return Jsoup.connect(url).
          proxy(proxyIp, proxyPort).
          timeout(REQUEST_TIMEOUT_TIME_MS).
          header(HEADER_USER_AGENT_KEY, HEADER_USER_AGENT_VALUE).
          execute().
          bufferUp().
          parse();
      } else {
        return Jsoup.connect(url).
          timeout(REQUEST_TIMEOUT_TIME_MS).
          header(HEADER_USER_AGENT_KEY, HEADER_USER_AGENT_VALUE).
          execute().
          bufferUp().
          parse();
      }
    } catch (IOException e) {
      return null;
    }
  }
}
