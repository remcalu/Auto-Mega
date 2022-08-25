import { Box, Divider, Stack } from '@mui/material';
import FiltersContainer from './FiltersContainer/FiltersContainer';
import ViewContainer from './ViewContainer/ViewContainer';
import "./SiteContent.css"

export default function SiteContent() {
  return (
    <Box className="SiteContent">
      <Stack direction="row" divider={<Divider orientation="vertical" flexItem />}>
        <FiltersContainer/>
        <ViewContainer/>
      </Stack>
    </Box>
  );
}