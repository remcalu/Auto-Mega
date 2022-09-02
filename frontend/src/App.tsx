import './App.css';
import { Box, createTheme, StyledEngineProvider, ThemeProvider } from '@mui/material';
import { cyan } from '@mui/material/colors';
import SiteHeader from './components/SiteHeader/SiteHeader';
import SiteContent from './components/SiteContent/SiteContent';
import { useAppDispatch } from './redux/hooks';
import { fetchNumVehicles, fetchVehicles } from './redux/reducers/vehiclesSlice';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

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
  fetchNumVehicles(dispatch);
  fetchVehicles(dispatch);
  
  return (
    <Box className="App">
      <StyledEngineProvider injectFirst>
        <ThemeProvider theme={theme}>
          <ToastContainer
            position="top-center"
            autoClose={5000}
            hideProgressBar={false}
            newestOnTop={false}
            closeOnClick
            rtl={false}
            theme="colored"
            draggable
          />
          <SiteHeader/>
          <SiteContent/>
        </ThemeProvider>
      </StyledEngineProvider>
    </Box>
  );
}
