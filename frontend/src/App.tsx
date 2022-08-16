import './App.css';
import { Box, Divider, Stack, StyledEngineProvider } from '@mui/material';
import VehicleViewContainer from './components/VehicleViewContainer/VehicleViewContainer';
import FilterOptionsContainer from './components/FilterOptionsContainer/FilterOptionsContainer';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <StyledEngineProvider injectFirst>
          <Box className="App-container">
            {/* <RefetchVehiclesButton/> */}
            <Divider orientation="horizontal"/>
            <Stack direction="row" divider={<Divider orientation="vertical" flexItem />}>
              <FilterOptionsContainer/>
              <VehicleViewContainer/>
            </Stack>
          </Box>
        </StyledEngineProvider>
      </header>
    </div>
  );
}

export default App;
