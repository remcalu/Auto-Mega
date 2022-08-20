import { ArrowDownward, ArrowUpward } from '@mui/icons-material';
import { Button, Menu, MenuItem, Stack } from '@mui/material';
import { useState } from 'react';

export default function SortOptions() {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  
  return (
    <div>
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
          <MenuItem onClick={handleClose}><ArrowUpward/>Price Ascending</MenuItem>
          <MenuItem onClick={handleClose}><ArrowDownward/>Price Descending</MenuItem>
          <MenuItem onClick={handleClose}><ArrowUpward/>Mileage Ascending</MenuItem>
          <MenuItem onClick={handleClose}><ArrowDownward/>Mileage Descending</MenuItem>
          <MenuItem onClick={handleClose}><ArrowUpward/>Year Ascending</MenuItem>
          <MenuItem onClick={handleClose}><ArrowDownward/>Year Descending</MenuItem>
        </Menu>
      </Stack>
    </div>
  );
}