import React from 'react';
import { Button } from 'antd';
import { MenuOutlined } from '@ant-design/icons';
import './Menu.css';

interface MenuProps {
  onMenuClick?: () => void; // Vue의 emit(['menuClick']) 역할
}

const Menu = ({ onMenuClick }: MenuProps) => {
  return (
    <Button 
      type="text" 
      onClick={onMenuClick}
      style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}
    >
      <MenuOutlined className="nav-icon" />
    </Button>
  );
};

export default Menu;