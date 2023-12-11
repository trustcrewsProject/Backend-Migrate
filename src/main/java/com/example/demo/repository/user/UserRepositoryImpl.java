package com.example.demo.repository.user;

import com.example.demo.model.technology_stack.QTechnologyStack;
import com.example.demo.model.user.QUser;
import com.example.demo.model.user.QUserTechnologyStack;
import com.example.demo.model.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public User fetchUserDetailsForUpdate(Long userId) {
        QUser qUser = QUser.user;
        QUserTechnologyStack qUserTechnologyStack = QUserTechnologyStack.userTechnologyStack;
        QTechnologyStack qTechnologyStack = QTechnologyStack.technologyStack;

        return jpaQueryFactory
                .selectFrom(qUser)
                .leftJoin(qUser.techStacks, qUserTechnologyStack)
                .fetchJoin()
                .leftJoin(qUserTechnologyStack.technologyStack, qTechnologyStack)
                .fetchJoin()
                .where(qUser.id.eq(userId))
                .fetchOne();
    }
}
