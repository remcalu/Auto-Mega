package auto.mega.models;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Vehicle {
  private String brand;
  private String model;
  private String website;
  private String dateScraped;
  private String link;
  private Boolean isPrivateDealer;
  private Integer marketValueDifference;
  private Integer mileage;
  private Integer price;
  private Integer year;

  private Vehicle(Builder builder) {
		this.brand = builder.brand;
		this.model = builder.model;
		this.website = builder.website;
		this.dateScraped = builder.dateScraped;
		this.link = builder.link;
    this.isPrivateDealer = builder.isPrivateDealer;
    this.marketValueDifference = builder.marketValueDifference;
    this.mileage = builder.mileage;
    this.price = builder.price;
    this.year = builder.year;
	}

  public String getBrand() { return brand; }
  public String getModel() { return model; }
  public String getWebsite() { return website; }
  public String getDateScraped() { return dateScraped; }
  public String getLink() { return link; }
  public Boolean getIsPrivateDealer() { return isPrivateDealer; }
  public Integer getMarketValueDifference() { return marketValueDifference; }
  public Integer getMileage() { return mileage; }
  public Integer getYear() { return year; }
  public Integer getPrice() { return price; }

  public String toStringPretty() {
    String websiteShort = "";
    if ("Autotrader".equals(website)) {
      websiteShort = "AT";
    }

    String privateDealerString = Boolean.TRUE.equals(isPrivateDealer) ? "PRIV" : "DEAL";
    return String.format("[%s] %s   %-6s %-8s $%-7s %-12s %-13s %-6s %-20s %s%n", dateScraped, websiteShort, year, mileage, price, brand, model, privateDealerString, marketValueDifference, link);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).
      append("brand", brand).
      append("model", model).
      append("website", website).
      append("dateScraped", dateScraped).
      append("link", link).
      append("isPrivateDealer", isPrivateDealer).
      append("marketValueDifference", marketValueDifference).
      append("mileage", mileage).
      append("price", price).
      append("year", year).
      toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
       return true;
    }
    if (o == null || getClass() != o.getClass()) {
       return false;
    }
    Vehicle that = (Vehicle) o;
    return new EqualsBuilder().
      appendSuper(super.equals(o)).
      append(brand, that.brand).
      append(model, that.model).
      append(website, that.website).
      append(dateScraped, that.dateScraped).
      append(link, that.link).
      append(marketValueDifference, that.marketValueDifference).
      append(isPrivateDealer, that.isPrivateDealer).
      append(mileage, that.mileage).
      append(price, that.price).
      append(year, that.year).
      isEquals();
  }

  @Override
  public int hashCode() {
    return Objects.hash(brand, model, website, dateScraped, link, isPrivateDealer, marketValueDifference, mileage, price, year);
  }

  public static class Builder {
    private String brand;
    private String model;
    private String website;
    private String dateScraped;
    private String link;
    private Boolean isPrivateDealer;
    private Integer marketValueDifference;
    private Integer mileage;
    private Integer price;
    private Integer year;

    public Builder withBrand(String brand) { this.brand = brand; return this; }
    public Builder withModel(String model) { this.model = model; return this; }
    public Builder withWebsite(String website) { this.website = website; return this; }
    public Builder withDateScraped(String dateScraped) { this.dateScraped = dateScraped;  return this; } 
    public Builder withLink(String link) { this.link = link; return this; }
    public Builder withIsPrivateDealer(Boolean isPrivateDealer) { this.isPrivateDealer = isPrivateDealer; return this; }
    public Builder withMarketValueDifference(Integer marketValueDifference) { this.marketValueDifference = marketValueDifference; return this; }
    public Builder withMileage(Integer mileage) { this.mileage = mileage; return this; }
    public Builder withPrice(Integer price) { this.price = price; return this; }
    public Builder withYear(Integer year) { this.year = year; return this; }
    
    public Vehicle build() {
      return new Vehicle(this);
    }
  }
}