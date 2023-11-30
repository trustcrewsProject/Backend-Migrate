package com.example.demo.service.work;

import com.example.demo.global.exception.customexception.WorkCustomException;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import com.example.demo.repository.work.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService{
    private final WorkRepository workRepository;

    public Work findById(Long id){
        return workRepository.findById(id).orElseThrow(() -> WorkCustomException.NOT_FOUND_WORK);
    }

    @Override
    public List<Work> findWorksByProject(Project project) {
        return workRepository
                .findWorksByProject(project)
                .orElseThrow(() -> WorkCustomException.NOT_FOUND_WORK);
    }

    public Work findLastCompleteWork(Project project, User user, Boolean completeStatus) {
        return workRepository.findFirstByProjectAndAssignedUserIdAndCompleteStatusOrderByIdDesc(project, user, completeStatus)
                .orElseThrow(() -> WorkCustomException.NOT_FOUND_WORK);
    }


}
