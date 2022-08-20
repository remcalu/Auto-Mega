import { Mark } from '@material-ui/core';
import { Box,  Slider,  Stack, Typography } from '@mui/material';

interface IProps {
  label: string,
  defaultValue: number,
  step: number,
  marks: Mark[],
  min: number,
  max: number,
}


export default function InputSlider(props: IProps) {
  return (
    <Box>
      <Stack>
        <Typography align='left' gutterBottom color="black">
          {props.label}
        </Typography>
        <Slider
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