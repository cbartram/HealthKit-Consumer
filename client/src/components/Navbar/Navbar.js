import React, { useState } from 'react';
import { Menu } from 'semantic-ui-react'
import { history} from "../../util/util";
import Logo from '../../resources/images/logo.png';
import { useAuth0 } from "../../util/auth0-spa";
import './Navbar.css';

const Navbar = () => {
    const { isAuthenticated, loginWithRedirect, logout } = useAuth0();
    const [activePage, setActivePage] = useState();
    return (
      <Menu>
          <Menu.Item>
              <img src={Logo} alt="logo" />
          </Menu.Item>
         <Menu.Item name='home' active={activePage === 'home'} onClick={() => {
             history.push('/');
             setActivePage('home');
         }}>
          Home
        </Menu.Item>
          {
              isAuthenticated ?
              <Menu.Item
                  active={activePage === 'dashboard'}
                  onClick={() =>  {
                      history.push('/dashboard');
                      setActivePage('dashboard');
                  }}>
                  Dashboard
              </Menu.Item> :
              <Menu.Item
                  onClick={() => loginWithRedirect({})}
              >
                  Login
              </Menu.Item>
          }
          {
              isAuthenticated &&
                <Menu.Item onClick={() => logout()}>
                  Logout
                </Menu.Item>
          }
      </Menu>
  )
};

export default Navbar