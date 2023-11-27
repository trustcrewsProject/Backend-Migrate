package com.example.demo.repository.trust_score;

public interface TrustScoreTypeRepositoryCustom {
    /**
     * 프로젝트가 아닌 경우 점수조회
     *
     * @param trustScoreTypeId
     * @return
     */
    int getScore(Long trustScoreTypeId);

    /**
     * 프로젝트에 해당하는 점수 조회
     *
     * @param trueScoreTypeId
     * @return
     */
    int getScoreByProject(Long projectId, Long trueScoreTypeId);
}