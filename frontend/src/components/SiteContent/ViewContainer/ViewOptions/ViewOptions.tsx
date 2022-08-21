import { ViewList, ViewModule } from '@mui/icons-material';
import { ToggleButtonGroup} from '@mui/material';
import { useState } from 'react';
import { useAppDispatch } from '../../../../redux/hooks';
import { setIsGallery } from '../../../../redux/reducers/viewsSlice';
import { CustomToggleButton } from '../../../StyledMuiComponents/CustomButtons/CustomToggleButton/CustomToggleButton';
import './ViewOptions.css';

export default function ViewOptions() {
  const [alignment, setAlignment] = useState('left');

  const dispatch = useAppDispatch();

  const handleAlignment = (
    _event: React.MouseEvent<HTMLElement>,
    newAlignment: string | null,
  ) => {
    if (newAlignment !== null) {
      setAlignment(newAlignment);
      if (newAlignment === "left") {
        dispatch(setIsGallery(true))
      } else {
        dispatch(setIsGallery(false))
      }
    }
  };

  
  return (
    <div>
      <ToggleButtonGroup
        value={alignment}
        exclusive
        onChange={handleAlignment}
        aria-label="text alignment"
      >
        <CustomToggleButton aria-label="left" value="left"> <ViewModule/> Gallery </CustomToggleButton>
        <CustomToggleButton aria-label="right" value="right"> <ViewList/> List </CustomToggleButton>
      </ToggleButtonGroup>
    </div>
  );
}