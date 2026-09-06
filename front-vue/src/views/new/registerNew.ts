export enum PurchaseType {
  ONLINE = 'ONLINE',
  OFFLINE = 'OFFLINE'
}

export const purchaseTypeOptions = [
  { label: '온라인 구매', value: PurchaseType.ONLINE },
  { label: '오프라인 구매', value: PurchaseType.OFFLINE }
];


export enum Category {
  BEAUTY = 'BEAUTY',
  DIGITAL = 'DIGITAL',
  LIVING = 'LIVING',
  ETC = 'ETC'
}

export const categoryOptions = [
  { label: '뷰티/화장품', value: Category.BEAUTY },
  { label: '디지털/가전', value: Category.DIGITAL },
  { label: '생활/잡화', value: Category.LIVING },
  { label: '생활/잡화', value: Category.ETC }
];
