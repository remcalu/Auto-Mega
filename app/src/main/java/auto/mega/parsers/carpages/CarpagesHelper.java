package auto.mega.parsers.carpages;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CarpagesHelper {
  
  CarpagesHelper() {}

  public static String extractLinkFromAdContainer(Element adInfoContainer) {
    return "https://www.carpages.ca/" + adInfoContainer.select("a").attr("href");
  }

  public static Integer extractYearFromAdContainer(Element adInfoContainer) {
    String adYearBrandModelInfoContainer = adInfoContainer.select("a").attr("title");

    Pattern pattern = Pattern.compile("(\\d{4})");
    Matcher matcher = pattern.matcher(adYearBrandModelInfoContainer);
    Integer adYear = -1;
    if (matcher.find()) {
      adYear = Integer.parseInt(matcher.group());
    }
    return adYear;
  }

  public static String extractBrandFromAdContainer(Element adInfoContainer, ArrayList<String> allVehicleBrands) {
    StringBuilder brandsRegex = new StringBuilder();
    String adYearBrandModelInfoContainer = adInfoContainer.select("a").attr("title");

    brandsRegex.append("\\b(");
    brandsRegex.append(String.join("|", allVehicleBrands.toArray(new CharSequence[0])));
    brandsRegex.append(")");

    Pattern pattern = Pattern.compile(brandsRegex.toString(), Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(adYearBrandModelInfoContainer);
    String adBrand = "";
    if (matcher.find()) {
      adBrand = StringUtils.capitalize(matcher.group().toLowerCase());
    }
    return adBrand;
  }

  public static String extractModelFromAdContainer(Element adInfoContainer, ArrayList<String> allVehicleModels) {
    StringBuilder modelsRegex = new StringBuilder();
    String adYearBrandModelInfoContainer = adInfoContainer.select("a").attr("title");
    modelsRegex.append("\\b(");
    modelsRegex.append(String.join("|", allVehicleModels.toArray(new CharSequence[0])));
    modelsRegex.append(")");
    String modelsRegexString = modelsRegex.toString().replace("mazda 6", "mazda 6|mazda6").replace("mazda 3", "mazda 3|mazda3");
    
    Pattern pattern = Pattern.compile(modelsRegexString, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(adYearBrandModelInfoContainer);
    String adModel = null;
    if (matcher.find()) {
      adModel = StringUtils.capitalize(matcher.group().toLowerCase().replace(" ", ""));
      adModel = adModel.replace("Mazda3", "Mazda 3").replace("Mazda6", "Mazda 6");
    }
    return adModel;
  }

  public static Integer extractPriceFromAdContainer(Element adInfoContainer) {
    Pattern pattern = Pattern.compile("(\\d{1,2}\\,\\d{3})");
    Matcher matcher = pattern.matcher(adInfoContainer.select(".delta").text());
    Integer adPrice = -1;
    if (matcher.find()) {
      adPrice = Integer.parseInt(matcher.group().replace(",", ""));
    }
    return adPrice;
  }

  public static Integer extractMileageFromAdContainer(Element adInfoContainer) {
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
    return adMileage;
  }
}
