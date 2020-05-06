import React, { Component } from 'react'
import takeRight from 'lodash/takeRight';
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
        )
    }
}