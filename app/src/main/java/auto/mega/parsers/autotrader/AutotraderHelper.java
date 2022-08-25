package auto.mega.parsers.autotrader;

import org.jsoup.nodes.Element;

public class AutotraderHelper {

  AutotraderHelper() {}
  
  public static String extractLinkFromAdContainer(Element adInfoContainer) {
    return "https://www.autotrader.ca" + adInfoContainer.attr("href");
  }

  public static Integer extractYearFromAdContainer(Element adInfoContainer) {
    String adHtmlStr = adInfoContainer.text();
    return Integer.parseInt(adHtmlStr.split(" ")[0]);
  }

  public static Integer extractPriceFromAdContainer(Element adHTMLContainer) {
    String adHtmlStr = adHTMLContainer.select(".price-amount").first().text();
    return Integer.parseInt(adHtmlStr.replace(",", "").replace("$", ""));
  }

  public static Integer extractMileageFromAdContainer(Element adHTMLContainer) {
    String adHtmlStr = adHTMLContainer.select(".kms").text();
    String mileageStr = "0";
    if (adHtmlStr.split(" ").length == 3) {
      mileageStr = adHtmlStr
        .split(" ")[1]
        .replace(",", "");
    }
    return Integer.parseInt(mileageStr);
  }

  public static Boolean extractDealerFromAdContainer(Element adHTMLContainer) {
    if (!adHTMLContainer.select(".private-car-en").isEmpty() || !adHTMLContainer.select(".svg_privateBadge").isEmpty()) {
      return true;
    }
    return false;
  }
}
