package com.example.demo.model.projectVote.fwithdraw.history;

import com.example.demo.constant.VoteOptionDA;
import com.example.demo.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "project_vote_fw_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VoteFWithdrawHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_vote_fw_history_id")
    private Long id;

    private Long project_vote_fw_id;

    private Long voter_id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "answer")
    private VoteOptionDA answer;

    @Builder
    public VoteFWithdrawHistory(Long project_vote_fw_id, Long voter_id, VoteOptionDA answer){
        this.project_vote_fw_id = project_vote_fw_id;
        this.voter_id = voter_id;
        this.answer = answer;
    }
}
