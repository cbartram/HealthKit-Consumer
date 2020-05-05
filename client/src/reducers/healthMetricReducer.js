/**
 * Handles fetching health metrics from the API
 * @param state
 * @param action
 * @returns {{isFetching: boolean, error: null}}
 */
import {
    GET_HEALTH_METRICS_FAILURE,
    GET_HEALTH_METRICS_REQUEST,
    GET_HEALTH_METRICS_SUCCESS, UPDATE_HEALTH_METRICS
} from "../constants";
import moment from "moment";

export default (state = { isFetching: false, error: null, }, action) => {
    switch (action.type) {
        case GET_HEALTH_METRICS_REQUEST:
            return {
                ...state,
                error: null,
                isFetching: true,
                metrics: []
            };
        case GET_HEALTH_METRICS_SUCCESS:
            return {
                ...state,
                error: null,
                isFetching: false,
                metrics: [...state.metrics, {
                    name: 'BPM',
                    data: action.payload.metrics.map(({ metric, value }, i) => ({ name: metric, value: parseFloat(value), timestamp: moment().subtract(i, 'minutes').format('hh:mm') }))
                }],
            };
        case GET_HEALTH_METRICS_FAILURE:
            return {
                ...state,
                error: action.payload,
                isFetching: false,
            };
        case UPDATE_HEALTH_METRICS:
            // TODO will need to be updated with additional metrics are added
            return {
                ...state,
                metrics: [
                    // TODO should copy in metric here and filter for correct series to update in this case there is only ever 1 series (bpm)
                    {
                        name: 'BPM',
                        data: [
                            ...state.metrics[0].data,
                            {
                                name: 'bpm',
                                value: action.payload.value,
                                timestamp: moment().format('hh:mm')
                            }
                        ]
                    },
                ]
            };
        default:
            return {
                ...state
            }
    }
};