import React, { useState, useCallback, useEffect } from 'react';
import { Input } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import debounce from 'lodash/debounce';

interface SearchFormProps {
  onSearch: (value: string) => void;
}

const SearchForm = ({ onSearch }: SearchFormProps) => {
  const [searchQuery, setSearchQuery] = useState('');

  // 1. 디바운스 함수 생성 (useCallback을 써야 리렌더링 시 함수가 유지됨)
  const debouncedSearch = useCallback(
    debounce((value: string) => {
      onSearch(value);
    }, 300),
    [onSearch]
  );

  // 2. 입력 이벤트 핸들러 (v-model:value 역할)
  const handleInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setSearchQuery(value);
    debouncedSearch(value.trim());
  };

  // 3. 엔터키 핸들러
  const handleEnter = () => {
    debouncedSearch.cancel(); // 예약된 디바운스 취소
    onSearch(searchQuery.trim());
  };

  // 4. 언마운트 시 클린업 (onBeforeUnmount 역할)
  useEffect(() => {
    return () => {
      debouncedSearch.cancel();
    };
  }, [debouncedSearch]);

  return (
    <Input
      placeholder="상품명, 공동구매명, 카테고리 등을 검색하세요"
      value={searchQuery}
      onChange={handleInput}
      onPressEnter={handleEnter}
      prefix={<SearchOutlined style={{ color: 'rgba(0,0,0,.45)' }} />}
      style={{ borderRadius: '20px' }}
    />
  );
};

export default SearchForm;