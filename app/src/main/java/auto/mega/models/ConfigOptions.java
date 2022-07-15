package auto.mega.models;

import java.util.Map;
import java.util.Objects;
import java.util.ArrayList;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ConfigOptions {
  private Map<String, ArrayList<String>> vehicleBrandModels;
  private String transmission;
  private String postalCode;
  private Integer distanceFromPostalCode;
  private Integer maxMileage;
  private Integer maxPrice;
  private Integer minYear;
  private Double belowThreshold;
  private Boolean includePrivateDealers;

  private ConfigOptions(Builder builder) {
		this.vehicleBrandModels = builder.vehicleBrandModels;
    this.transmission = builder.transmission;
		this.postalCode = builder.postalCode;
		this.distanceFromPostalCode = builder.distanceFromPostalCode;
		this.maxMileage = builder.maxMileage;
		this.maxPrice = builder.maxPrice;
    this.minYear = builder.minYear;
    this.belowThreshold = builder.belowThreshold;
    this.includePrivateDealers = builder.includePrivateDealers;
	}

  public Map<String, ArrayList<String>> getVehicleBrandModels() { return vehicleBrandModels; }
  public String getTransmission() { return transmission; }
  public String getPostalCode() { return postalCode; }
  public Integer getDistanceFromPostalCode() { return distanceFromPostalCode; }
  public Integer getMaxMileage() { return maxMileage; }
  public Integer getMaxPrice() { return maxPrice; }
  public Integer getMinYear() { return minYear; }
  public Double getBelowThreshold() { return belowThreshold; }
  public Boolean getIncludePrivateDealers() { return includePrivateDealers; }

  @Override
  public String toString() {
    return new ToStringBuilder(this).
      append("vehicleBrandModels", vehicleBrandModels).
      append("transmission", transmission).
      append("postalCode", postalCode).
      append("distanceFromPostalCode", distanceFromPostalCode).
      append("maxMileage", maxMileage).
      append("maxPrice", maxPrice).
      append("minYear", minYear).
      append("belowThreshold", belowThreshold).
      append("includePrivateDealers", includePrivateDealers).
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
    ConfigOptions that = (ConfigOptions) o;
    return new EqualsBuilder().
      appendSuper(super.equals(o)).
      append(vehicleBrandModels, that.vehicleBrandModels).
      append(transmission, that.transmission).
      append(postalCode, that.postalCode).
      append(distanceFromPostalCode, that.distanceFromPostalCode).
      append(maxMileage, that.maxMileage).
      append(maxPrice, that.maxPrice).
      append(minYear, that.minYear).
      append(belowThreshold, that.belowThreshold).
      append(includePrivateDealers, that.includePrivateDealers).
      isEquals();
  }

  @Override
  public int hashCode() {
    return Objects.hash(vehicleBrandModels, transmission, postalCode, distanceFromPostalCode, maxMileage, maxPrice, minYear, belowThreshold, includePrivateDealers);
  }

  public static class Builder {
    private Map<String, ArrayList<String>> vehicleBrandModels;
    private String transmission;
    private String postalCode;
    private Integer distanceFromPostalCode;
    private Integer maxMileage;
    private Integer maxPrice;
    private Integer minYear;
    private Double belowThreshold;
    private Boolean includePrivateDealers;

    public Builder withVehicleBrandModels(Map<String, ArrayList<String>> vehicleBrandModels) { this.vehicleBrandModels = vehicleBrandModels; return this; }
    public Builder withTransmission(String transmission) { this.transmission = transmission; return this; }
    public Builder withPostalCode(String postalCode) { this.postalCode = postalCode; return this; }
    public Builder withDistanceFromPostalCode(Integer distanceFromPostalCode) { this.distanceFromPostalCode = distanceFromPostalCode;  return this; } 
    public Builder withMaxMileage(Integer maxMileage) { this.maxMileage = maxMileage; return this; }
    public Builder withMaxPrice(Integer maxPrice) { this.maxPrice = maxPrice; return this; }
    public Builder withMinYear(Integer minYear) { this.minYear = minYear; return this; }
    public Builder withBelowThreshold(Double belowThreshold) { this.belowThreshold = belowThreshold; return this; }
    public Builder withIncludePrivateDealers(Boolean includePrivateDealers) { this.includePrivateDealers = includePrivateDealers; return this; }
    
    public ConfigOptions build() {
      return new ConfigOptions(this);
    }
  }
}
