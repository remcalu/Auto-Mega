import styled from "@emotion/styled";
import { cyan } from "@material-ui/core/colors";
import { ToggleButton, ToggleButtonProps } from "@mui/lab";

export const CustomToggleButton = styled(ToggleButton)<ToggleButtonProps>(() => ({
  color: "#FFFFFF",
  backgroundColor: cyan[800],
  ':hover': {
    backgroundColor: cyan[700],
  },

  '&.Mui-selected': {
    color: "#FFFFFF",
    backgroundColor: cyan[700],
    ':hover': {
      backgroundColor: cyan[700],
    },
  }
}));