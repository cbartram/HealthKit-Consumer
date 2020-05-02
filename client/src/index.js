import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux'
import {applyMiddleware, compose, createStore} from 'redux';
import thunk from 'redux-thunk';
import rootReducer from './reducers/rootReducer';
import * as constants from './constants'
import {dispatchProcess, dispatchProcessMiddleware} from './util';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';


// Setup Redux middleware and store
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store = createStore(rootReducer, constants.INITIAL_STATE, composeEnhancers(
    applyMiddleware(thunk, dispatchProcessMiddleware)
));

/**
 * Checks the cookies/local storage to see if the user has been authenticated recently/remembered and loads
 * necessary data from the user's profile.
 * @returns {Promise<void>}
 */
const load = async () => {
    try {
        store.dispatch(loginSuccess(user));
        await dispatchProcess(fetchVideos(`user-${user.idToken.payload['cognito:username']}`), constants.VIDEOS_SUCCESS, constants.VIDEOS_FAILURE);
    }
    catch (e) {
        console.log('[ERROR] Failed to load web app: ', e);
    }
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
        // Now render the full page
        ReactDOM.render(
            <Provider store={store}>
                <Router/>
            </Provider>
            , document.getElementById('root'));

        // If you want your app to work offline and load faster, you can change
        // unregister() to register() below. Note this comes with some pitfalls.
        // Learn more about service workers: http://bit.ly/CRA-PWA
        serviceWorker.unregister();
    } catch(err) {
        console.log(err);
        ReactDOM.render(
            <Provider store={store}>
                <Router error />
            </Provider>, document.getElementById('root'));
    }
};

// Execute the App
render().then(() => {
    console.log('App Rendered Successfully.')
});

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
