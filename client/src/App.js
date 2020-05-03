import React from 'react';
import { connect } from 'react-redux';
import './App.css';
import LineGraph from "./components/LineGraph/LineGraph";

const mapStateToProps = state => ({
  data: state.health.metrics.map(m => parseFloat(m.value)),
});

function App(props) {
  return (
    <div className="App">
      <header className="App-header">
        <p>
          View latest Health Metrics
        </p>
        <LineGraph data={props.data} />
      </header>
    </div>
  );
}

export default connect(mapStateToProps)(App);
