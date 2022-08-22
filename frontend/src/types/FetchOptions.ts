type FetchOptions = {
    postalCode: string,
    distance: number,
    vehicles: string[],
    transmission: string[],
    vendorType: string[],
    maxPrice: number,
    maxMileage: number,
    minYear: number,
  }
  
  export default FetchOptions;