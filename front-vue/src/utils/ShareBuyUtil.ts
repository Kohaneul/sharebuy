// src/utils/ShareBuyUtil.ts

import axios, { AxiosInstance, AxiosResponse } from 'axios';

const API_PREFIX = '/rest'; // 접두사 정의

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
    const combinedUrl = url.startsWith('/') ? `${API_PREFIX}${url}` : `${API_PREFIX}/${url}`;
  try {
    const response: AxiosResponse<T> = await api.get(combinedUrl, { params });
    return response.data; 
  } catch (error) {
    if (axios.isAxiosError(error)) {
        console.error(`[API Error] GET ${combinedUrl}:`, error.message);
    } else {
        console.error(`[API Error] Unexpected Error on GET ${combinedUrl}:`, error);
    }
    throw error;
  }
}


/**
 * [commonPost] 범용적인 POST 요청 유틸리티 함수
 */
export async function commonPost<T, D = any>(url: string, data: D,  headers?: Record<string, string>): Promise<T> {
     const combinedUrl = url.startsWith('/') ? `${API_PREFIX}${url}` : `${API_PREFIX}/${url}`;

    try {
        const response: AxiosResponse<T> = await api.post(combinedUrl, data,{headers});
        return response.data;
    } catch (error) {
        if (axios.isAxiosError(error)) {
            console.error(`[API Error] POST ${combinedUrl}:`, error.message);
        } else {
            console.error(`[API Error] Unexpected Error on POST ${combinedUrl}:`, error);
        }
        throw error;
    }
}
/**
 * [commonPost] 범용적인 POST 요청 유틸리티 함수
 */
export async function commonPostLogin<T, D = any>(url: string, data: D): Promise<T> {
     const combinedUrl = url.startsWith('/') ? `${API_PREFIX}${url}` : `${API_PREFIX}/${url}`;

    try {
        const response: AxiosResponse<T> = await api.post(combinedUrl, data, { headers: { 'Content-Type': 'application/x-www-form-urlencoded' }});
        return response.data;
    } catch (error) {
        if (axios.isAxiosError(error)) {
            console.error(`[API Error] POST ${combinedUrl}:`, error.message);
        } else {
            console.error(`[API Error] Unexpected Error on POST ${combinedUrl}:`, error);
        }
        throw error;
    }
}