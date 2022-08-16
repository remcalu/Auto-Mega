import { Box, Card, CardContent, Paper } from '@mui/material';
import TopBar from '../TopBar/TopBar';
import VehicleGalleryView from '../VehicleGalleryView/VehicleGalleryView';
import './FilterOptionsContainer.css';

export default function FilterOptionsContainer() {
  return (
    <Box className="FilterOptionsContainer">
      <Box
        sx={{
          width: 300,
          height: 300,
          backgroundColor: 'primary.dark',
          '&:hover': {
            backgroundColor: 'primary.main',
            opacity: [0.9, 0.8, 0.7],
          },
        }}
      />
    </Box>
  );
}