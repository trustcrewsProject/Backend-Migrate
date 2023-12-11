package com.example.demo.service.trust_score;

import com.example.demo.dto.trust_score_type.TrustScoreTypeSearchCriteria;
import com.example.demo.dto.trust_score_type.request.TrustScoreTypeCreateRequestDto;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeCreateResponseDto;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeReadResponseDto;
import com.example.demo.global.exception.customexception.TrustScoreTypeCustomException;
import com.example.demo.model.trust_score.TrustScoreType;
import com.example.demo.repository.trust_score.TrustScoreTypeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrustScoreTypeServiceImpl implements TrustScoreTypeService {

    private final TrustScoreTypeRepository trustScoreTypeRepository;

    @Override
    public List<TrustScoreTypeReadResponseDto> getAllAndReturnDto() {
        List<TrustScoreType> findAllTrustScoreType = trustScoreTypeRepository.findAll();
        return findAllTrustScoreType.stream()
                .map(TrustScoreTypeReadResponseDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrustScoreTypeReadResponseDto> getSearchResults(
            TrustScoreTypeSearchCriteria criteria) {
        return trustScoreTypeRepository.findSearchResults(criteria);
    }

    @Override
    public TrustScoreTypeReadResponseDto findByIdAndReturnDto(Long trustScoreTypeId) {
        TrustScoreType findTrustScoreType =
                trustScoreTypeRepository
                        .findById(trustScoreTypeId)
                        .orElseThrow(
                                () -> TrustScoreTypeCustomException.NOT_FOUND_TRUST_SCORE_TYPE);
        return TrustScoreTypeReadResponseDto.of(findTrustScoreType);
    }

    @Override
    public TrustScoreTypeCreateResponseDto createTrustScoreType(
            TrustScoreTypeCreateRequestDto requestDto) {

        TrustScoreType trustScoreType =
                TrustScoreType.builder()
                        .upTrustScoreType(getUpTrustScoreType(requestDto))
                        .trustScoreTypeName(requestDto.getTrustScoreTypeName())
                        .score(requestDto.getScore())
                        .gubunCode(requestDto.getGubunCode())
                        .deleteStatus(requestDto.getDeleteStatus())
                        .build();

        TrustScoreType saveTrustScoreType = trustScoreTypeRepository.save(trustScoreType);

        return TrustScoreTypeCreateResponseDto.builder()
                .id(saveTrustScoreType.getId())
                .upTrustScoreTypeName(
                        saveTrustScoreType.getUpTrustScoreType() == null
                                ? null
                                : saveTrustScoreType.getUpTrustScoreType().getTrustScoreTypeName())
                .trustGradeName(saveTrustScoreType.getTrustGradeName())
                .gubunCode(saveTrustScoreType.getGubunCode().toUpperCase())
                .score(saveTrustScoreType.getScore())
                .deleteStatus(saveTrustScoreType.getDeleteStatus().toUpperCase())
                .createDate(saveTrustScoreType.getCreateDate())
                .build();
    }

    private TrustScoreType getUpTrustScoreType(TrustScoreTypeCreateRequestDto requestDto) {
        if (requestDto.getUpTrustScoreTypeId() == null) {
            return null;
        }
        return trustScoreTypeRepository
                .findById(requestDto.getUpTrustScoreTypeId())
                .orElseThrow(() -> TrustScoreTypeCustomException.NOT_FOUND_TRUST_SCORE_TYPE);
    }
}
