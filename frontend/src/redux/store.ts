import { configureStore } from '@reduxjs/toolkit'
import vehiclesReducer from './reducers/vehiclesSlice';
import viewsReducer from './reducers/viewsSlice';
import filterFormReducer from './reducers/filterFormSlice';

const store = configureStore({
  reducer: {
    vehicles: vehiclesReducer,
    views: viewsReducer,
    filterForm: filterFormReducer
  },
});

export default store;

export type RootState = ReturnType<typeof store.getState>;
export type GetState = typeof store.getState;
export type AppDispatch = typeof store.dispatch;