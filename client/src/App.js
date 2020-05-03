import React from 'react';
import './App.css';
import LineGraph from "./components/LineGraph/LineGraph";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <p>
          View latest Health Metrics
        </p>
        <LineGraph />
      </header>
    </div>
  );
}

export default App;
