/**
 * Handles fetching health metrics from the API
 * @param state
 * @param action
 * @returns {{isFetching: boolean, error: null}}
 */
import {
    GET_HEALTH_METRICS_FAILURE,
    GET_HEALTH_METRICS_REQUEST,
    GET_HEALTH_METRICS_SUCCESS
} from "../constants";

export default (state = { isFetching: false, error: null, }, action) => {
    switch (action.type) {
        case GET_HEALTH_METRICS_REQUEST:
            return {
                ...state,
                error: null,
                isFetching: true,
            };
        case GET_HEALTH_METRICS_SUCCESS:
            return {
                ...state,
                error: null,
                isFetching: false,
                metrics: [...action.payload]
            };
        case GET_HEALTH_METRICS_FAILURE:
            return {
                ...state,
                error: action.payload,
                isFetching: false,
            };
        default:
            return {
                ...state
            }
    }
};