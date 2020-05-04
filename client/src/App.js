import React from 'react';
import { connect } from 'react-redux';
import moment from 'moment';
import './App.css';
import LineGraph from "./components/LineGraph/LineGraph";

const mapStateToProps = state => ({
  data: state.health.metrics.map((m, i) => ({ value: parseFloat(m.value), timestamp: moment().subtract(i, 'minutes').format('hh:mm') })),
});

function App(props) {
  return (
    <div className="App">
      <header className="App-header">
        <p>
          View latest Health Metrics
        </p>
        <LineGraph series={[{ name: 'BPM', data: props.data }]} />
      </header>
    </div>
  );
}

export default connect(mapStateToProps)(App);
