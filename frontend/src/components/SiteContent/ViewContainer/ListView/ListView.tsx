import { Box, Pagination, Stack, Typography } from '@mui/material';
import { useAppSelector } from '../../../../redux/hooks';
import { RootState } from '../../../../redux/store';
import VehicleListCard from './VehicleListCard/VehicleListCard';
import "./ListView.css"
import { useState } from 'react';
import { calcShownVehicles, calcNumPages, scrollTop } from '../../../../util/VehicleCardUtil';

export default function ListView() {
  const vehicles = useAppSelector((state: RootState) => state.vehicles.vehicles);
  
  const [page, setPage] = useState(1);
  const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
    scrollTop();
  };
  
  return (
    <Box className="ListView-body">
      {vehicles.length > 0 &&
        <Box>
          <Stack direction="row" alignItems="center" justifyContent="space-around" spacing={4}>
            <Box width="50px">
              <Typography variant="h5" align='left' color="black">Price</Typography>
            </Box>
            <Box width="80px">
              <Typography variant="h5" align='left' color="black">Brand</Typography>
            </Box>
            <Box width="80px">
              <Typography variant="h5" align='left' color="black">Model</Typography>
            </Box>
            <Box width="70px">
              <Typography variant="h5" align='left' color="black">Mileage</Typography>
            </Box>
            <Box width="50px">
              <Typography variant="h5" align='left' color="black">Year</Typography>
            </Box>
            <Box width="50px">
              <Typography variant="h5" align='left' color="black">Vendor</Typography>
            </Box>
          </Stack>
          <Stack>
            {calcShownVehicles(vehicles, page).length !== 0 && calcShownVehicles(vehicles, page).map((vehicle, index) => 
              <VehicleListCard key={index} vehicle={vehicle}/>
            )}
          </Stack>
          <Box marginTop={2} display="flex" justifyContent="center">
            {vehicles.length !== 0 && <Pagination count={calcNumPages(vehicles)} page={page} onChange={handleChange} />}
          </Box>
        </Box>
      }
    </Box>
  );
}