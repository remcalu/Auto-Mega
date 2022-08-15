import React, { useEffect, useState } from 'react';
import './VehicleGalleryView.css';
import { getAllVehicles } from '../../util/VehicleService';
import Vehicle from '../../types/Vehicle';
import VehicleItem from '../VehicleItem/VehicleItem';
import { Grid } from '@mui/material';

function VehicleGalleryView() {
  const [vehicles, setVehicles] = useState<Vehicle[]>([]);
  useEffect(() => {
    async function fetchData() {
      return getAllVehicles();
    }
    fetchData().then(e => setVehicles(e as Array<Vehicle>))
  }, []);
  
  return (
    <div className="VehicleGalleryView-body">
      <Grid className="VehicleGalleryView-grid" container spacing={2}>
        {vehicles.map((vehicle, index) => 
        <Grid item xs="auto" zeroMinWidth key={index}>
          <VehicleItem vehicle={vehicle}/>
        </Grid>
        )}
      </Grid>
    </div>
  );
}

export default VehicleGalleryView;
