import { post } from '../util';
import * as constants from '../constants'
import {UPDATE_HEALTH_METRICS} from "../constants";

/**
 * Sends a user's current video information (such as duration completed, started watching etc.) to
 * the backend for storage. This also updates redux with the latest values.
 * @returns {Function}
 */
export const fetchHealthMetrics = (payload) => async (dispatch, getState) => {
    await post(payload, constants.GET_HEALTH_METRICS, constants.GET_HEALTH_METRICS_REQUEST, constants.GET_HEALTH_METRICS_SUCCESS, constants.GET_HEALTH_METRICS_FAILURE, dispatch, getState);
};

export const updateHealthMetrics = (payload) => ({
   type: UPDATE_HEALTH_METRICS,
   payload,
});