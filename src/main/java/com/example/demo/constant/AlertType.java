package com.example.demo.constant;

/** AlertType
 * RECRUIT: 모집신청 알림 >> 프로젝트 지원시 생성
 * WORK: 작업 알림 >> 업무 완료시 생성
 * WITHDRAWAL: 크루 탈퇴 신청 알림 >> 크루가 탈퇴신청시 생성
 * FORCED_WITHDRAWAL: 크루 강제탈퇴 신청 알림 >> 크루가 다른 크루 강제탈퇴 요청시 생성
 * CREW_UPDATE: 각종 크루 업데이트 알림 >> 모집/탈퇴/강제탈퇴 알림 confirm으로 인해 기존 크루에 변동이 생긴 경우 생성
 */
public enum AlertType {
    RECRUIT,
    WORK,
    ADD,
    CREW_UPDATE,
    WITHDRAWAL,
    FORCED_WITHDRAWAL
}
