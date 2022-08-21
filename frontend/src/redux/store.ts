import { configureStore } from '@reduxjs/toolkit'
import vehiclesReducer from './reducers/vehiclesSlice';

const store = configureStore({
  reducer: {
    vehicles: vehiclesReducer
  },
});

export default store;

export type RootState = ReturnType<typeof store.getState>;
export type GetState = typeof store.getState;
export type AppDispatch = typeof store.dispatch;