import './RefetchVehiclesButton.css';
import { Refresh } from '@mui/icons-material';
import { useState } from 'react';
import { refetchAllVehicles } from '../../util/VehicleService';
import { CustomLoadingButton } from '../StyledMuiComponents/CustomButtons/CustomLoadingButton/CustomLoadingButton';
import { Box } from '@mui/material';

export default function RefetchVehiclesButton() {
  const [fetching, setfetching] = useState<boolean>();
  
  const handleClick = async() => {
    setfetching(true);
    await refetchAllVehicles().then(() => setfetching(false));
  }

  return (
    <Box>
      <CustomLoadingButton
        onClick={handleClick}
        loading={fetching}
        loadingPosition="start"
        variant="contained"
        startIcon={<Refresh/>}
      >
        {fetching ? "Fetch in progress" : "Refetch vehicles"}
      </CustomLoadingButton>
    </Box>
  );
}