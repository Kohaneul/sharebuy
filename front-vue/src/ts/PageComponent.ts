
export enum PageComponent {
  CARD = 'CARD',
  BUTTON='BUTTON'
  // GRID = 'GRID',
  // INPUT = 'INPUT',
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