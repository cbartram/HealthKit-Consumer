import React, { Component } from 'react';
import { Button } from 'semantic-ui-react';
import withContainer from "../../components/withContainer";
import './Home.css';
import { Link } from "react-router-dom";

class Home extends Component {
    render() {
        return (
            <div>
                <h1>Home Page</h1>
                <Button primary><Link to="/dashboard">View Metrics</Link></Button>
            </div>
        )
    }
}

export default withContainer(Home)
