import { Box, Stack, TextField, Typography } from '@mui/material';
import { useAppDispatch } from '../../../../redux/hooks';
import { setDistance, setPostalCode } from '../../../../redux/reducers/filterFormSlice';
import { DISTANCE_SLIDER_MARKS, INITIAL_DISTANCE } from '../../../../util/Constants';
import InputSlider from '../InputSlider/InputSlider';

export default function LocationFilter() {
  const dispatch = useAppDispatch();
  
  function updateState(e: any) {
    console.log(e.target.value)
    dispatch(setPostalCode(e.target.value));
  }

  return (
    <Box>
      <Stack spacing={2}>
        <Typography align="left" color="black" variant="h5">Location</Typography>
        <TextField onChange={updateState} label="Postal code" variant="outlined" />
        <InputSlider 
          updateStateReducer={setDistance}
          label={'Max distance from postal code'} 
          defaultValue={INITIAL_DISTANCE} 
          step={10} 
          marks={DISTANCE_SLIDER_MARKS} 
          min={10} 
          max={500}
        />
      </Stack>
    </Box>
  );
}