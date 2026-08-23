<template>
    <PageWrapper>
  <div class="post-create">
    <a-form
      :model="form"
      layout="vertical"
      @finish="handleSubmit"
    >
      <!-- 기본 정보 -->
      <!-- <a-divider>기본 정보</a-divider> -->

      <a-row :gutter="16">
        <a-col :span="24">
          <a-form-item
            label="제목"
            name="title"
            :rules="[
              { required: true, message: '제목을 입력해주세요.' },
              { min: 2, max: 100, message: '제목은 2~100자 사이여야 합니다.' }
            ]"
          >
            <a-input
              v-model:value="form.title"
              placeholder="게시글 제목을 입력해주세요."
            />
          </a-form-item>
        </a-col>

        <a-col :span="12">
          <a-form-item
            label="카테고리"
            name="category"
            :rules="[
              { required: true, message: '카테고리를 선택해주세요.' }
            ]"
          >
            <a-select
              v-model:value="form.category"
              placeholder="카테고리를 선택해주세요."
            >
              <a-select-option
                v-for="category in categoryOptions"
                :key="category.value"
                :value="category.value"
              >
                {{ category.label }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>

        <a-col :span="12">
          <a-form-item
            label="구매 방식"
            name="purchaseType"
            :rules="[
              { required: true, message: '구매 방식을 선택해주세요.' }
            ]"
          >
            <a-select
              v-model:value="form.purchaseType"
              placeholder="구매 방식을 선택해주세요."
            >
              <a-select-option
                v-for="type in purchaseTypeOptions"
                :key="type.value"
                :value="type.value"
              >
                {{ type.label }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>

      <!-- 상품 정보 -->
      <!-- <a-divider>상품 정보</a-divider> -->

      <a-row :gutter="16">
        <a-col :span="12">
          <a-form-item
            label="구입처"
            name="purchasePlace"
            :rules="[
              { required: true, message: '구입처를 입력해주세요.' }
            ]"
          >
            <a-input
              v-model:value="form.purchasePlace"
              placeholder="예: 쿠팡"
            />
          </a-form-item>
        </a-col>

        <a-col :span="12">
          <a-form-item
            label="상품 코드"
            name="productCode"
            :rules="[
              { required: true, message: '상품 코드를 입력해주세요.' }
            ]"
          >
            <a-input
              v-model:value="form.productCode"
              placeholder="상품 코드를 입력해주세요."
            />
          </a-form-item>
        </a-col>

        <a-col :span="24">
          <a-form-item
            label="상품 구매 URL"
            name="purchaseUrl"
            :rules="[
              { required: true, message: '상품 URL을 입력해주세요.' }
            ]"
          >
            <a-input
              v-model:value="form.purchaseUrl"
              placeholder="https://..."
            />
          </a-form-item>
        </a-col>

        <a-col :span="8">
          <a-form-item
            label="총 구매 금액"
            name="totalPrice"
            :rules="[
              { required: true, message: '총 구매 금액을 입력해주세요.' }
            ]"
          >
            <a-input-number
              v-model:value="form.totalPrice"
              :min="0"
              :precision="0"
              style="width: 100%"
              addon-after="원"
            />
          </a-form-item>
        </a-col>

        <a-col :span="8">
          <a-form-item
            label="인당 참여 금액"
            name="perPrice"
            :rules="[
              { required: true, message: '인당 참여 금액을 입력해주세요.' }
            ]"
          >
            <a-input-number
              v-model:value="form.perPrice"
              :min="0"
              :precision="0"
              style="width: 100%"
              addon-after="원"
            />
          </a-form-item>
        </a-col>

        <a-col :span="8">
          <a-form-item
            label="최대 참여 인원"
            name="maxParticipants"
            :rules="[
              { required: true, message: '최대 참여 인원을 입력해주세요.' }
            ]"
          >
            <a-input-number
              v-model:value="form.maxParticipants"
              :min="1"
              :precision="0"
              style="width: 100%"
              addon-after="명"
            />
          </a-form-item>
        </a-col>

        <a-col :span="12">
          <a-form-item
            label="구매 일시"
            name="purchaseAt"
            :rules="[
              { required: true, message: '구매 일시를 선택해주세요.' }
            ]"
          >
            <a-date-picker
              v-model:value="form.purchaseAt"
              show-time
              format="YYYY-MM-DD HH:mm"
              style="width: 100%"
            />
          </a-form-item>
        </a-col>
      </a-row>

      <!-- 약속 정보 -->
      <!-- <a-divider>약속 정보</a-divider> -->

     <a-form-item
        name="appointment.place.placeName"
        :rules="[
            { required: true, message: '약속 장소를 입력해주세요.' }
        ]"
        >
        <template #label>
            <div class="place-label">
            <span>약속 장소</span>
            <AddressSearch></AddressSearch>
            </div>
        </template>

        <a-input
            v-model:value="form.appointment.place.placeName"
            placeholder="주소 찾기를 눌러주세요."
            readonly
        />
        </a-form-item>


      <!-- 내용 -->
      <a-divider>게시글 내용</a-divider>

      <a-form-item
        label="내용"
        name="content"
        :rules="[
          { required: true, message: '내용을 입력해주세요.' }
        ]"
      >
        <a-textarea
          v-model:value="form.content"
          :rows="8"
          placeholder="게시글 내용을 입력해주세요."
        />
      </a-form-item>

      <!-- 버튼 -->
      <div class="button-wrapper">
        <a-button @click="handleCancel">
          취소
        </a-button>

        <a-button
          type="primary"
          html-type="submit"
          :loading="loading"
        >
          등록
        </a-button>
      </div>
    </a-form>
</div>
  </PageWrapper>
</template>

<script setup lang="ts">
import { ref,  computed,onMounted,reactive } from 'vue';
import dayjs, { Dayjs } from 'dayjs';
import { useRouter } from 'vue-router';
import { useRoute } from 'vue-router';
import { commonGet, commonPost } from '@/utils/ShareBuyUtil'; // 기존 유틸 활용
import { message } from 'ant-design-vue';
import { useUserStore } from '@/store/user';
import PageWrapper from '@/views/PageWrapper.vue';
import { useLocationStore } from '@/store/location';
import KakaoStaticMap from '@/components/page/KakaoStaticMap.vue';
import AddressSearch from './AddressSearch.vue';
const locationStore = useLocationStore();
const userStore = useUserStore();


const route = useRoute();
const post = ref<any>(null);
const latitude = ref();
const longitude = ref();


enum Category {
  FOOD = 'FOOD',
  DAILY = 'DAILY',
  ETC = 'ETC'
}

enum PurchaseType {
  GROUP = 'GROUP',
  INDIVIDUAL = 'INDIVIDUAL'
}

interface PostCreateRequest {
  title: string;
  content: string;
  purchaseType: PurchaseType;
  purchasePlace: string;
  productCode: string;
  purchaseUrl: string;
  totalPrice: number | null;
  perPrice: number | null;
  purchaseAt: string;
  appointment: {
    place: {
      location: {
        latitude: number | null;
        longitude: number | null;
      };
      placeName: string;
    };
    appointmentTime: string;
  };
  imgUrl: string[];
  maxParticipants: number | null;
  category: Category | undefined;
}

interface PostForm {
  title: string;
  content: string;
  purchaseType: PurchaseType | undefined;
  purchasePlace: string;
  productCode: string;
  purchaseUrl: string;
  totalPrice: number | null;
  perPrice: number | null;
  purchaseAt: Dayjs | null;
  appointment: {
    place: {
      location: {
        latitude: number | null;
        longitude: number | null;
      };
      placeName: string;
    };
    appointmentTime: Dayjs | null;
  };
  imgUrl: string[];
  maxParticipants: number | null;
  category: Category | undefined;
}

const router = useRouter();
const loading = ref(false);

const form = reactive<PostForm>({
  title: '',
  content: '',
  purchaseType: undefined,
  purchasePlace: '',
  productCode: '',
  purchaseUrl: '',
  totalPrice: null,
  perPrice: null,
  purchaseAt: null,

  appointment: {
    place: {
      location: {
        latitude: null,
        longitude: null
      },
      placeName: ''
    },
    appointmentTime: null
  },

  imgUrl: [],
  maxParticipants: null,
  category: undefined
});

const categoryOptions = [
  { label: '식품', value: Category.FOOD },
  { label: '생활', value: Category.DAILY },
  { label: '기타', value: Category.ETC }
];

const purchaseTypeOptions = [
  { label: '공동구매', value: PurchaseType.GROUP },
  { label: '개별구매', value: PurchaseType.INDIVIDUAL }
];

const handleSubmit = async () => {
  loading.value = true;

  try {
    const request: PostCreateRequest = {
      title: form.title,
      content: form.content,
      purchaseType: form.purchaseType!,
      purchasePlace: form.purchasePlace,
      productCode: form.productCode,
      purchaseUrl: form.purchaseUrl,
      totalPrice: form.totalPrice,
      perPrice: form.perPrice,
      purchaseAt: form.purchaseAt!.format('YYYY-MM-DDTHH:mm:ss'),

      appointment: {
        place: {
          location: {
            latitude: form.appointment.place.location.latitude,
            longitude: form.appointment.place.location.longitude
          },
          placeName: form.appointment.place.placeName
        },
        appointmentTime:
          form.appointment.appointmentTime!.format(
            'YYYY-MM-DDTHH:mm:ss'
          )
      },

      imgUrl: form.imgUrl,
      maxParticipants: form.maxParticipants,
      category: form.category
    };

    console.log('등록 요청:', request);

    // 실제 API 연결
    // await commonPost('/api/posts', request);

    message.success('게시글이 등록되었습니다.');

    await router.push('/post');
  } catch (error) {
    console.error(error);
    message.error('게시글 등록에 실패했습니다.');
  } finally {
    loading.value = false;
  }
};

const handleCancel = () => {
  router.back();
};

onMounted(async ()=>{
  const isSync = !userStore.isLoggedIn;

  if (isSync) {
    // 캐시된 좌표가 있으면 즉시 그걸로 먼저 렌더링 (fire-and-forget 갱신)
    if (locationStore.latitude && locationStore.longitude) {
      await aboutMe();
      locationStore.syncLocation(true).then(() => {
        // 필요하면 백그라운드에서 최신 좌표로 재조회
      });
      return;
    }
  }

  // 캐시가 없는 최초 진입만 GPS 대기
  await locationStore.syncLocation(isSync);
  await aboutMe();
});

const aboutMe = async()=>{
  try{
    const res = await commonGet(`/user/me`, {
      latitude: locationStore.latitude,   // ← store에서 직접
      longitude: locationStore.longitude
    });
    userStore.setUserInfo({
      loginId: res.loginId,
      roleType: res.roleType,
      latitude: res.latitude,
      longitude: res.longitude
    });
  }
  catch(Error){
    console.log(Error);
  }
}

function findAddress(){

}


</script>

<style scoped>
.post-detail-wrapper {
  padding-bottom: 80px; /* 액션바 공간 */
  background: #fff;
}

.header-section {
  position: relative;
}

.main-image {
  width: 100%;
  height: 250px;
  object-fit: cover;
}

.header-content {
  padding: 16px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.nickname {
  font-weight: bold;
  font-size: 16px;
}

.content-section {
  padding: 0 16px;
}

.post-title {
  font-size: 22px;
  font-weight: 800;
  margin-bottom: 12px;
}

.post-body {
  font-size: 16px;
  line-height: 1.6;
  color: #333;
}

.map-placeholder {
  width: 100%;
  height: 150px;
  background: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  margin: 10px 0;
}

.stats-section {
  padding: 16px;
}

.action-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 16px;
  background: #fff;
  box-shadow: 0 -2px 8px rgba(0,0,0,0.1);
  z-index: 100;
}

.warning {
  color: #ff4d4f;
  font-size: 12px;
  margin-top: 8px;
}

.loading-state {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.post-create {
  max-width: 1000px;
  margin: 0 auto;
}

.button-wrapper {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  margin-top: 24px;
}

.post-create {
  max-width: 1000px;
  padding:10px;
  margin: 0 auto;
}


</style>