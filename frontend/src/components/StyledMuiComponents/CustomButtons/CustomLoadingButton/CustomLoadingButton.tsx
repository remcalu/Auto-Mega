import styled from "@emotion/styled";
import { LoadingButton, LoadingButtonProps } from "@mui/lab";
import { cyan, grey } from "@mui/material/colors";

export const CustomLoadingButton = styled(LoadingButton)<LoadingButtonProps>(() => ({
  backgroundColor: cyan[800],
  ':hover': {
    backgroundColor: cyan[700],
  },
  ':disabled': {
    color: grey[300],
    backgroundColor: cyan[700],
  },
}));