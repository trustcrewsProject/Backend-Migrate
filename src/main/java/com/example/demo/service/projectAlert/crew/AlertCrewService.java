package com.example.demo.service.projectAlert.crew;

import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.model.project.alert.crew.AlertCrew;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface AlertCrewService {
    AlertCrew toAlertCrewEntity(Long project_id, String alertContents);

    PaginationResponseDto getProjectAlertCrewList(Long projectId, int pageIndex, int itemCount);
}
