package com.example.demo.service.user;

import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserProjectHistoryService {

    public UserProjectHistory toUserProjectHistoryEntity(User user, Project project);

    public UserProjectHistory save(UserProjectHistory userProjectHistory);
}
