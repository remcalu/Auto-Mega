import { Box, Checkbox, FormControl, InputLabel, ListItemText, MenuItem, OutlinedInput, Select, SelectChangeEvent } from '@mui/material';
import { useState } from 'react';

interface IProps {
  selectOptions: Array<string>,
  label: string
}


export default function MultiSelect(props: IProps) {
  const [selectOptionValue, setSelectOptionValue] = useState<string[]>([]);

  const handleChange = (event: SelectChangeEvent<typeof selectOptionValue>) => {
    const {
      target: { value },
    } = event;
    setSelectOptionValue(
      // On autofill we get a stringified value.
      typeof value === 'string' ? value.split(',') : value,
    );
  };

  return (
    <Box>
      <FormControl sx={{ width: '100%' }}>
        <InputLabel>{props.label}</InputLabel>
        <Select
          labelId="demo-multiple-checkbox-label"
          multiple
          value={selectOptionValue}
          onChange={handleChange}
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