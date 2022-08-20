import React, { useEffect, useState } from 'react';
import './GalleryView.css';
import { getAllVehicles } from '../../../../util/VehicleService';
import Vehicle from '../../../../types/Vehicle';
import VehicleCard from './VehicleCard/VehicleCard';
import { Grid } from '@mui/material';

function GalleryView() {
  const [vehicles, setVehicles] = useState<Vehicle[]>([]);
  useEffect(() => {
    async function fetchData() {
      return getAllVehicles();
    }
    fetchData().then(e => setVehicles(e as Array<Vehicle>))
  }, []);
  
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
