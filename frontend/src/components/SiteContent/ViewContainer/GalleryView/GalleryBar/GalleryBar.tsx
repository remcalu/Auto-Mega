import { Box, Typography } from '@mui/material';
import { Stack } from '@mui/system';
import { useAppSelector } from '../../../../../redux/hooks';
import { RootState } from '../../../../../redux/store';
import SortOptions from '../../SortOptions/SortOptions';
import ViewOptions from '../../ViewOptions/ViewOptions';
import './GalleryBar.css';

export default function GalleryBar() {
  const numVehicles = useAppSelector((state: RootState) => state.vehicles.numVehicles);

  return (
    <Box>
      <Stack direction="row" justifyContent="space-between" alignItems="center">
        <Typography color="black">{numVehicles} cars for sale</Typography>
        <ViewOptions/>
        <SortOptions/>
      </Stack>
    </Box>
  );
}