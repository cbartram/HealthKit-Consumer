import React from 'react';
import { Link } from 'react-router-dom';
import Logo from '../../resources/images/logo.png';
import './Footer.css';

/**
 * The page footer component
 */
const Footer = (props) => {
    return (
        <footer className={`pt-4 ${!props.noMargin && 'mt-5'} pt-md-5 border-top`}>
          <div className="row">
            <div className="col-12 col-md">
              <img className="mb-2" src={Logo} alt="" width="24"
                   height="24" />
              <small className="d-block mb-3 text-muted">&copy; 2020-2021</small>
            </div>
            <div className="col-6 col-md">
              <h5>Sitemap</h5>
              <ul className="list-unstyled text-small">
                <li><Link className="text-muted" to="/metrics">Health Metrics</Link></li>
                <li><Link className="text-muted" to="/info">Information</Link></li>
                <li><Link className="text-muted" to="/login">Login</Link></li>
                <li><Link className="text-muted" to="/signup">Sign Up</Link></li>
                <li><Link className="text-muted" to="/cookie">Cookie Policy</Link></li>
              </ul>
            </div>
            <div className="col-6 col-md">
              <h5>Resources</h5>
              <ul className="list-unstyled text-small">
                <li><Link className="text-muted" to="/support">Support</Link></li>
                <li><Link className="text-muted" to="/legal">Legal</Link></li>
                <li><Link className="text-muted" to="/legal">Privacy</Link></li>
                <li><Link className="text-muted" to="/terms">Terms of Service</Link></li>
                <li><Link className="text-muted" target="_blank" to="/sitemap.xml">Sitemap</Link></li>
              </ul>
            </div>
          </div>
        </footer>
    );
};

export default Footer;