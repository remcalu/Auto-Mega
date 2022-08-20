import { Box, Typography } from '@mui/material';
import { Stack } from '@mui/system';
import SortOptions from '../../SortOptions/SortOptions';
import ViewOptions from '../../ViewOptions/ViewOptions';
import './GalleryBar.css';


export default function GalleryBar() {

  return (
    <Box>
      <Stack direction="row" justifyContent="space-between" alignItems="center">
        <Typography color="black">812 cars for sale</Typography>
        <ViewOptions/>
        <SortOptions/>
      </Stack>
    </Box>
  );
}