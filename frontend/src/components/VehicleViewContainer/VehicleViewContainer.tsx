import { Box } from '@mui/material';
import TopBar from '../TopBar/TopBar';
import VehicleGalleryView from '../VehicleGalleryView/VehicleGalleryView';
import './VehicleViewContainer.css';

export default function VehicleViewContainer() {
  return (
    <Box className="VehicleViewContainer">
      <TopBar/>
      <VehicleGalleryView/>
    </Box>
  );
}