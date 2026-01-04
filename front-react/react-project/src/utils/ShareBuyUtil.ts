import axios, { type AxiosInstance, type AxiosResponse } from 'axios';
// 1. Axios 인스턴스 생성
const api: AxiosInstance = axios.create({
// 중요: Vite에서는 VITE_로 시작하는 환경변수를 쓰거나,
// 테스트용이라면 직접 'http://localhost:8080'을 적어주세요.
// import.meta.env.BASE_URL은 프로젝트의 배포 루트 경로를 의미합니다.
baseURL: 'http://localhost:8080',
timeout: 5000,
headers: {
'Content-Type': 'application/json',
},
});

/**
* [commonGet] 범용적인 GET 요청 유틸리티 함수
*/
export async function commonGet<T>(url: string, params?: object): Promise<T> {
try {
const response: AxiosResponse<T> = await api.get(url, { params });
return response.data;
} catch (error) {
handleApiError(url, 'GET', error); // 에러 로그 공통화
    throw error;
  }
}

/**
 * [commonPost] 범용적인 POST 요청 유틸리티 함수
 */
export async function commonPost<T, D = any>(url: string, data?: D): Promise<T> {
    try {
        const response: AxiosResponse<T> = await api.post(url, data);
        return response.data;
    } catch (error) {
        handleApiError(url, 'POST', error);
        throw error;
    }
}

// 에러 핸들링 로직 분리 (가독성 향상)
function handleApiError(url: string, method: string, error: any) {
    if (axios.isAxiosError(error)) {
        console.error(`[API Error] ${method} ${url}:`, error.response?.data || error.message);
    } else {
        console.error(`[API Error] Unexpected Error on ${method} ${url}:`, error);
    }
}