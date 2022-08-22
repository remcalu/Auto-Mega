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
      urlParamMap.put("distance", searchOptions.getdistance());
      urlParamMap.put("postalCode", searchOptions.getPostalCode());
      urlParamMap.put("transmission", searchOptions.getTransmission());
      urlParamMap.put("includePrivateDealers", searchOptions.getIncludePrivateDealers());
    return urlParamMap;
  }

  protected String getImageUrlFromModel(String model) {
    if ("corolla".equalsIgnoreCase(model)) {
      return "https://s3.amazonaws.com/toyota.site.toyota-v5/tci-prod/toyota/media/build/cor/col/big/b22_bprbe_fl1_0209_a.png?ck=08122022045935";
    } else if ("camry".equalsIgnoreCase(model)) {
      return "https://www.motortrend.com/uploads/sites/10/2021/07/2021-toyota-camry-hybrid-xse-sedan-angular-front.png";
    } else if ("avalon".equalsIgnoreCase(model)) {
      return "https://crdms.images.consumerreports.org/c_lfill,w_470,q_auto,f_auto/prod/cars/cr/car-versions/12689-2019-toyota-avalon-hybrid-xle";
    } else if ("matrix".equalsIgnoreCase(model)) {
      return "https://mysterio.yahoo.com/mysterio/api/A38935504FFEE34580C754A658E32E83E92B887A346AE575D29177E4547C4E98/autoblog/resizefill_w660_h372;quality_80;format_webp;cc_31536000;/https://s.aolcdn.com/commerce/autodata/images/USC30TOC171B021001.jpg";
    } else if ("yaris".equalsIgnoreCase(model)) {
      return "https://www.motortrend.com/uploads/sites/10/2015/11/2014-toyota-yaris-le-3-door-hatchback-angular-front.png";
    } else if ("civic".equalsIgnoreCase(model)) {
      return "https://images.honda.ca/models/H/Models/2023/civic_sedan/sport_10766_nh_904mmeteoroid_gray_metallic_front.png?width=1000";
    } else if ("accord".equalsIgnoreCase(model)) {
      return "https://images.honda.ca/models/H/Models/2022/accord_sedan/sport_2_0_10734_crystal_black_pearl_front.png?width=1000";
    } else if ("insight".equalsIgnoreCase(model)) {
      return "https://www.ccarprice.com/products/Honda_Insight_EX_2022_1.jpg";
    } else if ("forte".equalsIgnoreCase(model)) {
      return "https://platform.cstatic-images.com/large/in/v2/stock_photos/e6a2de4c-4149-4159-b663-7a14a6a15abb/c8d23e34-664a-45c4-a9ea-0fbf16ee017e.png";
    } else if ("niro".equalsIgnoreCase(model)) {
      return "https://img.sm360.ca/ir/w640h390c/images/newcar/ca/2022/kia/niro-hev/ex/cuv/exteriorColors/15165_cc0640_032_abp.png";
    } else if ("elantra".equalsIgnoreCase(model)) {
      return "https://platform.cstatic-images.com/xlarge/in/v2/stock_photos/4825d411-30aa-4fe4-8e13-510a538f9991/74c2f29a-da6d-486b-b61f-3710c6198732.png";
    } else if ("accent".equalsIgnoreCase(model)) {
      return "https://www.ccarprice.com/products/Hyundai_Accent_Limited_2021.jpg";
    } else if ("sonata".equalsIgnoreCase(model)) {
      return "https://www.ccarprice.com/products/Hyundai_Sonata_Hybrid_Limited_2021.jpg";
    } else if ("veloster".equalsIgnoreCase(model)) {
      return "https://file.kelleybluebookimages.com/kbb/base/evox/CP/10649/2016-Hyundai-Veloster-front_10649_032_1892x817_MZH_cropped.png";
    } else if ("venue".equalsIgnoreCase(model)) {
      return "https://dealerimages.dealereprocess.com/image/upload/c_limit,f_auto,fl_lossy,w_auto/v1/svp/dep/22hyundaivenueselsu1t/hyundai_22venueselsu1t_angularfront_galacticgray";
    } else if ("mazda 3".equalsIgnoreCase(model)) {
      return "https://di-uploads-pod14.dealerinspire.com/mazdaofnewrochelle/uploads/2020/03/2020-Mazda-CX-5-MLP-Hero.png";
    } else if ("mazda 6".equalsIgnoreCase(model)) {
      return "https://platform.cstatic-images.com/medium/in/v2/stock_photos/3435a83e-8751-498a-8c7d-462d8f42e83c/c817b9e7-21d5-47ee-a715-2e599b586999.png";
    } else if ("altima".equalsIgnoreCase(model)) {
      return "https://www.nobodydealslike.com/vimgs/usc90nic041a022000/IOFSPICBG/ColourPhotoSample_0.jpg";
    } else if ("maxima".equalsIgnoreCase(model)) {
      return "https://file.kelleybluebookimages.com/kbb/base/evox/CP/11403/2018-Nissan-Maxima-front_11403_032_1772x741_KH3_cropped.png";
    } else if ("sentra".equalsIgnoreCase(model)) {
      return "https://img.sm360.ca/ir/w600h340c/images/newcar/ca/2022/hyundai/elantra/ultimate/sedan/exteriorColors/2022_hyundai_elantra_ultimate_032_nka.png";
    } else if ("versa".equalsIgnoreCase(model)) {
      return "https://platform.cstatic-images.com/medium/in/v2/stock_photos/a644cf55-4bc7-4739-94aa-b19e64790a5f/b4c2ddce-7975-4c96-af25-94bae10ee9a8.png";
    } else if ("is".equalsIgnoreCase(model)) {
      return "https://s3.amazonaws.com/lexus.site.lexus/tci-prod/lexus/media/build/is/col/big/b22_gz1e2t_fl2_0223.png?ck=08122022104411";
    } else if ("es".equalsIgnoreCase(model)) {
      return "https://toyota.scene7.com/is/image/toyota/Lexus-ES-250-AWD-visualizer-styles-750x471-LEX-ESG-MY22-0026-04?wid=750&hei=471&fmt=png-alpha";
    }
    return "";
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
