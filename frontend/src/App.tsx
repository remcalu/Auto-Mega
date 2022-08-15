import React from 'react';
import logo from './logo.svg';
import VehicleGalleryView from './components/VehicleGalleryView/VehicleGalleryView';
import './App.css';
import RefetchVehiclesButton from './components/RefetchVehiclesButton/RefetchVehiclesButton';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <RefetchVehiclesButton/>
        <VehicleGalleryView/>
      </header>
    </div>
  );
}

export default App;
