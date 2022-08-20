import { Box } from '@mui/material';
import GalleryBar from './GalleryView/GalleryBar/GalleryBar';
import GalleryView from './GalleryView/GalleryView';
import './ViewContainer.css';

export default function ViewContainer() {
  return (
    <Box className="ViewContainer">
      <GalleryBar/>
      <GalleryView/>
    </Box>
  );
}