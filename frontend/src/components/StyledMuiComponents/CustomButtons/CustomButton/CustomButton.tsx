import styled from "@emotion/styled";
import { Button, ButtonProps } from "@mui/material";
import { cyan, grey } from "@mui/material/colors";

export const CustomButton = styled(Button)<ButtonProps>(() => ({
  color: "#FFFFFF",
  backgroundColor: cyan[800],
  ':hover': {
    backgroundColor: cyan[700],
  },
  ':disabled': {
    color: grey[300],
    backgroundColor: cyan[600],
  },
}));

export const CustomButtonBorderless = styled(Button)<ButtonProps>(() => ({
  color: cyan[800],
  ':disabled': {
    color: grey[300],
    backgroundColor: cyan[600],
  },
}));