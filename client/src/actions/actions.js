import { get } from '../util/util';
import {
    UPDATE_HEALTH_METRICS,
    GET_HEALTH_METRICS_FAILURE,
    GET_HEALTH_METRICS_SUCCESS,
    GET_HEALTH_METRICS_REQUEST,
    OAUTH_ENDPOINT,
    OAUTH_TOKEN_SUCCESS,
    AUTH0_CLIENT_SECRET,
    GET_HEALTH_METRICS, AUTH0_DOMAIN, AUTH0_CLIENT_ID
} from "../constants";

/**
 * Sends a user's current video information (such as duration completed, started watching etc.) to
 * the backend for storage. This also updates redux with the latest values.
 * @returns {Function}
 */
export const fetchHealthMetrics = () => async (dispatch, getState) => {
    await get(GET_HEALTH_METRICS, GET_HEALTH_METRICS_REQUEST, GET_HEALTH_METRICS_SUCCESS, GET_HEALTH_METRICS_FAILURE, dispatch, getState);
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
    const response = await (await fetch(`https://${AUTH0_DOMAIN}${OAUTH_ENDPOINT}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify({
            client_id: AUTH0_CLIENT_ID,
            client_secret: AUTH0_CLIENT_SECRET,
            grant_type: 'client_credentials',
            audience: 'http://localhost:8080'
        })
    })).json();
    dispatch({ type: OAUTH_TOKEN_SUCCESS, payload: response });
};