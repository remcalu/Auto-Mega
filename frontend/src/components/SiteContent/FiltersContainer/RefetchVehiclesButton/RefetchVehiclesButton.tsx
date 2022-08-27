import './RefetchVehiclesButton.css';
import { Search } from '@mui/icons-material';
import { useState } from 'react';
import { refetchAllVehicles } from '../../../../util/VehicleService';
import { CustomLoadingButton } from '../../../StyledMuiComponents/CustomButtons/CustomLoadingButton/CustomLoadingButton';
import { Box } from '@mui/material';
import { useAppDispatch, useAppSelector } from '../../../../redux/hooks';
import { getDistance, getMaxMileage, getMaxPrice, getMinYear, getPostalCode, getTransmission, getVehicles, getVendorType } from '../../../../redux/reducers/filterFormSlice';
import { fetchNumVehicles, fetchVehicles } from '../../../../redux/reducers/vehiclesSlice';
import FetchOptions from '../../../../types/FetchOptions';
import { notifyError, notifySuccess } from '../../../../util/VehicleCardUtil';

export default function RefetchVehiclesButton() {
  const dispatch = useAppDispatch();

  const [fetching, setfetching] = useState<boolean>();

  let fetchOptions: FetchOptions = {
    postalCode: useAppSelector(getPostalCode),
    distance: useAppSelector(getDistance),
    vehicles: useAppSelector(getVehicles),
    transmission: useAppSelector(getTransmission),
    vendorType: useAppSelector(getVendorType),
    maxPrice: useAppSelector(getMaxPrice),
    maxMileage: useAppSelector(getMaxMileage),
    minYear: useAppSelector(getMinYear),
  };
  
  const handleClick = async(fetchOptions: FetchOptions) => {
    setfetching(true);
    await refetchAllVehicles(fetchOptions).then(e => {
      setfetching(false);
      if (e == "Success") {
        notifySuccess(e);
        window.scrollTo({top: 0, left: 0, behavior: 'smooth'});
      } else {
        let errors = e.split(", ");
        for (let i = 0; i < errors.length; i++) {
          if (i != 0) {
            errors[i] = "ERROR: " + errors[i];
          }
          notifyError(errors[i]);
        }
      }
    });
    fetchNumVehicles(dispatch);
    fetchVehicles(dispatch);
    
  }

  
  return (
    <Box>
      <CustomLoadingButton
        type='submit'
        onClick={() => handleClick(fetchOptions)}
        loading={fetching}
        loadingPosition="start"
        variant="contained"
        startIcon={<Search/>}
      >
        {fetching ? "Searching..." : "Search for ads"}
      </CustomLoadingButton>
    </Box>
  );
}