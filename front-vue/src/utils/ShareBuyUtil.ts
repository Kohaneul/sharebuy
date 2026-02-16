// src/utils/ShareBuyUtil.ts

import axios, { AxiosInstance, AxiosResponse } from 'axios';

// 1. Axios 인스턴스 생성
// const api: AxiosInstance = axios.create({
//   baseURL: import.meta.env.BASE_URL, // 환경 변수 사용
//   timeout: 5000,
//   headers: {
//     'Content-Type': 'application/json',
//   },
// });

const api: AxiosInstance = axios.create({
  baseURL: 'http://localhost:9011', // Spring Boot 서버 주소
  timeout: 5000,
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true, // 세션/쿠키 필요하면 추가
});


/**
 * [commonGet] 범용적인 GET 요청 유틸리티 함수
 */
export async function commonGet<T>(url: string, params?: object): Promise<T> {
  try {
    const response: AxiosResponse<T> = await api.get(url, { params });
    return response.data; 
  } catch (error) {
    if (axios.isAxiosError(error)) {
        console.error(`[API Error] GET ${url}:`, error.message);
    } else {
        console.error(`[API Error] Unexpected Error on GET ${url}:`, error);
    }
    throw error;
  }
}

/**
 * [commonPost] 범용적인 POST 요청 유틸리티 함수
 */
export async function commonPost<T, D = any>(url: string, data: D): Promise<T> {
    try {
        const response: AxiosResponse<T> = await api.post(url, data);
        return response.data;
    } catch (error) {
        if (axios.isAxiosError(error)) {
            console.error(`[API Error] POST ${url}:`, error.message);
        } else {
            console.error(`[API Error] Unexpected Error on POST ${url}:`, error);
        }
        throw error;
    }
}