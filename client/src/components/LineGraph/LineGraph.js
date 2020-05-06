import React, { Component } from 'react'
import takeRight from 'lodash/takeRight';
import { Card } from 'semantic-ui-react';
import {
    LineChart,
    Tooltip,
    Line,
    XAxis,
    YAxis,
    CartesianGrid,
    Legend
} from 'recharts';

export default class LineGraph extends Component {
    render() {
        return (
                <Card>
                    <Card.Content>
                        <Card.Header>Heart Rates in BPM</Card.Header>
                        <Card.Meta>Latest heart rate metrics</Card.Meta>
                        <LineChart width={900} height={300} backgroundColor="#00ff00">
                            {this.props.series.map(s => (
                                <Line dataKey="value" stroke="#5469D4" strokeWidth={2} data={takeRight(s.data, 15)} name={s.name} key={s.name} />
                            ))}
                            <Legend wrapperStyle={{ fontFamily: 'Roboto, sans-serif', color: 'black', fontSize: 15 }} />
                            <Tooltip />
                            <CartesianGrid strokeDasharray="3 3" />
                            <XAxis dataKey="timestamp" tick={{ fontSize: 10 }} />
                            <YAxis tick={{ fontSize: 10 }} />
                        </LineChart>
                    </Card.Content>
                </Card>

        )
    }
}