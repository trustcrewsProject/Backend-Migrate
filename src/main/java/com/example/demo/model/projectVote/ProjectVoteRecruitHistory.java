package com.example.demo.model.projectVote;

import com.example.demo.constant.VoteAnswerYN;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "project_vote_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProjectVoteRecruitHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_vote_history_id")
    private Long id;

    private Long project_vote_id;

    private Long voter_id;

    private VoteAnswerYN answerYN;

    private String create_date;

}
