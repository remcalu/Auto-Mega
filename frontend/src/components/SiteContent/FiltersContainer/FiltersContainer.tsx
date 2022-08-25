import { Box, Stack } from '@mui/material';
import RefetchVehiclesButton from './RefetchVehiclesButton/RefetchVehiclesButton';
import './FiltersContainer.css';
import LocationFilter from './LocationFilter/LocationFilter';
import VehicleFilter from './VehicleFilter/VehicleFilter';

export default function FiltersContainer() {
  return (
    <Box className="FiltersContainer">
      <Stack spacing={5}>
        <LocationFilter/>
        <VehicleFilter/>
        <RefetchVehiclesButton/>
      </Stack>
    </Box>
  );
}