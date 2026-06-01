# ShareBuy 🛒

> 위치 기반 대량 상품 공동구매 플랫폼

여러 사용자가 대량 상품을 공동 구매하고 비용을 분담할 수 있는 플랫폼입니다.

사용자는 주변에서 진행 중인 공동구매를 탐색하고 참여할 수 있으며, 회원뿐 아니라 게스트 사용자도 위치 기반 서비스를 이용할 수 있습니다.

---

## 📌 프로젝트 소개

ShareBuy는 공동구매 참여 과정을 간소화하고, 위치 기반으로 주변 사용자와 상품을 공유할 수 있도록 개발한 플랫폼입니다.

단순한 게시판 형태의 공동구매 서비스가 아닌, 페이지 구성을 메타데이터 기반으로 동적으로 제공하는 구조를 목표로 설계하고 있습니다.

---

## 🛠 Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL

### Frontend

* TypeScript
* Vue.js
* Vite

### External Service

* Kakao Maps API

---

## ✨ 주요 기능

### 공동구매 게시글

* 공동구매 등록 및 수정
* 모집 인원 관리
* 참여 신청 및 참여자 조회
* 공동구매 상태 관리

### 위치 기반 서비스

* 사용자 위치 기반 게시글 조회
* Google Maps API 기반 주소 변환
* 게스트 사용자 위치 기반 서비스 제공

### 인증 및 권한 관리

* Spring Security 기반 인증
* RoleType 기반 권한 제어
* 메뉴 및 페이지 접근 제한

---

## 🏗 핵심 설계

### 1. SDUI(Server Driven UI) 적용

화면 변경이 빈번한 영역을 중심으로 SDUI(Server Driven UI) 구조를 적용하고 있습니다.

백엔드가 페이지 메타데이터를 생성하고,
프론트엔드는 이를 기반으로 화면을 동적으로 렌더링합니다.

복잡한 사용자 인터랙션이 필요한 일부 화면은 기존 방식으로 구현하고,
조회성 간단한 화면들은 점진적으로 SDUI 구조로 전환하고 있습니다.

#### 제공 메타데이터

* Top Navigation
* Page Section
* 접근 권한 정보
* 페이지 컨텍스트 정보

#### 기대 효과

* 프론트엔드 수정 최소화
* 신규 메뉴 확장 용이
* UI 구성의 유연성 확보
* 관리자 페이지 기반 화면 구성 지원 예정

---

### 2. 권한 기반 UI 구성

사용자의 RoleType에 따라 접근 가능한 화면 요소만 제공하도록 설계했습니다.

#### 예시

* GUEST : 조회 기능 제공
* USER : 공동구매 참여 가능
* ADMIN : 관리 기능 제공

백엔드에서 권한 검증과 화면 구성 데이터를 함께 제공하여 클라이언트와 서버 간 권한 정책을 일관되게 유지합니다.

---

### 3. Strategy Pattern 기반 화면 구성

페이지 유형별 데이터 조립 로직을 분리하기 위해 Strategy Pattern을 적용했습니다.

#### 주요 컴포넌트

* TopNavProvider(페이지 상단 구성)
* PageSectionProvider(페이지 본문 구성)
* ProviderFactory

페이지 추가 시 기존 코드 수정 없이 새로운 Provider만 추가하여 확장할 수 있습니다.

---

### 4. 게스트 사용자 위치 처리

회원가입 없이도 주변 공동구매 조회가 가능하도록 구현했습니다.

#### 처리 과정

1. 브라우저 위치 정보 수집
2. Google Maps API를 통한 주소 변환
3. 지역 기반 게시글 조회

---

## 📂 프로젝트 구조

```text
sharebuy

├── backend
│   ├── domain
│   ├── application
│   ├── infrastructure
│   └── presentation
│
└── frontend
    ├── pages
    ├── components
    ├── stores
    ├── services
    └── router
```

---

## 🚀 실행 방법

### Backend

```bash
git clone https://github.com/Kohaneul/sharebuy.git

cd sharebuy/backend

./gradlew bootRun
```

### Frontend

```bash
cd sharebuy/frontend

nvm use 20

npm install

npm run dev
```

---

## 🎯 향후 계획

* 메타데이터 기반 페이지 렌더링 완성
* 관리자 페이지 기반 레이아웃 구성
* 공동구매 실시간 상태 반영
* 알림 기능 추가
* 지도 기반 공동구매 탐색 기능 강화

