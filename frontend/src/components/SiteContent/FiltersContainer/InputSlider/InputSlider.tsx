import { Mark } from '@material-ui/core';
import { ActionCreatorWithPayload } from '@reduxjs/toolkit';
import { Box,  Slider,  Stack, Typography } from '@mui/material';
import { useAppDispatch } from '../../../../redux/hooks';

interface IProps {
  label: string,
  defaultValue: number,
  step: number,
  marks: Mark[],
  min: number,
  max: number,
  updateStateReducer: ActionCreatorWithPayload<number, string>,
}


export default function InputSlider(props: IProps) {
  const dispatch = useAppDispatch();
  
  function updateState(e: any) {
    dispatch(props.updateStateReducer(e.target.value));
  }

  return (
    <Box>
      <Stack>
        <Typography align='left' gutterBottom color="black">
          {props.label}
        </Typography>
        <Slider
          onChange={updateState}
          aria-label="Custom marks"
          defaultValue={props.defaultValue}
          step={props.step}
          valueLabelDisplay="auto"
          marks={props.marks}
          min={props.min}
          max={props.max}
        />
      </Stack>
    </Box>
  );
}