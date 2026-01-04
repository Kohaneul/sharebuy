import React from 'react';
import TopNavBar from '../nav/TopNavBar'; // 경로에 주의하세요!

interface LayoutProps {
  children: React.ReactNode; // 여기에 각 페이지(BoardPage 등)가 들어옵니다.
}

const MainLayout = ({ children }: LayoutProps) => {
  return (
    <div className="main-layout" style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      {/* 1. 고정 상단바: 모든 페이지에 공통으로 나타남 */}
      <TopNavBar />

      {/* 2. 가변 컨텐츠: URL에 따라 바뀌는 페이지 내용 */}
      <main className="content-area" style={{ flex: 1, padding: '16px' }}>
        {children}
      </main>

      {/* 3. 필요하면 여기에 Footer 추가 가능 */}
    </div>
  );
};

export default MainLayout;