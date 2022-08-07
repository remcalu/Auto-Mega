package auto.mega.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import auto.mega.models.ConfigOptions;
import auto.mega.models.Vehicle;
import io.github.bonigarcia.wdm.WebDriverManager;

public class KijijiAutosParser extends SeleniumWebsiteParser {

	public ArrayList<Vehicle> parseWebsite(ConfigOptions searchOptions) throws InterruptedException {
    ArrayList<Vehicle> allVehicles = new ArrayList<>();

    ChromeDriver driver = initWebDriver();

		HashMap<String, Object> urlParamsMap = createUrlParamMap(searchOptions);
		String url = urlConstructor(urlParamsMap);
		driver.get(url);

		/* Set the makes */
		setBrands(driver, searchOptions);

		/* Set the models */
		setModels(driver, searchOptions);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		for(int i = 0; i < 15; i++) {
			jse.executeScript("window.scrollBy(0,10000)");
			Thread.sleep(1000);
		}
		
    return allVehicles;
  }

	private ChromeDriver initWebDriver() {
    /* Initializing chrome webdriver */
    WebDriverManager.chromedriver().setup();
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments(
      "--ignore-certificate-errors",
      "--disable-default-apps",
      "--disable-extensions",
      "--disable-web-security",
      "--disable-popup-blocking", 
      "--disable-infobars",
      "--disable-gpu"
    );
    ChromeDriver webdDriver = new ChromeDriver(chromeOptions);
    webdDriver.manage().deleteAllCookies();

    return webdDriver;
  }

	private void setBrands(WebDriver driver, ConfigOptions searchOptions) throws InterruptedException {
		driver.findElement(By.id("make")).click();
		for (String brand : searchOptions.getVehicleBrandModels().keySet()) {
			setName(driver, brand, true);
		}
		driver.findElement(By.cssSelector("ul[data-testid='MakeFilterDesktopMenu']"))
			.findElement(By.cssSelector("button[data-testid='ApplyButton']"))
			.click();
	}


	private void setModels(WebDriver driver, ConfigOptions searchOptions) throws InterruptedException {
		driver.findElement(By.id("model")).click();
		for (String brand : searchOptions.getVehicleBrandModels().keySet()) {
			for (String model : searchOptions.getVehicleBrandModels().get(brand)) {
				setName(driver, model, false);
			}
		}
		driver.findElement(By.cssSelector("ul[data-testid='ModelFilterDesktopMenu']"))
			.findElement(By.cssSelector("button[data-testid='ApplyButton']"))
			.click();
	}

	private void setName(WebDriver driver, String name, boolean isBrand) {
		try {
			String nameType = isBrand ? "Make" : "Model";
			name = StringUtils.capitalize(name);
			WebElement searchBar = driver.findElement(By.cssSelector("ul[data-testid='" + nameType + "FilterDesktopMenu']"))
				.findElement(By.cssSelector("input[role='search']"));
			WebElement searchItem = driver.findElement(By.cssSelector("input[name='" + name + "']"))
				.findElement(By.xpath(".."))
				.findElement(By.xpath(".."));

			searchBar.sendKeys(name);
			searchItem.click();
			searchBar.click();
			searchBar.sendKeys(Keys.CONTROL + "a");
			searchBar.sendKeys(Keys.DELETE);
		} catch (Exception e) {
			System.out.println("Error for: " + name);
		}
	}

	  /** Constructs a URL for a specific website to narrow down search operations for a vehicle
   * @param params the map containing key value pairs of various paramaters for URL construction
   * @return       the constructed URL
  */
  protected String urlConstructor(Map<String, Object> params) {
    Integer minYear = (Integer) params.get("minYear");
    Integer maxPrice = (Integer) params.get("maxPrice");
    Integer maxMileage = (Integer) params.get("maxMileage");
    String transmission = (String) params.get("transmission");
    Boolean includePrivateDealers = (Boolean) params.get("includePrivateDealers");

    /* Some cleaning up and post processing */
    String privateDealer = StringUtils.EMPTY;
    if (Boolean.FALSE.equals(includePrivateDealers)) {
      privateDealer = "&st=DEALER";
    }

		String transmissionString = StringUtils.EMPTY;
		if ("Automatic".equals(transmission)) {
			transmissionString = "&tr=AUTOMATIC_GEAR";
		} else if ("Manual".equals(transmission)) {
			transmissionString = "&tr=MANUAL_GEAR";
		}

    return "https://www.kijijiautos.ca/cars/automatic/#con=USED&con=NEW&ml=10000%3A" + 
		maxMileage + "&od=down&p=1000%3A" + 
		maxPrice + "&sb=rel" + 
		privateDealer + 
		transmissionString + "&yc=" + 
		minYear + "%3A";
  }
}
