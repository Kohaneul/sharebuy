# ShareBuy 🛒
대량 상품 공동구매 플랫폼

## 프로젝트 소개
여러 사용자가 대량 상품을 1/n으로 구매할 수 있는 플랫폼입니다.
로그인 사용자 한정 위치 기반으로 주변 공동구매에 참여할 수 있습니다.

## 기술 스택
- **Backend** : Java 17, Spring Boot, JPA, PostgreSQL
- **Frontend** : TypeScript, Vue.js

## 핵심 설계

### 메타데이터 기반 동적 페이지 렌더링(진행 중)
페이지별 화면 요소를 하드코딩하지 않고, 백엔드에서 페이지 컨텍스트에 따라 메타데이터를 조합하여 단일 응답으로 제공합니다. 프론트엔드는 이 메타데이터를 기반으로 동적으로 렌더링합니다.

- 메뉴별 TopNav, PageSection, 권한 정보를 단일 API 응답으로 조립
- 권한(RoleType)에 따라 접근 가능한 섹션만 필터링하여 제공
- TopNavProvider 전략 패턴으로 컴포넌트별 데이터 조립 로직 분리
- 관리자 페이지에서 코드 수정 없이 페이지 레이아웃 구성 가능 (예정)

### 게스트/로그인 사용자 처리
- 로그인 사용자 : Spring Security 기반 인증
- 게스트 : 위치 정보(위도/경도) 기반 Google Maps API로 주소 변환 

### Backend
```bash
git clone https://github.com/Kohaneul/sharebuy.git
./gradlew bootRun
```

### Frontend
```bash
# Node.js 버전 설정 (nvm 필요)
nvm use 20

# 패키지 설치
npm install

# 개발 서버 실행
npm run dev
