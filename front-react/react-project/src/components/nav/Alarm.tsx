import React from 'react';
import { Button, Badge } from 'antd';
import { BellOutlined } from '@ant-design/icons';
import './Alarm.css'; // 스타일 시트 분리

// 1. Interface 정의 (Vue의 defineProps 역할)
interface AlarmProps {
  location?: string;
  notificationCount?: number;
  onNotificationClick?: () => void; // Vue의 emit 역할을 하는 함수 Prop
}

const Alarm = ({
  location = '서울시 강남구',
  notificationCount = 0,
  onNotificationClick
}: AlarmProps) => {

  // Vue의 searchQuery ref는 현재 템플릿에서 사용되지 않으므로
  // 필요한 경우에만 useState로 선언하면 됩니다.

  return (
    <Badge count={notificationCount > 0 ? notificationCount : 3}>
      <Button
        type="text"
        onClick={onNotificationClick}
        style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}
      >
        <BellOutlined className="nav-icon" />
      </Button>
    </Badge>
  );
};

export default Alarm;