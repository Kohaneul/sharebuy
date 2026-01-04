import React from 'react';
import { Layout, Button } from 'antd';
import LocationInfo from './nav/LocationInfo';
import SearchForm from './nav/SearchForm';
import Alarm from './nav/Alarm';
import Menu from './nav/Menu';
import './TopNavBar.css';

const { Header } = Layout;

interface TopNavBarProps {
  location?: string;
  onLogoClick?: () => void;
  onSearch?: (value: string) => void;
  onNotificationClick?: () => void;
  onMenuClick?: () => void;
}

const TopNavBar = ({
  location = '서울시 강남구',
  onLogoClick,
  onSearch,
  onNotificationClick,
  onMenuClick
}: TopNavBarProps) => {

  return (
    <Header className="top-nav-bar">
      {/* 왼쪽: 로고 + 위치 */}
      <div className="left">
        <Button type="text" onClick={onLogoClick} className="logo-button">
          <img 
            src="/main-logo.png" 
            className="button-image" 
            alt="logo"
          />
        </Button>
        <LocationInfo location={location} />
      </div>

      {/* 가운데: 검색창 */}
      <div className="center">
        <SearchForm onSearch={onSearch || ((val) => console.log(val))} />
      </div>

      {/* 오른쪽: 알람 + 메뉴 */}
      <div className="right">
        <Alarm onNotificationClick={onNotificationClick} />
        <Menu onMenuClick={onMenuClick} />
      </div>
    </Header>
  );
};

export default TopNavBar;