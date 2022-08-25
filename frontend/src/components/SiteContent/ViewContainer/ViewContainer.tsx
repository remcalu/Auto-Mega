import { Box, Typography } from '@mui/material';
import { useAppSelector } from '../../../redux/hooks';
import { selectNumVehicles } from '../../../redux/reducers/vehiclesSlice';
import { isGallery } from '../../../redux/reducers/viewsSlice';
import GalleryBar from './GalleryView/GalleryBar/GalleryBar';
import GalleryView from './GalleryView/GalleryView';
import ListView from './ListView/ListView';
import './ViewContainer.css';

export default function ViewContainer() {
  const isGallerySelected = useAppSelector(isGallery);
  const numVehicles = useAppSelector(selectNumVehicles);

  return (
    <Box className="ViewContainer">
      <GalleryBar/>
      {numVehicles !== 0 ?
        <Box>
          {isGallerySelected ? 
            <GalleryView/> : 
            <ListView/>
          } 
        </Box> :
        <Typography sx={{marginTop: "33vh"}} color="black" variant="h3">No results found</Typography>
      }
    </Box>
  );
}