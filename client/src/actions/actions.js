import { post } from '../util/util';
import {
    getRequestUrl,
    UPDATE_HEALTH_METRICS,
    GET_HEALTH_METRICS_FAILURE,
    GET_HEALTH_METRICS_SUCCESS,
    GET_HEALTH_METRICS_REQUEST,
    OAUTH_ENDPOINT,
    OAUTH_TOKEN_SUCCESS,
    CLIENT_ID,
    CLIENT_SECRET,
    GET_HEALTH_METRICS
} from "../constants";

/**
 * Sends a user's current video information (such as duration completed, started watching etc.) to
 * the backend for storage. This also updates redux with the latest values.
 * @returns {Function}
 */
export const fetchHealthMetrics = (payload) => async (dispatch, getState) => {
    await post(payload, GET_HEALTH_METRICS, GET_HEALTH_METRICS_REQUEST, GET_HEALTH_METRICS_SUCCESS, GET_HEALTH_METRICS_FAILURE, dispatch, getState);
};

export const updateHealthMetrics = (payload) => ({
   type: UPDATE_HEALTH_METRICS,
   payload,
});

/**
 * Retrieves an oauth token from Auth0 using the backend Spring
 * server as a proxy
 * @returns {Function}
 */
export const getOAuthToken = () => async (dispatch) => {
    const response = await (await fetch(getRequestUrl(OAUTH_ENDPOINT), {
        method: 'GET',
        headers: {
            Authorization: `Basic ${btoa(`${CLIENT_ID}:${CLIENT_SECRET}`)}`
        }
    })).json();
    dispatch({ type: OAUTH_TOKEN_SUCCESS, payload: response });
};