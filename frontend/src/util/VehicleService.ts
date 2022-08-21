export async function getAllVehicles() {
  try {
    const response = await fetch('/api/vehicles');
    return await response.json();
  } catch(error) {
    return [];
  }
}

export async function getVehicleCount() {
  try {
    const response = await fetch('/api/vehicles/count');
    return await response.json();
  } catch(error) {
    return [];
  }
}

export async function refetchAllVehicles() {
  try {
    const response = await fetch('/api/vehicles/refetch');
    return await response.json();
  } catch(error) {
    return [];
  }
}