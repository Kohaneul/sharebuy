
export enum PageComponent {
  CARD = 'CARD',
  BUTTON='BUTTON',
  TEXT='TEXT',
  IMAGE='IMAGE',
  PARTICIPANTS='PARTICIPANTS',
  STATUS='STATUS',
  INPUT = 'INPUT'
  // GRID = 'GRID',
  // SEARCH_FORM='SEARCH_FORM'
}

export enum ActionType {
  MOVE = "MOVE",
  API = "API"
}

export interface JsonConfig {
  method?: 'GET' | 'POST' | 'PUT' | 'DELETE'; // HTTP 메서드
  confirm?: string;   // 컨펌 창 문구 (없으면 스킵)
  msg?: string;       // 완료 후 알림 문구
  refresh?: boolean;  // 완료 후 새로고침 여부
  payloadKey?: string; // 데이터를 감쌀 키값 (예: 'ids')
}

export interface CardData{
  id: string;       
  title: string;       // 게시글 제목
  nickName: string;    // 작성자 닉네임
  avatar: string | null;  // 프로필 이미지 (null 가능)
  content: string;     // 게시글 본문
  imgUrl: string | null;  // 게시글 썸네일 (null 가능)
  status: 'RECRUITING' | 'CLOSED'; // 모집 상태 (Enum 기반)  
  loginId:string;
  currentParticipants:number;
  maxParticipants:number;
}