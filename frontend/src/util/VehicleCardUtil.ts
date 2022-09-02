import { toast } from "react-toastify";
import Vehicle from "../types/Vehicle";

export function formatIsPrivateDealer(isPrivateDealer: boolean) {
  if (isPrivateDealer) {
    return "Private seller"
  }
  return "Dealership"
}

export function getColorFromWebsite(website: string) {
  if (website === "Autotrader") {
    return "#300000";
  } else if (website === "Carpages") {
    return "#003001";
  } else if (website === "Kijiji") {
    return "#30002d";
  }
  return "#000000"; 
}

export function formatWithCommas(number: number) {
  return number.toLocaleString('en-US');
}

export function preSort(vehicles: Vehicle[]) {
  let sortedVehicles = vehicles;
  return sortedVehicles.sort((a, b) => b.year - a.year)
    .sort((a, b) => a.mileage - b.mileage)
    .sort((a, b) => a.price - b.price);
}

export function calcShownVehicles(vehicles, pageNum) {
  if (vehicles.length === 0) {
    return [];
  }

  let vehiclesForPage = [];
  for (let i = 0; i < vehicles.length; i += 24) {
    vehiclesForPage.push(vehicles.slice(i, i+24));
  }
  return vehiclesForPage[pageNum-1];
}

export function calcNumPages(vehicles) {
  return Math.ceil(vehicles.length / 24);
}

export function scrollTop() {
  window.scrollTo({top: 0, left: 0, behavior: 'smooth'});
}

export const notifyError = (text: string) => toast.error(text, {
  position: "top-center",
  autoClose: 7000,
  hideProgressBar: false,
  closeOnClick: true,
  draggable: true,
  theme: "colored",
  progress: undefined,
});

export const notifySuccess = (text: string) => toast.success(text, {
  position: "top-center",
  autoClose: 7000,
  hideProgressBar: false,
  closeOnClick: true,
  draggable: true,
  theme: "colored",
  progress: undefined,
});
