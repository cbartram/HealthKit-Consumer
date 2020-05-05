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
        let ws = new WebSocket('ws://localhost:8080/ws/metrics');
        // ws.addEventListener('open', () => {
        //     let value = window.prompt('What is the heart rate value?');
        //     ws.send("{ \"metric\": \"heartRate\", \"value\": \"" +  value + "\"}");
        // });

        ws.onopen = () => {
            console.log('[INFO] Connected...')
        };

        ws.onclose = () => {
            console.log('[INFO] Disconnected...')
        };

        ws.onerror = err => {
            console.error(
                "Socket encountered error: ",
                err.message,
                "Closing socket"
            );

            ws.close();
        };

        ws.onmessage = ({ data }) => {
         const d = JSON.parse(data);
         console.log('[INFO] Received event: ', d);
        };
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
