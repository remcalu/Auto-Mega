import { Box, Typography } from '@mui/material';
import { Stack } from '@mui/system';
import { useAppSelector } from '../../../../../redux/hooks';
import { selectNumVehicles } from '../../../../../redux/reducers/vehiclesSlice';
import SortOptions from '../../SortOptions/SortOptions';
import ViewOptions from '../../ViewOptions/ViewOptions';
import './GalleryBar.css';

export default function GalleryBar() {
  const numVehicles = useAppSelector(selectNumVehicles);

  return (
    <Box>
      <Stack direction="row" justifyContent="space-between" alignItems="center">
        <Typography variant="h6" color="black">{numVehicles} cars for sale</Typography>
        <ViewOptions/>
        <SortOptions/>
      </Stack>
    </Box>
  );
}