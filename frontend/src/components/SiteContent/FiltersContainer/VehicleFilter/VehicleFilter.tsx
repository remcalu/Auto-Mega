import { Box, Stack, Typography } from '@mui/material';
import { setMaxMileage, setMaxPrice, setMinYear, setTransmission, setVehicles, setVendorType } from '../../../../redux/reducers/filterFormSlice';
import { ALL_SUPPORTED_VEHICLES, TRANSMISSION_TYPES, VENDOR_TYPES, PRICE_SLIDER_MARKS, MILEAGE_SLIDER_MARKS, MIN_YEAR_MARKS, INITIAL_MAX_PRICE, INITIAL_MAX_MILEAGE, INITIAL_MIN_YEAR, INITIAL_VENDOR_TYPES, INITIAL_TRANSMISSION_TYPES, INITIAL_ALL_SUPPORTED_VEHICLES } from '../../../../util/Constants';
import InputSlider from '../InputSlider/InputSlider';
import MultiSelect from './MultiSelect/MultiSelect';

export default function VehicleFilter() {
  return (
    <Box>
      <Stack spacing={2}>
        <Typography align="left" color="black" variant="h5">Vehicle specs</Typography>
        <MultiSelect 
          updateStateReducer={setVehicles}
          defaultValues={INITIAL_ALL_SUPPORTED_VEHICLES}
          selectOptions={ALL_SUPPORTED_VEHICLES} 
          label={"Vehicles"}        
        />
        <MultiSelect 
          updateStateReducer={setTransmission}
          defaultValues={INITIAL_TRANSMISSION_TYPES}
          selectOptions={TRANSMISSION_TYPES} 
          label={"Transmission"}        
        />
        <MultiSelect 
          updateStateReducer={setVendorType}
          defaultValues={INITIAL_VENDOR_TYPES}
          selectOptions={VENDOR_TYPES} 
          label={"Vendor type"}        
        />
        <InputSlider 
          updateStateReducer={setMaxPrice}
          label={'Max price'} 
          defaultValue={INITIAL_MAX_PRICE} 
          step={200} 
          marks={PRICE_SLIDER_MARKS} 
          min={1000} 
          max={50000}
        />
        <InputSlider 
          updateStateReducer={setMaxMileage}
          label={'Max mileage'} 
          defaultValue={INITIAL_MAX_MILEAGE} 
          step={2500} 
          marks={MILEAGE_SLIDER_MARKS} 
          min={10000} 
          max={500000}
        />
        <InputSlider 
          updateStateReducer={setMinYear}
          label={'Min year'} 
          defaultValue={INITIAL_MIN_YEAR} 
          step={1} 
          marks={MIN_YEAR_MARKS} 
          min={1980} 
          max={2023}
        />
      </Stack>
    </Box>
  );
}