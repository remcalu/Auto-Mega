import './RefetchVehiclesButton.css';
import { LoadingButton, LoadingButtonProps } from '@mui/lab';
import { Refresh } from '@mui/icons-material';
import { styled } from '@mui/material';
import { useState } from 'react';
import { refetchAllVehicles } from '../../util/VehicleService';
import { blue, grey } from '@material-ui/core/colors';

const StyledLoadingButton = styled(LoadingButton)<LoadingButtonProps>(() => ({
  ':disabled': {
    color: grey[300],
    backgroundColor: blue[900],
  },
}));

export default function RefetchVehiclesButton() {
  const [fetching, setfetching] = useState<boolean>();
  
  async function fetchData() {
    return refetchAllVehicles();
  }

  const handleClick = async() => {
    setfetching(true);
    console.log("TEST");
    await fetchData().then(() => setfetching(false));
  }

  return (
    <div>
      {
        <StyledLoadingButton
          onClick={handleClick}
          loading={fetching}
          loadingPosition="start"
          variant="contained"
          startIcon={<Refresh/>}
        >
          {fetching ? "Fetch in progress" : "Refetch vehicles"}
        </StyledLoadingButton>
      }
    </div>
  );
}