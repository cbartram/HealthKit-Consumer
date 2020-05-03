import React, { Component } from 'react'
import moment from 'moment';
import takeRight from 'lodash/takeRight';
import {
    VictoryChart,
    VictoryAxis,
    VictoryLine,
    VictoryTheme
} from 'victory';


export default class LineGraph extends Component {

    render() {
        return (
            <VictoryChart
                theme={VictoryTheme.material}
                // domainPadding will add space to each side of VictoryBar to
                // prevent it from overlapping the axis
                domainPadding={20}
            >
                {/*<VictoryAxis*/}
                {/*    tickValues={[...takeRight(this.props.data.map((d, i) => i), 50)]}*/}
                {/*    tickFormat={[...takeRight(this.props.data.map((d, i) => moment().add(i, 'minutes').format('hh:mm:ss')), 50)]}*/}
                {/*/>   */}
                <VictoryAxis
                    dependentAxis
                />
                <VictoryLine
                    style={{
                        data: { stroke: "#c43a31" },
                        parent: { border: "1px solid #ccc"}
                    }}
                    data={[
                        ...takeRight(this.props.data.map((d, i) => ({ x: i, y: d})), 50)
                    ]}
                 />
            </VictoryChart>
        )
    }
}