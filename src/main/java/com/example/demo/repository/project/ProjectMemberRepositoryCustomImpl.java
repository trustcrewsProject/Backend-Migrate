package com.example.demo.repository.project;

import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.model.project.QProjectMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class ProjectMemberRepositoryCustomImpl implements ProjectMemberRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QProjectMember qProjectMember = QProjectMember.projectMember;

    @Override
    public void updateProjectMemberAuth(Long projectMemberId, ProjectMemberAuth projectMemberAuth) {
        jpaQueryFactory
                .update(qProjectMember)
                .set(qProjectMember.projectMemberAuth, projectMemberAuth)
                .where(qProjectMember.id.eq(projectMemberId))
                .execute();
    }

    @Override
    public Long countOtherProjectManagers(Long projectId, Long projectMemberId) {
        return jpaQueryFactory
                .select(qProjectMember.count())
                .from(qProjectMember)
                .where(
                        qProjectMember.project.id.eq(projectId),
                        qProjectMember.id.ne(projectMemberId),
                        qProjectMember.projectMemberAuth.eq(ProjectMemberAuth.PAUTH_1001)
                )
                .fetchOne();
    }
}
