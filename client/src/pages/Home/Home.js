import React, { Component } from 'react';
import { Button } from 'semantic-ui-react';
import { history } from '../../util/util';
import withContainer from "../../components/withContainer";
import './Home.css';

class Home extends Component {
    render() {
        return (
            <div>
                <h1>Home Page</h1>
                <Button primary onClick={() => history.push('/dashboard')}>View Metrics</Button>
            </div>
        )
    }
}

export default withContainer(Home)
