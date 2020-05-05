import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import moment from 'moment';
import './App.css';
import LineGraph from "./components/LineGraph/LineGraph";
import { updateHealthMetrics} from "./actions/actions";

const mapStateToProps = state => ({
    healthMetrics: state.health.metrics,
});

const mapDispatchToProps = dispatch => ({
    updateHealthMetrics: (payload) => dispatch(updateHealthMetrics(payload))
});

function App(props) {
    useEffect(() => {
        let ws = new WebSocket('ws://localhost:8080/ws/metrics');

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
         props.updateHealthMetrics(d);
        };
    }, []);

    return (
        <div className="App">
            <header className="App-header">
                <p>
                    View latest Health Metrics
                </p>
                <LineGraph series={props.healthMetrics} />
            </header>
        </div>
    );
}

export default connect(mapStateToProps, mapDispatchToProps)(App);
