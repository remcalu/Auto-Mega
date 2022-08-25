import { Box, Stack, Typography } from '@mui/material';
import { useAppSelector } from '../../../../redux/hooks';
import { RootState } from '../../../../redux/store';
import VehicleListCard from './VehicleListCard/VehicleListCard';
import "./ListView.css"

export default function ListView() {
  const vehicles = useAppSelector((state: RootState) => state.vehicles.vehicles);
  
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
            {vehicles.map((vehicle, index) => 
              <VehicleListCard key={index} vehicle={vehicle}/>
            )}
          </Stack>
        </Box>
      }
    </Box>
  );
}