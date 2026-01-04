import React, { useState, useEffect } from 'react';
import TopNavBar from '../components/TopNavBar'; // 경로 확인 필요!
import { commonGet } from '../utils/ShareBuyUtil'; // 기존 유틸 사용

const BoardPage = () => {
  // 1. Model 정의 (Vue의 ref 역할)
  const [boardData, setBoardData] = useState<any>({});

  // 2. 초기화 로직 (Vue의 onMounted 역할)
  useEffect(() => {
    const fetchBoard = async () => {
      try {
        const res = await commonGet('/board');
        setBoardData(res); // 데이터 세팅
      } catch (error) {
        console.error("게시판 데이터 로드 실패:", error);
      }
    };

    fetchBoard();
  }, []); // 마운트 시점에 딱 한 번만 실행

  // 3. 이벤트 핸들러들 (자식 컴포넌트들로부터 올라오는 신호 처리)
  const handleSearch = (value: string) => {
    console.log("검색어 전달받음:", value);
    // 여기서 다시 commonGet 등으로 검색 결과를 받아오면 되겠죠?
  };

  const handleMenuClick = () => {
    alert("메뉴가 클릭되었습니다!");
  };

  return (
    <div className="board-page">
      {/* 조립된 상단바 사용 */}
      <TopNavBar 
        onSearch={handleSearch} 
        onMenuClick={handleMenuClick}
        location="산본동 공구중" 
      />

      {/* 메인 컨텐츠 영역 */}
      <main style={{ padding: '20px' }}>
        <h2>게시판 테스트</h2>
        <pre style={{ background: '#f4f4f4', padding: '15px', borderRadius: '8px' }}>
          {JSON.stringify(boardData, null, 2)}
        </pre>
      </main>
    </div>
  );
};

export default BoardPage;