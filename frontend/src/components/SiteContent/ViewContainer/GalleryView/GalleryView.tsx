import './GalleryView.css';
import VehicleCard from './VehicleCard/VehicleCard';
import { Box, Grid, Pagination } from '@mui/material';
import { useAppSelector } from '../../../../redux/hooks';
import { selectVehicles } from '../../../../redux/reducers/vehiclesSlice';
import { useState } from 'react';
import { calcShownVehicles, calcNumPages, scrollTop } from '../../../../util/VehicleCardUtil';

export default function GalleryView() {
  const vehicles = useAppSelector(selectVehicles);

  const [page, setPage] = useState(1);
  const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
    scrollTop();
  };

  return (
    <Box className="GalleryView-body">
      <Box className="GalleryView-body">
        <Grid className="GalleryView-grid" container spacing={2}>
          {calcShownVehicles(vehicles, page).length !== 0 && calcShownVehicles(vehicles, page).map((vehicle, index) => 
            <Grid item xs="auto" zeroMinWidth key={index}>
              <VehicleCard vehicle={vehicle}/>
            </Grid>
          )}
        </Grid>
      </Box>
      <Box display="flex" justifyContent="center">
        {vehicles.length !== 0 && <Pagination count={calcNumPages(vehicles)} page={page} onChange={handleChange} />}
      </Box>
    </Box>
  );
}
