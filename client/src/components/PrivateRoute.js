import React, { useEffect } from "react";
import { Route } from "react-router-dom";
import { useAuth0 } from "../util/auth0-spa";

/**
 * Will not render the route while the user is not authenticated with Auth0
 * @param Component Component to render
 * @param path String the path when this component should render
 * @param rest  Any additional props to pass to the underlying Router component
 * @returns {*}
 * @constructor
 */
const PrivateRoute = ({ component: Component, path, ...rest }) => {
    const { loading, isAuthenticated, loginWithRedirect } = useAuth0();

    useEffect(() => {
        if (loading || isAuthenticated) {
            return;
        }
        const fn = async () => {
            await loginWithRedirect({
                appState: { targetUrl: path }
            });
        };
        fn();
    }, [loading, isAuthenticated, loginWithRedirect, path]);

    const render = props =>
        isAuthenticated === true ? <Component {...props} /> : null;

    return <Route path={path} render={render} {...rest} />;
};

export default PrivateRoute;