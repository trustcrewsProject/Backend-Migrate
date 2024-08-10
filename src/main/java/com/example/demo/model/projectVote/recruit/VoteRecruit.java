package com.example.demo.model.projectVote.recruit;

import com.example.demo.constant.VoteResult;
import com.example.demo.constant.VoteStatus;
import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.project.Project;
import com.example.demo.model.projectApply.ProjectApply;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "projectVoteRecruit")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VoteRecruit extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_vote_recruit_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToOne
    @JoinColumn(name = "project_applicant_id")
    private ProjectApply projectApply;

    @Column(name = "vote_status")
    @Enumerated(value = EnumType.STRING)
    private VoteStatus voteStatus;

    private int agrees;

    private int disagrees;

    private int max_vote_count;


    @Builder
    public VoteRecruit(Project project, ProjectApply projectApply, int max_vote_count) {
        this.project = project;
        this.projectApply = projectApply;
        this.agrees = 0;
        this.disagrees = 0;
        this.max_vote_count = max_vote_count;
        this.voteStatus = VoteStatus.VSTAT1001;
    }

    public void updateProjectVoteAgrees() {
        this.agrees += 1;
    }

    public void updateProjectVoteDisagrees() {
        this.disagrees += 1;
    }

    public void endProjectVote() {
        this.voteStatus = VoteStatus.VSTAT1002;
    }

    public VoteResult getProjectVoteResult() {
        int maxCountForOne = Math.round(this.max_vote_count / 2);
        boolean isFulfilled =
                this.agrees + this.disagrees == this.max_vote_count ||
                        this.agrees > maxCountForOne ||
                        this.disagrees > maxCountForOne;

        if (!isFulfilled) {
            return VoteResult.NOT_FULFILLED;
        }

        return this.agrees > maxCountForOne ? VoteResult.AGREE : VoteResult.DISAGREE;
    }

}
