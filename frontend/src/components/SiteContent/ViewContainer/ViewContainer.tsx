import { Box } from '@mui/material';
import { useAppSelector } from '../../../redux/hooks';
import { RootState } from '../../../redux/store';
import GalleryBar from './GalleryView/GalleryBar/GalleryBar';
import GalleryView from './GalleryView/GalleryView';
import ListView from './ListView/ListView';
import './ViewContainer.css';

export default function ViewContainer() {
  const isGallery = useAppSelector((state: RootState) => state.views.isGallery);

  return (
    <Box className="ViewContainer">
      <GalleryBar/>
      {isGallery ? 
        <GalleryView/> : 
        <ListView/>
      }
    </Box>
  );
}