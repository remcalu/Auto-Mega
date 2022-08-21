import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RootState } from '../store';

interface ViewsState {
  isGallery: boolean
}

const initialState: ViewsState = {
  isGallery: true
}

export const viewsSlices = createSlice({
  name: 'views',
  initialState,
  reducers: {
    setIsGallery: (state, action: PayloadAction<boolean>) => {
      state.isGallery = action.payload;
    },
  },
})

export const { setIsGallery } = viewsSlices.actions;
export const isGallery = (state: RootState) => state.views.isGallery;
export default viewsSlices.reducer;