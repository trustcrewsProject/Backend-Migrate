package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.TrustGradeRequestDto;
import com.example.demo.model.TrustGrade;
import com.example.demo.repository.TrustGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
public class TrustGradeService {

    @Autowired private TrustGradeRepository trustGradeRepository;

    public ResponseDto<?> create(TrustGradeRequestDto dto){
        try{
            if(trustGradeRepository.existsByName(dto.getName())){
                return ResponseDto.setFailed("이미 존재하는 신뢰 점수 설정입니다.");
            }
        }catch(Exception e){
            return ResponseDto.setFailed("Data Base Error!");
        }

        TrustGrade trustGrade = new TrustGrade(dto);

        try{
            trustGradeRepository.save(trustGrade);
        }catch(Exception exception){
            return ResponseDto.setFailed("Data Base Error!");
        }

        return ResponseDto.setSuccess("신뢰 등급 설정을 생성하였습니다.", null);
    }

    public ResponseDto<?> update(TrustGradeRequestDto dto){
        try{
            if(trustGradeRepository.existsByName(dto.getName())){
                return ResponseDto.setFailed("이미 존재하는 신뢰 등급 설정입니다.");
            }
        }catch (Exception exception){
            ResponseDto.setFailed("데이터베이스 에러");
        }

        TrustGrade trustGrade = null;

        try{
            trustGrade = trustGradeRepository.findById(dto.getId()).get();
            trustGrade.setName(dto.getName());
            trustGrade.setScore(dto.getScore());
        }catch(Exception exception){
            return ResponseDto.setFailed("Data Base Error!");
        }

        return ResponseDto.setSuccess("신뢰 등급 설정을 변경 성공했습니다.", null);
    }

    public ResponseDto<?> delete(Long trustGradeId){
        try{
            TrustGrade trustGrade = trustGradeRepository.findById(trustGradeId).orElseThrow(EntityNotFoundException::new);
            trustGradeRepository.delete(trustGrade);

            return ResponseDto.setSuccess("신뢰 등급 설정을 삭제하였습니다.", null);
        }catch(EntityNotFoundException exception){
            return ResponseDto.setFailed("신뢰 등급 설정이 존재하지 않습니다.");
        }catch (Exception exception){
            return ResponseDto.setFailed(exception.getMessage());
        }
    }
}
