package com.example.demo.dto.trust_score_type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrustScoreTypeSearchCriteria {
    private Boolean isDeleted;
    private Boolean isParentType;
    private List<String> trustGrade;
    private List<Long> parentTypeId;
    private String gubunCode;
}
