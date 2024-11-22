package com.devcourse.web2_1_dashbunny_be.domain.owner;

import com.devcourse.web2_1_dashbunny_be.domain.owner.role.CategoryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="categorys")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Categorys {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)  // 명시적으로 매핑
    private Long categoryId;

    @Column(nullable = false) // 필수 필드
    private CategoryType categoryType;

    @ManyToOne
    @JoinColumn(name="store_id",nullable = false)
    private StoreManagement storeManagement;
    public Categorys(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}
