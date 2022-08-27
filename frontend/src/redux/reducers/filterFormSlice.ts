import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { INITIAL_DISTANCE, INITIAL_MAX_MILEAGE, INITIAL_MAX_PRICE, INITIAL_MIN_YEAR } from '../../util/Constants';
import { RootState } from '../store';

interface filterFormState {
  postalCode: string;
  distance: number;
  vehicles: Array<string>;
  transmission: Array<string>;
  vendorType: Array<string>;
  maxPrice: number;
  maxMileage: number;
  minYear: number;
}

const initialState: filterFormState = {
  postalCode: "",
  distance: INITIAL_DISTANCE,
  vehicles: [],
  transmission: [],
  vendorType: [],
  maxPrice: INITIAL_MAX_PRICE,
  maxMileage: INITIAL_MAX_MILEAGE,
  minYear: INITIAL_MIN_YEAR
}

export const filterFormSlice = createSlice({
  name: 'filterForm',
  initialState,
  reducers: {
    setPostalCode: (state, action: PayloadAction<string>) => {
      state.postalCode = action.payload;
    },
    setDistance: (state, action: PayloadAction<number>) => {
      state.distance = action.payload;
    },
    setVehicles: (state, action: PayloadAction<Array<string>>) => {
      state.vehicles = action.payload;
    },
    setTransmission: (state, action: PayloadAction<Array<string>>) => {
      state.transmission = action.payload;
    },
    setVendorType: (state, action: PayloadAction<Array<string>>) => {
      state.vendorType = action.payload;
    },
    setMaxPrice: (state, action: PayloadAction<number>) => {
      state.maxPrice = action.payload;
    },
    setMaxMileage: (state, action: PayloadAction<number>) => {
      state.maxMileage = action.payload;
    },
    setMinYear: (state, action: PayloadAction<number>) => {
      state.minYear = action.payload;
    },
  },
})

export const { setPostalCode, setDistance, setVehicles, setTransmission, setVendorType,
  setMaxPrice, setMaxMileage, setMinYear } = filterFormSlice.actions;
export const getPostalCode = (state: RootState) => state.filterForm.postalCode;
export const getDistance = (state: RootState) => state.filterForm.distance;
export const getVehicles = (state: RootState) => state.filterForm.vehicles;
export const getTransmission = (state: RootState) => state.filterForm.transmission;
export const getVendorType = (state: RootState) => state.filterForm.vendorType;
export const getMaxPrice = (state: RootState) => state.filterForm.maxPrice;
export const getMaxMileage = (state: RootState) => state.filterForm.maxMileage;
export const getMinYear = (state: RootState) => state.filterForm.minYear;
export default filterFormSlice.reducer;