package com.example.demo.service.projectAlert.crew;

import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.model.project.alert.crew.AlertCrew;
import com.example.demo.repository.projectAlert.crew.AlertCrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertCrewServiceImpl implements AlertCrewService {

    private final AlertCrewRepository alertCrewRepository;
    @Override
    public AlertCrew toAlertCrewEntity(Long project_id, String alertContents) {
        AlertCrew alertCrew = AlertCrew.builder()
                .project_id(project_id)
                .alertContents(alertContents)
                .build();
        return alertCrewRepository.save(alertCrew);
    }

    @Override
    public PaginationResponseDto getProjectAlertCrewList(Long projectId, int pageIndex, int itemCount) {
        if (pageIndex < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }
        List<AlertCrew> alertCrewList = new ArrayList<>();
        long totalItems = 0;

        try{
            totalItems = alertCrewRepository.countAlertCrewsByProject_id(projectId);
            if(totalItems > 0){
                Pageable pageable = PageRequest.of(pageIndex, itemCount);
                alertCrewList = alertCrewRepository.findAlertCrewsByProject_id(projectId, pageable);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return PaginationResponseDto.of(alertCrewList, totalItems);
    }
}
