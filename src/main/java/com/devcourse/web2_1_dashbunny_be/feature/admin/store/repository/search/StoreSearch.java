package com.devcourse.web2_1_dashbunny_be.feature.admin.store.repository.search;

import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.AdminStoreListRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 가게 목록을 조회할 때 필요한 검색 기능을 추상화한 인터페이스.
 */
public interface StoreSearch {
  /**
   * 가게 목록을 페이징 처리하여 반환.
   * @param pageable 페이징 및 정렬 정보를 담고 있는 Pageable 객체.
   * @return Page<AdminStoreListRequestDto> 페이징 처리된 가게 목록 데이터를 담고 있는 Page 객체
   */
  Page<AdminStoreListRequestDto> storeSearch(Pageable pageable);
}
