package com.example.demo.service.trust_score;

import com.example.demo.dto.trust_score_type.TrustScoreTypeSearchCriteria;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeReadResponseDto;
import com.example.demo.global.exception.customexception.TrustScoreTypeCustomException;
import com.example.demo.model.trust_score.TrustScoreType;
import com.example.demo.repository.trust_score.TrustScoreTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrustScoreTypeServiceImpl implements TrustScoreTypeService {
    private final TrustScoreTypeRepository trustScoreTypeRepository;

    public List<TrustScoreTypeReadResponseDto> getAllAndReturnDto() {
        List<TrustScoreType> findAllTrustScoreType = trustScoreTypeRepository.findAll();
        return findAllTrustScoreType.stream().map(TrustScoreTypeReadResponseDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrustScoreTypeReadResponseDto> getSearchResults(
            TrustScoreTypeSearchCriteria criteria) {
        return trustScoreTypeRepository.findSearchResults(criteria);
    }

    @Override
    public TrustScoreTypeReadResponseDto findByIdAndReturnDto(Long trustScoreTypeId) {
        TrustScoreType findTrustScoreType = trustScoreTypeRepository.findById(trustScoreTypeId)
                .orElseThrow(() -> TrustScoreTypeCustomException.NOT_FOUND_TRUST_SCORE_TYPE);
        return TrustScoreTypeReadResponseDto.of(findTrustScoreType);
    }

}
