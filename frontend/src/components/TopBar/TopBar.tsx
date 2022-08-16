import { Box, Typography } from '@mui/material';
import { Stack } from '@mui/system';
import VehicleSortOptions from '../VehicleSortOptions/VehicleSortOptions';
import VehicleViewOptions from '../VehicleViewOptions/VehicleViewOptions';
import './TopBar.css';


export default function TopBar() {

  return (
    <Box>
      <Stack direction="row" justifyContent="space-between" alignItems="center">
        <Typography color="black">812 cars for sale</Typography>
        <VehicleViewOptions/>
        <VehicleSortOptions/>
      </Stack>
    </Box>
  );
}