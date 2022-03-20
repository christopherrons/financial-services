import React from 'react';
import {
  Nav,
  NavLink,
  NavMenu,
} from './NavbarElements';

const AppNameDiv = () => {
  return (
    <div className="app-name">
      <h1>Financial Services</h1>
    </div>
  );
}

const MainNavbar = () => {
  return (
    <>
      <Nav>
        <NavMenu>
          <AppNameDiv />
          <NavLink to='/marketdata'>
            Market Data
          </NavLink>
          <NavLink to='/surveillance'>
            Surveillance
          </NavLink>
          <NavLink to='/pricing'>
            Pricing
          </NavLink>
          <NavLink to='/risk'>
            Risk
          </NavLink>
          <NavLink to='/referencedata'>
            Reference Data
          </NavLink>
        </NavMenu>
      </Nav>
    </>
  );
};

export default MainNavbar;