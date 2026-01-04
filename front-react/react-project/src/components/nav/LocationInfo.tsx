import React from 'react';
import { Button } from 'antd';
import './LocationInfo.css';

// 1. Props 타입 정의 (Vue의 defineProps 역할)
interface LocationInfoProps {
  location?: string;
  onLogoClick?: () => void; // Vue의 emit(['logoClick']) 역할
}

const LocationInfo = ({ 
  location = '서울시 강남구', 
  onLogoClick 
}: LocationInfoProps) => {

  return (
    <div className="location-info-container">
      <Button 
        type="text" 
        onClick={onLogoClick} 
        className="location-button"
      >
        <img 
          src="/location.png" 
          alt="버튼 이미지" 
          className="w-5 h-5 mr-1"
          style={{ width: '20px', height: '20px' }} // Tailwind 클래스 미적용 대비
        />
        <span className="location-text">
          {location}
        </span>
      </Button>
    </div>
  );
};

export default LocationInfo;