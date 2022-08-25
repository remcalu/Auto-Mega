import { Box, Checkbox, FormControl, InputLabel, ListItemText, MenuItem, OutlinedInput, Select, SelectChangeEvent } from '@mui/material';
import { ActionCreatorWithPayload } from '@reduxjs/toolkit';
import { useState } from 'react';
import { useAppDispatch } from '../../../../../redux/hooks';

interface IProps {
  selectOptions: Array<string>,
  label: string,
  updateStateReducer: ActionCreatorWithPayload<string[]>,
  defaultValues: Array<string>
}


export default function MultiSelect(props: IProps) {
  const [selectOptionValue, setSelectOptionValue] = useState<string[]>([]);
  const dispatch = useAppDispatch();
  
  function updateState(e: SelectChangeEvent<typeof selectOptionValue>) {
    const value = e.target.value;
    setSelectOptionValue(
      // On autofill we get a stringified value.
      typeof value === 'string' ? value.split(',') : value,
    );
    if(Array.isArray(value)) {
      dispatch(props.updateStateReducer(value));
    }
  }

  return (
    <Box>
      <FormControl sx={{ width: '100%' }}>
        <InputLabel>{props.label}</InputLabel>
          <Select
            labelId="demo-multiple-checkbox-label"
            multiple
            defaultValue={props.defaultValues}
            value={selectOptionValue}
            onChange={updateState}
            input={<OutlinedInput label={props.label} />}
            renderValue={(selected) => selected.join(', ')}
          >
          {props.selectOptions.sort().map((selectOption) => (
            <MenuItem key={selectOption} value={selectOption}>
              <Checkbox checked={selectOptionValue.indexOf(selectOption) > -1} />
              <ListItemText primary={selectOption} />
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </Box>
  );
}