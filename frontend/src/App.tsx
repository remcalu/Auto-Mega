import './App.css';
import { Box, createTheme, StyledEngineProvider, ThemeProvider } from '@mui/material';
import { cyan } from '@mui/material/colors';
import SiteHeader from './components/SiteHeader/SiteHeader';
import SiteContent from './components/SiteContent/SiteContent';
import { useAppDispatch } from './redux/hooks';
import { fetchNumVehicles, fetchVehicles } from './redux/reducers/vehiclesSlice';
import { clearAllVehicles } from './util/VehicleService';

const theme = createTheme({
  palette: {
    primary: { 
      main: cyan[700],
      light: cyan[600],
      dark: cyan[800]
    },
  }
});

export default function App() {
  const dispatch = useAppDispatch();
  clearAllVehicles().then(() => {
    fetchNumVehicles(dispatch);
    fetchVehicles(dispatch);
  });
  
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
