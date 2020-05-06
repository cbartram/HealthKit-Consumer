import React from 'react';
import './Container.css';
import Navbar from '../Navbar/Navbar';
import Footer from '../Footer/Footer';

/**
 * Container Component which renders its children and includes the navbar and footer.
 */
const Container = (props) => {
    return (
        <div className="container-fluid" style={props.style}>
          <Navbar />
          {props.children}
          <Footer noMargin={props.noFooterMargin} />
        </div>
    );
};

export default Container;