package com.devcourse.web2_1_dashbunny_be.feature.admin.admin.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.Categorys;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.CategoryType;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.admin.admin.dto.CategoryCountDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.admin.dto.UserInfoResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.CategoryRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryCountService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryCountDto getCategoryCount() {
            List<Object[]> results = categoryRepository.countByCategoryType();

            // Map<CategoryType, Long> 형태로 변환
            Map<CategoryType, Long> categoryCounts = results.stream()
                    .collect(Collectors.toMap(
                            result -> (CategoryType) result[0],
                            result -> (Long) result[1]
                    ));

            // CategoryCountDto에 매핑
            CategoryCountDto categoryCountDto = CategoryCountDto.builder()
                    .korean(categoryCounts.getOrDefault(CategoryType.KOREAN, 0L).intValue())
                    .japanese(categoryCounts.getOrDefault(CategoryType.JAPANESE, 0L).intValue())
                    .western(categoryCounts.getOrDefault(CategoryType.WESTERN, 0L).intValue())
                    .chinese(categoryCounts.getOrDefault(CategoryType.CHINESE, 0L).intValue())
                    .asian(categoryCounts.getOrDefault(CategoryType.ASIAN, 0L).intValue())
                    .lateNightFood(categoryCounts.getOrDefault(CategoryType.LATENIGHTFOOD, 0L).intValue())
                    .koreanSnacks(categoryCounts.getOrDefault(CategoryType.KOREANSNACKS, 0L).intValue())
                    .chicken(categoryCounts.getOrDefault(CategoryType.CHICKEN, 0L).intValue())
                    .pizza(categoryCounts.getOrDefault(CategoryType.PIZZA, 0L).intValue())
                    .barbecue(categoryCounts.getOrDefault(CategoryType.BARBECUE, 0L).intValue())
                    .cafeDessert(categoryCounts.getOrDefault(CategoryType.CAFEDESSERT, 0L).intValue())
                    .fastFood(categoryCounts.getOrDefault(CategoryType.FASTFOOD, 0L).intValue())
                    .build();

            return categoryCountDto;

    }
    public UserInfoResponseDto getUserOwnerCount(){
        List<Object[]> results = userRepository.countByUserOwnerCount();
        Map<String, Long> userOwnerCounts = results.stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0],
                        result -> (Long) result[1]
                ));
        return UserInfoResponseDto.builder()
                .userCount(userOwnerCounts.getOrDefault("ROLE_USER", 0L).intValue())
                .ownerCount(userOwnerCounts.getOrDefault("ROLE_OWNER", 0L).intValue()).build();
    }

}
