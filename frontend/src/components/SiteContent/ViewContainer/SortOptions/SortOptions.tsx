import { ArrowDownward, ArrowUpward } from '@mui/icons-material';
import { Box, Button, Menu, MenuItem, Stack } from '@mui/material';
import { useCallback, useState } from 'react';
import { useAppDispatch, useAppSelector } from '../../../../redux/hooks';
import { sortMileageAscending, sortMileageDescending, sortPriceAscending, sortPriceDescending, sortYearAscending, sortYearDescending } from '../../../../redux/reducers/vehiclesSlice';
import { RootState } from '../../../../redux/store';
import Vehicle from '../../../../types/Vehicle';

export default function SortOptions() {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const dispatch = useAppDispatch();
  const vehicles = useAppSelector((state: RootState) => state.vehicles.vehicles);

  const handleSortPriceAscending = useCallback((vehiclesPassed: Vehicle[]) => {
    dispatch(sortPriceAscending(vehiclesPassed));
    setAnchorEl(null);
  }, [dispatch]);

  const handleSortPriceDescending = useCallback((vehiclesPassed: Vehicle[]) => {
    dispatch(sortPriceDescending(vehiclesPassed));
    setAnchorEl(null);
  }, [dispatch]);

  const handleSortMileageAscending = useCallback((vehiclesPassed: Vehicle[]) => {
    dispatch(sortMileageAscending(vehiclesPassed));
    setAnchorEl(null);
  }, [dispatch]);

  const handleSortMileageDescending = useCallback((vehiclesPassed: Vehicle[]) => {
    dispatch(sortMileageDescending(vehiclesPassed));
    setAnchorEl(null);
  }, [dispatch]);

  const handleSortYearAscending = useCallback((vehiclesPassed: Vehicle[]) => {
    dispatch(sortYearAscending(vehiclesPassed));
    setAnchorEl(null);
  }, [dispatch]);

  const handleSortYearDescending = useCallback((vehiclesPassed: Vehicle[]) => {
    dispatch(sortYearDescending(vehiclesPassed));
    setAnchorEl(null);
  }, [dispatch]);

  return (
    <Box>
      <Stack direction="row" alignItems="center">
        <Button
          id="basic-button"
          variant="contained"
          aria-controls={open ? 'basic-menu' : undefined}
          aria-haspopup="true"
          aria-expanded={open ? 'true' : undefined}
          onClick={handleClick}
        >
          Sort by
        </Button>
        <Menu
          id="basic-menu"
          anchorEl={anchorEl}
          open={open}
          onClose={handleClose}
          MenuListProps={{
            'aria-labelledby': 'basic-button',
          }}
        >
          <MenuItem onClick={() => handleSortPriceAscending(vehicles)}><ArrowUpward/>Price Ascending</MenuItem>
          <MenuItem onClick={() => handleSortPriceDescending(vehicles)}><ArrowDownward/>Price Descending</MenuItem>
          <MenuItem onClick={() => handleSortMileageAscending(vehicles)}><ArrowUpward/>Mileage Ascending</MenuItem>
          <MenuItem onClick={() => handleSortMileageDescending(vehicles)}><ArrowDownward/>Mileage Descending</MenuItem>
          <MenuItem onClick={() => handleSortYearAscending(vehicles)}><ArrowUpward/>Year Ascending</MenuItem>
          <MenuItem onClick={() => handleSortYearDescending(vehicles)}><ArrowDownward/>Year Descending</MenuItem>
        </Menu>
      </Stack>
    </Box>
  );
}