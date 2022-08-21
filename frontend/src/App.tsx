import './App.css';
import { Box, createMuiTheme, StyledEngineProvider, ThemeProvider } from '@mui/material';
import { cyan } from '@mui/material/colors';
import SiteHeader from './components/SiteHeader/SiteHeader';
import SiteContent from './components/SiteContent/SiteContent';
import { useAppDispatch } from './redux/hooks';
import { fetchNumVehicles, fetchVehicles } from './redux/reducers/vehiclesSlice';

const theme = createMuiTheme({
  palette: {
    primary: { 
      main: cyan[700],
      light: cyan[600],
      dark: cyan[800]
    },
  }
});

export default function App() {
  fetchNumVehicles(useAppDispatch());
  fetchVehicles(useAppDispatch());
  
  return (
    <Box className="App">
      <StyledEngineProvider injectFirst>
        <ThemeProvider theme={theme}>
          <SiteHeader/>
          <SiteContent/>
        </ThemeProvider>
      </StyledEngineProvider>
    </Box>
  );
}
