import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux'
import { Loader } from 'semantic-ui-react';
import { applyMiddleware, compose, createStore } from 'redux';
import thunk from 'redux-thunk';
import { Auth0Provider } from "./util/auth0-spa";
import rootReducer from './reducers/rootReducer';
import * as serviceWorker from './serviceWorker';
import Router from "./components/ClientRouter";
import {
    dispatchProcess,
    dispatchProcessMiddleware,
    history,
} from './util/util';
import {
    fetchHealthMetrics,
    getOAuthToken
} from './actions/actions';
import {
    OAUTH_TOKEN_SUCCESS,
    OAUTH_TOKEN_FAILURE,
    GET_HEALTH_METRICS_SUCCESS,
    GET_HEALTH_METRICS_FAILURE,
    AUTH0_DOMAIN,
    AUTH0_CLIENT_ID
} from "./constants";
import './index.css';


// Setup Redux middleware and store
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store = createStore(rootReducer,{}, composeEnhancers(
    applyMiddleware(thunk, dispatchProcessMiddleware)
));

/**
 * Checks the cookies/local storage to see if the user has been authenticated recently/remembered and loads
 * necessary data from the user's profile.
 * @returns {Promise<void>}
 */
const load = async () => {
    try {
        await dispatchProcess(getOAuthToken(), OAUTH_TOKEN_SUCCESS, OAUTH_TOKEN_FAILURE);
        await dispatchProcess(fetchHealthMetrics(), GET_HEALTH_METRICS_SUCCESS, GET_HEALTH_METRICS_FAILURE);
    }
    catch (e) {
        console.log('[ERROR] Failed to load web app: ', e);
    }
};

// A function that routes the user to the right place
// after login
const onRedirectCallback = appState => {
    history.push(
        appState && appState.targetUrl
            ? appState.targetUrl
            : window.location.pathname
    );
};

/**
 * Render function renders the React app to the DOM and is simply a wrapper
 * to allow for async/await syntax
 * @returns {Promise<void>}
 */
const render = async () => {
    // Render a loading page immediately while we wait for our content to load
    ReactDOM.render(
        <Provider store={store}>
            <Loader active />
        </Provider> ,document.getElementById('root'));

    try {
        await load();
        ReactDOM.render(
            <Auth0Provider
                domain={AUTH0_DOMAIN}
                client_id={AUTH0_CLIENT_ID}
                redirect_uri={window.location.origin}
                onRedirectCallback={onRedirectCallback}
            >
                <Provider store={store}>
                    <Router />
                </Provider>
            </Auth0Provider>
            , document.getElementById('root'));

        // If you want your app to work offline and load faster, you can change
        // unregister() to register() below. Note this comes with some pitfalls.
        // Learn more about service workers: http://bit.ly/CRA-PWA
        serviceWorker.unregister();
    } catch(err) {
        console.log(err);
        ReactDOM.render(
            <Provider store={store}>
                <Router />
            </Provider>, document.getElementById('root'));
    }
};

// Execute the App
render().then(() => {});

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
