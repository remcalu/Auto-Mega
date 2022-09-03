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
import { notifyError, notifySuccess, notifyWarn, scrollTop } from '../../../../util/VehicleCardUtil';

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
    let errorFound = false;
    
    refetchAllVehicles(fetchOptions).then(e => {
      setfetching(false);
      console.log(e.length);
      if (e.length === 0) {
        notifySuccess("Successfully loaded results");
        scrollTop();
      } else {
        errorFound = true;
        for (let error of e) {
          notifyError(error);
        }
      }

      fetchNumVehicles(dispatch);
      fetchVehicles(dispatch);
    });
    
    setTimeout(function () {
      if (!errorFound) {
        notifyWarn("Note that it may take a few minutes for the results to load, you can come back later if you'd like")
      }
    }, 1500);
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