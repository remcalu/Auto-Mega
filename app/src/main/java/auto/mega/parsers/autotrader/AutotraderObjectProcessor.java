package auto.mega.parsers.autotrader;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

public class AutotraderObjectProcessor {

  /** Enum used for identiying which string to post processes */
  public enum Identifier {
    AT_STR_URL_PRIV_DEALERS,
    AT_STR_URL_TRANSMISSION,
    AT_STR_URL_MAZDA,
    AT_STR_AD_PRICE,
    AT_STR_AD_YEAR,
    AT_STR_AD_MILEAGE,
    AT_BOOL_AD_PRIV_DEALER;
  }

  /** Processes an object so that it can be processed to a required string
   * @param unprocessedObject the object that will be processed
   * @return                  the processed string
  */
  public static String processToString(Identifier identifier, Object unprocessedObject) {
    
    if (identifier.equals(Identifier.AT_STR_URL_PRIV_DEALERS)) {
      Boolean includePrivateDealers = (boolean) unprocessedObject;
      if (Boolean.FALSE.equals(includePrivateDealers)) {
        return "&adtype=Dealer";
      }

    } else if (identifier.equals(Identifier.AT_STR_URL_TRANSMISSION)) {
      String transmission = (String) unprocessedObject;
      String transmissionStr = StringUtils.EMPTY;
      if (transmission.equals("Automatic")) {
        transmissionStr = "&transmission=2";
      } else if (transmission.equals("Manual")) {
        transmissionStr = "&transmission=1";
      }
      return transmissionStr;

    } else if (identifier.equals(Identifier.AT_STR_URL_MAZDA)) {
      String model = (String) unprocessedObject;
      if (model.contains("mazda")) {
        model = model.replace(" ", "");
      } else {
        model = model.replace(" ", "%20");
      }
      return model;

    }

    return "";
  }

  public static boolean processToBoolean(Identifier identifier, Object unprocessedObject) {
    if (identifier.equals(Identifier.AT_BOOL_AD_PRIV_DEALER)) {
      Element adHTMLContainer = (Element) unprocessedObject;
      if (!adHTMLContainer.select(".private-car-en").isEmpty() || !adHTMLContainer.select(".svg_privateBadge").isEmpty()) {
        return true;
      }

    }
    return false;
  }

  public static int processToInt(Identifier identifier, Object unprocessedObject) {
    if (identifier.equals(Identifier.AT_STR_AD_PRICE)) {
      String adHtmlStr = (String) unprocessedObject;
      return Integer.parseInt(adHtmlStr.replace(",", "").replace("$", ""));
    
    } else if (identifier.equals(Identifier.AT_STR_AD_YEAR)) {
      String adHtmlStr = (String) unprocessedObject;
      return Integer.parseInt(adHtmlStr.split(" ")[0]);
    
    } else if (identifier.equals(Identifier.AT_STR_AD_MILEAGE)) {
      String adHtmlStr = (String) unprocessedObject;
      String mileageStr = "0";
      if (adHtmlStr.split(" ").length == 3) {
        mileageStr = adHtmlStr
          .split(" ")[1]
          .replace(",", "");
      }
      return Integer.parseInt(mileageStr);

    }
    
    return 0;
  }
}
