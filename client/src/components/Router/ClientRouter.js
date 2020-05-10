import React, {Component} from 'react';
import { Router, Route, Switch } from "react-router-dom";
import {connect} from 'react-redux';
import Home from "../../pages/Home/Home";
import PrivateRoute from "../PrivateRoute";
import App from "../../pages/Dashboard/App";
import NotFound from "../../pages/NotFound/NotFound";
import { history } from '../../util/util';

const mapStateToProps = state => ({
    auth: state.auth,
});

/**
 * This Component handles the routes which are displayed within index.js
 */
class ClientRouter extends Component {
    render() {
        return (
            <Router history={history}>
                <Switch>
                    <Route exact path="/" component={Home} />
                    <PrivateRoute path="/dashboard" component={App} />

                    {/* Catch All unmatched paths with a 404 */}
                    <Route component={NotFound} />
                </Switch>
            </Router>
        )
    }
}

export default connect(mapStateToProps)(ClientRouter);