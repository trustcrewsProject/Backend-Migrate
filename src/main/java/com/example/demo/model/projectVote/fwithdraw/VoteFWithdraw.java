package com.example.demo.model.projectVote.fwithdraw;

import com.example.demo.constant.ProjectFWReason;
import com.example.demo.constant.VoteStatus;
import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.project.ProjectMember;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "project_vote_fw")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VoteFWithdraw extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_vote_fw_id")
    private Long id;

    private Long project_id;

    @OneToOne
    @JoinColumn(name = "project_fwMember_id")
    private ProjectMember fwMember;

    @Column(name = "vote_status")
    @Enumerated(value = EnumType.STRING)
    private VoteStatus voteStatus;

    private int agrees;

    private int disagrees;

    private int max_vote_count;

    @Column(name = "vote_reason")
    @Enumerated(value=EnumType.STRING)
    private ProjectFWReason reason;

    @Builder
    public VoteFWithdraw(ProjectMember fwMember, int max_vote_count, Long project_id, ProjectFWReason reason){
        this.fwMember =fwMember;
        this.voteStatus = VoteStatus.VSTAT1001;
        this.agrees = 0;
        this.disagrees = 0;
        this.max_vote_count = max_vote_count;
        this.project_id = project_id;
        this.reason = reason;
    }
}
