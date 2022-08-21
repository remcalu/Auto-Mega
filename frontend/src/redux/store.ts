import { configureStore } from '@reduxjs/toolkit'
import vehiclesReducer from './reducers/vehiclesSlice';
import viewsReducer from './reducers/viewsSlice';

const store = configureStore({
  reducer: {
    vehicles: vehiclesReducer,
    views: viewsReducer
  },
});

export default store;

export type RootState = ReturnType<typeof store.getState>;
export type GetState = typeof store.getState;
export type AppDispatch = typeof store.dispatch;