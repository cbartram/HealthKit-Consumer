import React, { Component } from 'react';
import Container from './Container/Container';

/**
 * Attaches a <Container /> Component around the
 * base component which shows the Navbar and footer on the page.
 * @param BaseComponent
 * @param props
 * @returns {{new(props: Readonly<P>): EnhancedComponent, new(props: P, context?: any): EnhancedComponent, prototype: EnhancedComponent}}
 */
const withContainer = (BaseComponent, props = {}) => {
    return class EnhancedComponent extends Component {
        constructor(props) {
            super(props);
            this.state = { alerts: [] }
        }

        render() {
            return (
                <Container {...props}>
                    <BaseComponent />
                </Container>
            );
        }
    }
};

export default withContainer;