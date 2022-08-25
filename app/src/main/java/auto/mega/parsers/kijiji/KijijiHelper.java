package auto.mega.parsers.kijiji;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

public class KijijiHelper {

  KijijiHelper() {}

  public static String extractLinkFromAdContainer(Element adInfoContainer) {
    return "https://www.kijiji.ca" + adInfoContainer.select(".title").attr("href");
  }

  public static Integer extractYearFromAdContainer(Element adInfoContainer) {
    Pattern pattern = Pattern.compile("(\\d{4})");
    Matcher matcher = pattern.matcher(adInfoContainer.select(".title").text());
    Integer adYear = -1;
    if (matcher.find()) {
      adYear = Integer.parseInt(matcher.group());
    }
    return adYear;
  }

  public static Integer extractPriceFromAdContainer(Element adInfoContainer) {
    Pattern pattern = Pattern.compile("(\\d{1,2}\\,\\d{3})");
    Matcher matcher = pattern.matcher(adInfoContainer.select(".price").text());
    Integer adPrice = -1;
    if (matcher.find()) {
      adPrice = Integer.parseInt(matcher.group().replace(",", ""));
    }
    return adPrice;
  }

  public static Integer extractMileageFromAdContainer(Element adInfoContainer) {
    Pattern pattern = Pattern.compile("(\\d{1,3}\\,\\d{3})");
    Matcher matcher = pattern.matcher(adInfoContainer.select(".description .details").text());
    Integer adMileage = -1;
    if (matcher.find()) {
      adMileage = Integer.parseInt(matcher.group().replace(",", ""));
    }
    return adMileage;
  }
}
