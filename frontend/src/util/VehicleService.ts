import FetchOptions from "../types/FetchOptions";

export async function getAllVehicles() {
  try {
    const response = await fetch('/api/vehicles');
    return await response.json();
  } catch(error) {
    console.error(error);
    return [];
  }
}

export async function getVehicleCount() {
  try {
    const response = await fetch('/api/vehicles/count');
    return await response.json();
  } catch(error) {
    console.error(error);
    return [];
  }
}

export async function refetchAllVehicles(fetchOptions: FetchOptions) {
  try {
    const response = await fetch('/api/vehicles/refetch', {
      method: "POST",
      body: JSON.stringify({
          postalCode: fetchOptions.postalCode,
          distance: fetchOptions.distance,
          vehicles: fetchOptions.vehicles,
          transmission: fetchOptions.transmission,
          vendorType: fetchOptions.vendorType,
          maxPrice: fetchOptions.maxPrice,
          maxMileage: fetchOptions.maxMileage,
          minYear: fetchOptions.minYear,
          
      }),
    });
    return await response.json();
  } catch(error) {
    console.error(error);
    return [];
  }
}

export async function clearAllVehicles() {
  try {
    const response = await fetch('/api/vehicles/clear');
    return await response.json();
  } catch(error) {
    console.error(error);
    return [];
  }
}