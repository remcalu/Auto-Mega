import './GalleryView.css';
import VehicleCard from './VehicleCard/VehicleCard';
import { Grid } from '@mui/material';
import { useAppSelector } from '../../../../redux/hooks';
import { RootState } from '../../../../redux/store';

function GalleryView() {
  const vehicles = useAppSelector((state: RootState) => state.vehicles.vehicles);
  
  return (
    <div className="GalleryView-body">
      <Grid className="GalleryView-grid" container spacing={2}>
        {vehicles.map((vehicle, index) => 
        <Grid item xs="auto" zeroMinWidth key={index}>
          <VehicleCard vehicle={vehicle}/>
        </Grid>
        )}
      </Grid>
    </div>
  );
}

export default GalleryView;
