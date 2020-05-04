import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import moment from 'moment';
import './App.css';
import LineGraph from "./components/LineGraph/LineGraph";
import {fetchHealthMetrics} from "./actions/actions";

const mapStateToProps = state => ({
    data: state.health.metrics.map((m, i) => ({ value: parseFloat(m.value), timestamp: moment().subtract(i, 'minutes').format('hh:mm') })),
});

const mapDispatchToProps = dispatch => ({
    fetchHeartRate: () => dispatch(fetchHealthMetrics())
});

function App(props) {

    const [healthMetrics, setHealthMetrics] = useState([]);

    useEffect(() => {
        const interval = setInterval(async () => {
            await props.fetchHeartRate();
            setHealthMetrics([{ name: 'BPM', data: props.data }])
        }, 5000);
        return () => clearInterval(interval);
    }, []);

    return (
        <div className="App">
            <header className="App-header">
                <p>
                    View latest Health Metrics
                </p>
                <LineGraph series={healthMetrics} />
            </header>
        </div>
    );
}

export default connect(mapStateToProps, mapDispatchToProps)(App);
