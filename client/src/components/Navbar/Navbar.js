import React from 'react';
import { Menu } from 'semantic-ui-react'
import Logo from '../../resources/images/logo.png';
import './Navbar.css';


const Navbar = (props) => {
  return (
      <Menu>
          <Menu.Item>
              <img src={Logo} alt="logo" />
          </Menu.Item>
          <Menu.Item name='home' active>
          Home
        </Menu.Item>
        <Menu.Item name='metrics'>
          Metrics
        </Menu.Item>
        <Menu.Item name='analytics'>
          Analytics
        </Menu.Item>
      </Menu>
  )
};

export default Navbar