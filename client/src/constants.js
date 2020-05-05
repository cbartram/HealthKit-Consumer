/**
 * This file defines constants used throughout the frontend portion of the application.
 * Constants as the name suggests are constant and never change regardless of state changes.
 * @type {{}}
 */
export const INITIAL_STATE = {
    health: {
        isFetching: false,
        error: null,
    }
};

/**
 * Helper variable to determine if the App is in the production environment. This decides which API call to make.
 * @type {boolean} True if the application is running in prod and false otherwise.
 */
export const IS_PROD = window.location.hostname !== 'localhost' || process.env.REACT_APP_NODE_ENV === 'production';


// Prod Params
export const PROD_URL = 'http://localhost:8080';

// Dev Params
export const DEV_URL = 'http://localhost:8080';

/**
 * Helper function which determines the correct API to hit (prod,dev) and the correct region to use.
 * Note: this defaults to the east region if the REACT_APP_API_REGION is not declared.
 * @param endpointURI String URI of the endpoint requested starting with '/' and ending without a '/'
 * i.e. (/users/find)
 * @returns {string}
 */
export const getRequestUrl = (endpointURI) => {
    let url = '';

    // Attempt to use prod
    if(IS_PROD)
        url = `${PROD_URL}${endpointURI}`;
    else
        url = `${DEV_URL}${endpointURI}`;

    return url;
};

// Api endpoints
export const GET_HEALTH_METRICS = '/health/metrics';

// Redux Constants
export const GET_HEALTH_METRICS_REQUEST = 'GET_HEALTH_METRICS_REQUEST';
export const GET_HEALTH_METRICS_SUCCESS = 'GET_HEALTH_METRICS_SUCCESS';
export const GET_HEALTH_METRICS_FAILURE = 'GET_HEALTH_METRICS_FAILURE';
export const UPDATE_HEALTH_METRICS = 'UPDATE_HEALTH_METRICS';