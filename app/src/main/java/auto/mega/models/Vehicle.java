package auto.mega.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Table(name="vehicles")
@IdClass(Vehicle.class)
@Entity
public class Vehicle implements Serializable{

  Vehicle() {}

  @Id
  @Column(name="id", length=64)
  private String id;

  @Column(name="brand", length=16)
  private String brand;

  @Column(name="model", length=16)
  private String model;
  
  @Column(name="website", length=16)
  private String website;
  
  @Column(name="date_scraped", length=16)
  private String dateScraped;
  
  @Column(name="link", length=256)
  private String link;

  @Column(name="image_link", length=256)
  private String imageLink;
  
  @Column(name="transmission", length=16)
  private String transmission;

  @Column(name="is_private_dealer")
  private Boolean isPrivateDealer;
  
  @Column(name="instant_scraped")
  private Long instantScraped;
  
  @Column(name="mileage")
  private Integer mileage;
  
  @Column(name="price")
  private Integer price;

  @Column(name="year")
  private Integer year;

  private Vehicle(Builder builder) {
    this.id = builder.id;
		this.brand = builder.brand;
		this.model = builder.model;
		this.website = builder.website;
		this.dateScraped = builder.dateScraped;
		this.link = builder.link;
    this.imageLink = builder.imageLink;
    this.transmission = builder.transmission;
    this.isPrivateDealer = builder.isPrivateDealer;
    this.instantScraped = builder.instantScraped;
    this.mileage = builder.mileage;
    this.price = builder.price;
    this.year = builder.year;
	}

  public String getId() { return id; }
  public String getBrand() { return brand; }
  public String getModel() { return model; }
  public String getWebsite() { return website; }
  public String getDateScraped() { return dateScraped; }
  public String getLink() { return link; }
  public String getImageLink() { return imageLink; }
  public String getTransmission() { return transmission; }
  public Boolean getIsPrivateDealer() { return isPrivateDealer; }
  public Long getInstantScraped() { return instantScraped; }
  public Integer getMileage() { return mileage; }
  public Integer getYear() { return year; }
  public Integer getPrice() { return price; }

  public String toStringPretty() {
    String websiteShort = "";
    if ("Autotrader".equals(website)) {
      websiteShort = "AT";
    } else if ("Kijiji".equals(website)) {
      websiteShort = "KI";
    } else if ("Carpages".equals(website)) {
      websiteShort = "CP";
    }
    String privateDealerString = Boolean.TRUE.equals(isPrivateDealer) ? "PRIV" : "DEAL";
    return String.format("[%s] %-3s %-6s %-8s $%-7s %-12s %-13s %-20s %s%n", dateScraped, websiteShort, year, mileage, price, brand, model, privateDealerString, link);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("id", id)
      .append("brand", brand)
      .append("model", model)
      .append("website", website)
      .append("dateScraped", dateScraped)
      .append("link", link)
      .append("imageLink", imageLink)
      .append("transmission", transmission)
      .append("isPrivateDealer", isPrivateDealer)
      .append("instantScraped", instantScraped)
      .append("mileage", mileage)
      .append("price", price)
      .append("year", year)
      .toString();
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
    return new EqualsBuilder()
      .appendSuper(super.equals(o))
      .append(id, that.id)
      .append(brand, that.brand)
      .append(model, that.model)
      .append(website, that.website)
      .append(dateScraped, that.dateScraped)
      .append(link, that.link)
      .append(imageLink, that.imageLink)
      .append(transmission, that.transmission)
      .append(isPrivateDealer, that.isPrivateDealer)
      .append(instantScraped, instantScraped)
      .append(mileage, that.mileage)
      .append(price, that.price)
      .append(year, that.year)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, brand, model, website, dateScraped, link, imageLink, transmission, isPrivateDealer, instantScraped, mileage, price, year);
  }

  public static class Builder {
    private String id;
    private String brand;
    private String model;
    private String website;
    private String dateScraped;
    private String link;
    private String imageLink;
    private String transmission;
    private Boolean isPrivateDealer;
    private Long instantScraped;
    private Integer mileage;
    private Integer price;
    private Integer year;

    public Builder withBrand(String brand) { this.brand = brand; return this; }
    public Builder withModel(String model) { this.model = model; return this; }
    public Builder withWebsite(String website) { this.website = website; return this; }
    public Builder withDateScraped(String dateScraped) { this.dateScraped = dateScraped;  return this; } 
    public Builder withLink(String link) { this.link = link; return this; }
    public Builder withImageLink(String imageLink) {this.imageLink = imageLink; return this; }
    public Builder withTransmission(String transmission) {this.transmission = transmission; return this; }
    public Builder withIsPrivateDealer(Boolean isPrivateDealer) { this.isPrivateDealer = isPrivateDealer; return this; }
    public Builder withInstantScraped(Long instantScraped) { this.instantScraped = instantScraped; return this; }
    public Builder withMileage(Integer mileage) { this.mileage = mileage; return this; }
    public Builder withPrice(Integer price) { this.price = price; return this; }
    public Builder withYear(Integer year) { this.year = year; return this; }
    
    public Vehicle build() {
      this.id = this.brand + "-" + this.model + "-" + this.price + "-" + this.year + "-" + this.mileage + "-" + this.instantScraped;
      return new Vehicle(this);
    }
  }
}
