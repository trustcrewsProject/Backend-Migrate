package com.example.demo.repository.user;

import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProjectHistoryRepository
        extends JpaRepository<UserProjectHistory, Long>, UserProjectHistoryRepositoryCustom {

    Optional<UserProjectHistory> findUserProjectHistoryByProjectAndUser(Project project, User user);
}
