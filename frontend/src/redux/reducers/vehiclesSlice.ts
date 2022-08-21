import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import Vehicle from "../../types/Vehicle";
import { preSort } from '../../util/VehicleCardUtil';
import { getAllVehicles, getVehicleCount } from '../../util/VehicleService';
import { AppDispatch, RootState } from '../store';

interface VehiclesState {
  vehicles: Vehicle[];
  numVehicles: number;
}

const initialState: VehiclesState = {
  vehicles: [],
  numVehicles: 0
}

export const vehiclesSlice = createSlice({
  name: 'vehicle',
  initialState,
  reducers: {
    setVehicles: (state, action: PayloadAction<Vehicle[]>) => {
      state.vehicles = action.payload;
    },
    setVehicleCount: (state, action: PayloadAction<number>) => {
      state.numVehicles = action.payload;
    },
    sortPriceAscending: (state, action: PayloadAction<Vehicle[]>) => {
      let presortedVehicles = preSort([...action.payload])
      state.vehicles = presortedVehicles.sort((a, b) => a.price - b.price);
    },
    sortPriceDescending: (state, action: PayloadAction<Vehicle[]>) => {
      let presortedVehicles = preSort([...action.payload])
      state.vehicles = presortedVehicles.sort((a, b) => b.price - a.price);
    },
    sortMileageAscending: (state, action: PayloadAction<Vehicle[]>) => {
      let presortedVehicles = preSort([...action.payload])
      state.vehicles = presortedVehicles.sort((a, b) => a.mileage - b.mileage);
    },
    sortMileageDescending: (state, action: PayloadAction<Vehicle[]>) => {
      let presortedVehicles = preSort([...action.payload])
      state.vehicles = presortedVehicles.sort((a, b) => b.mileage - a.mileage);
    },
    sortYearAscending: (state, action: PayloadAction<Vehicle[]>) => {
      let presortedVehicles = preSort([...action.payload])
      state.vehicles = presortedVehicles.sort((a, b) => a.year - b.year);
    },
    sortYearDescending: (state, action: PayloadAction<Vehicle[]>) => {
      let presortedVehicles = preSort([...action.payload])
      state.vehicles = presortedVehicles.sort((a, b) => b.year - a.year);
    },
  },
})

export async function fetchVehicles(dispatch: AppDispatch) {
  getAllVehicles().then(
    e => dispatch(setVehicles(preSort(e as Array<Vehicle>)))
  )
}

export async function fetchNumVehicles(dispatch: AppDispatch) {
  getVehicleCount().then(
    e => dispatch(setVehicleCount(e as number))
  )
}

export const { setVehicles, setVehicleCount, sortPriceAscending, sortPriceDescending, sortMileageAscending,
  sortMileageDescending, sortYearAscending, sortYearDescending } = vehiclesSlice.actions;
export const selectVehicles = (state: RootState) => state.vehicles.vehicles;
export const selectNumVehicles = (state: RootState) => state.vehicles.numVehicles;
export default vehiclesSlice.reducer;