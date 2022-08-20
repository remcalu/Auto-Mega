import { Box, Stack, Typography } from '@mui/material';
import { ALL_SUPPORTED_VEHICLES, TRANSMISSION_TYPES, VENDOR_TYPES, PRICE_SLIDER_MARKS, MILEAGE_SLIDER_MARKS, MIN_YEAR_MARKS } from '../../../../util/Constants';
import InputSlider from '../InputSlider/InputSlider';
import MultiSelect from './MultiSelect/MultiSelect';

export default function VehicleFilter() {
  return (
    <Box>
      <Stack spacing={2}>
        <Typography align="left" color="black" variant="h5">Vehicle specs</Typography>
        <MultiSelect 
          selectOptions={ALL_SUPPORTED_VEHICLES} 
          label={"Vehicles"}        
        />
        <MultiSelect 
          selectOptions={TRANSMISSION_TYPES} 
          label={"Transmission"}        
        />
        <MultiSelect 
          selectOptions={VENDOR_TYPES} 
          label={"Vendor type"}        
        />
        <InputSlider 
          label={'Max price'} 
          defaultValue={8000} 
          step={200} 
          marks={PRICE_SLIDER_MARKS} 
          min={1000} 
          max={50000}
        />
        <InputSlider 
          label={'Max mileage'} 
          defaultValue={150000} 
          step={2500} 
          marks={MILEAGE_SLIDER_MARKS} 
          min={10000} 
          max={500000}
        />
        <InputSlider 
          label={'Min year'} 
          defaultValue={2010} 
          step={1} 
          marks={MIN_YEAR_MARKS} 
          min={1980} 
          max={2023}
        />
      </Stack>
    </Box>
  );
}