import React from "react";
import { Link } from 'react-router-dom';
import withContainer from "../../components/withContainer";
import './NotFound.css';

/**
 * A functional component which is shown when the rout returns a 404
 * @returns {*}
 */
const NotFound = () =>
    <div className="row">
        <div className="d-flex flex-column align-self-center align-items-center ml-4">
            <i className="fas fa-7x fa-exclamation-triangle d-none d-md-block" style={{ color: '#ffa27b'}} />
        </div>
        <div className="col-md-8">
            <div id="error-block" className="container-lg 404">
                <h1 className="common-SectionTitle">Page not found!</h1>
                <h2 className="common-IntroText">Sorry, but the page you were looking for could not be found.</h2>
                <p className="common-BodyText">
                    You can <Link className="common-Link" to="/">return to our home page</Link>, or <Link className="common-Link" to="/support">drop
                    us a line</Link> if you can't find what you're looking for.
                </p>
            </div>
        </div>
    </div>;

export default withContainer(NotFound);