<template>
    <PageWrapper>
  <div class="post-create">
    <a-form
      :model="form"
      layout="vertical"
      @finish="handleSubmit"
    >
      <!-- 기본 정보 -->
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
            label="구매 일시"
            name="purchaseAt"
            :rules="[
              { required: true, message: '구매 일시를 선택해주세요.' }
            ]"
          >
            <a-date-picker
              v-model:value="form.purchaseAt"
              show-time
              format="YYYY-MM-DD"
              style="width: 100%"
            />
          </a-form-item>
        </a-col>

      </a-row>

      <!-- 상품 정보 -->
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
            label="구매 타입"
            name="purchaseType"
            :rules="[
              { required: true, message: '구매 타입을 선택해주세요.' }
            ]"
          >
            <a-radio-group v-model:value="form.purchaseType">
              <a-radio :value="PurchaseType.OFFLINE">
                오프라인
              </a-radio>
              <a-radio :value="PurchaseType.ONLINE">
                온라인
              </a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>

        <a-col :span="24" v-if="form.purchaseType ==PurchaseType.ONLINE">
          <a-form-item
            label="상품 구매 URL"
            name="purchaseUrl"
            :rules="[
              { required: false, message: '상품 URL을 입력해주세요.' }
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
              { required: true, message: '구매 금액을 입력해주세요.' }
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

      </a-row>

      <!-- 약속 정보 -->
     <a-form-item
        name="appointment.place.placeName"
        :rules="[
            { required: true, message: '약속 장소를 입력해주세요.' }
        ]"
        >
        <template #label>
            <div class="place-label">
            <span>약속 장소</span>
            <AddressSearch @select="addressResult"></AddressSearch>
            </div>
        </template>

        <a-input
            v-model:value="form.appointment.place.address.primaryAddress"
            placeholder="주소 찾기를 눌러주세요."
            
        />
     </a-form-item>

     <a-form-item
        v-if="form.appointment.place.address.primaryAddress"
        name="appointment.place.detailAddress"
        :rules="[
            { required: true, message: '상세 주소를 입력해주세요.' }
        ]"
        >

        <a-input 
            v-model:value="form.appointment.place.address.detailAddress"
            placeholder="상세 장소를 입력해주세요."
        />
     </a-form-item>

     <a-form-item
        name="appointment.place.placeName"
        :rules="[
            { required: true, message: '만나는 시간을 입력해주세요.' }
        ]"
        >
          <a-form-item
            label="약속 일정"
            name="purchaseAt"
            :rules="[
              { required: true, message: '만날 일정을 선택해주세요.' }
            ]"
          >
            <a-date-picker
              v-model:value="form.purchaseAt"
              show-time
              format="YYYY-MM-DD HH:mm"
              style="width: 100%"
            />
          </a-form-item>
        </a-form-item>

    <a-form-item label="이미지">
      <input
        type="file"
        multiple
        accept="image/*"
        @change="handleFileChange"
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
          @click="handleSubmit"
        >
          등록
        </a-button>
      </div>
    </a-form>
</div>
  </PageWrapper>
</template>

<script setup lang="ts">
import { ref,  onMounted,reactive,watch } from 'vue';
import { useRouter } from 'vue-router';
import { commonGet, commonPostFile } from '@/utils/ShareBuyUtil'; // 기존 유틸 활용
import { message } from 'ant-design-vue';
import { useUserStore } from '@/store/user';
import PageWrapper from '@/views/PageWrapper.vue';
import { useLocationStore } from '@/store/location';
import { PurchaseType,categoryOptions,Category, PostStatus } from '@/views/new/registerNew';
import AddressSearch from './AddressSearch.vue';

const locationStore = useLocationStore();
const userStore = useUserStore();


interface PostForm {
  title: string;
  content: string;
  appointment: {
    place: {
      address:{
        primaryAddress:string;
        detailAddress:string;
        zipCode:string;
      },
    };
    appointmentTime: string;
  };
  purchaseType:PurchaseType;
  purchasePlace: string;
  productCode: string;
  purchaseUrl: string;
  totalPrice: number | 0;
  perPrice: number | 0;
  purchaseAt: string;
  postStatus:PostStatus;
  imagePath: File[];
  maxParticipants: number | null;
  category: Category | undefined;
}


const router = useRouter();
const loading = ref(false);

const form = reactive<PostForm>({
  title: '',
  content: '',
  appointment: {
    place: {
      address:{
        primaryAddress:'',
        detailAddress:'',
        zipCode:''
      },
    },
    appointmentTime: ''
  },
  purchaseType:PurchaseType.OFFLINE,
  purchasePlace: '',
  productCode: '',
  purchaseUrl: '',
  totalPrice: 0,
  perPrice: 0,
  purchaseAt: '',
  postStatus:PostStatus.RECRUITING,
  imagePath: [],
  maxParticipants: null,
  category: undefined
});




const handleSubmit = async () => {
  loading.value = true;

  try {
  const request = {
    title: form.title,
    content: form.content,
    appointment: {
      place: {
        address: {
          primaryAddress: form.appointment.place.address.primaryAddress,
          detailAddress: form.appointment.place.address.detailAddress,
          zipCode: form.appointment.place.address.zipCode
        }
      },
      appointmentTime: form.appointment.appointmentTime
    },
    purchaseType: form.purchaseType,
    purchasePlace: form.purchasePlace,
    productCode: form.productCode,
    purchaseUrl: form.purchaseUrl,
    totalPrice: form.totalPrice,
    perPrice: form.perPrice,
    purchaseAt: form.purchaseAt,
    postStatus: PostStatus.RECRUITING,
    maxParticipants: form.maxParticipants,
    category: form.category
  };

  const formData = new FormData();

  formData.append(
    'request',
    new Blob(
      [JSON.stringify(request)],
      { type: 'application/json' }
    )
  );

  form.imagePath.forEach((file) => {
    formData.append('imagePath', file);
  });
  

  // 실제 API 연결
  const response = await commonPostFile('/post/add', formData);

    if(response.result){
      message.success('게시글이 등록되었습니다.');
    }
    await router.push('/board');

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


function addressResult(data:any){
  console.log(data);
  if(data){
    form.appointment.place.address.primaryAddress = data.address;
    form.appointment.place.address.zipCode = data.zonecode;
  }
}

const handleFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement;

  if (target.files) {
    form.imagePath = Array.from(target.files);
  }
};

watch(
  [() => form.totalPrice, () => form.perPrice],
  ([totalPrice, perPrice]) => {
    if (totalPrice > 0 && perPrice > 0) {
      form.maxParticipants = Math.floor(totalPrice / perPrice);
    } else {
      form.maxParticipants = null;
    }
  }
);

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