

-- POSITION 값 넣기.
-- 포지션 값 넣기
INSERT INTO position (position_id,name) VALUES (1,'프론트엔드');
INSERT INTO position (position_id,name) VALUES (2,'백엔드');
INSERT INTO position (position_id,name) VALUES (3,'안드로이드');
INSERT INTO position (position_id,name) VALUES (4,'IOS');
INSERT INTO position (position_id,name) VALUES (5,'데브옵스');
INSERT INTO position (position_id,name) VALUES (6,'디자이너');

-- technology_stack_category 값 넣기.
-- 기술 스택 상위 카테고리 값 넣기
INSERT INTO technology_stack_category (technology_stack_category_id,name) VALUES (1,'프론트엔드');
INSERT INTO technology_stack_category (technology_stack_category_id,name) VALUES (2,'백엔드');
INSERT INTO technology_stack_category (technology_stack_category_id,name) VALUES (3,'IOS');
INSERT INTO technology_stack_category (technology_stack_category_id,name) VALUES (4,'안드로이드');
INSERT INTO technology_stack_category (technology_stack_category_id,name) VALUES (5,'기타');

-- technology_stack 값 넣기.
INSERT INTO technology_stack (technology_stack_id, name) VALUES (1,'Java');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (2,'JavaScript');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (3,'React');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (4,'Spring');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (5,'TypeScript');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (6,'Figma');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (7,'Vue');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (8,'Svelte');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (9,'Nextjs');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (10,'Kotlin');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (11,'Nodejs');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (12,'Express');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (13,'Mysql');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (14,'Mongodb');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (15,'Python');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (16,'Django');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (17,'Php');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (18,'Graphql');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (19,'Firebase');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (20,'ReactNative');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (21,'Unity');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (22,'Flutter');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (23,'Swift');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (24,'Aws');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (25,'Kubernetes');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (26,'Docker');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (27,'Git');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (28,'Zeplin');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (29,'Jest');
INSERT INTO technology_stack (technology_stack_id, name) VALUES (30,'C');

-- technology_stack_category_mapping 값 넣기.
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (1, 1, 2);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (2, 2, 1);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (3, 3, 1);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (4, 4, 2);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (5, 5, 1);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (6, 6, 5);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (7, 7, 1);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (8, 8, 1);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (9, 9, 1);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (10, 10, 4);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (11, 11, 2);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (12, 12, 2);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (13, 13, 2);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (14, 14, 2);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (15, 15, 2);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (16, 16, 2);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (17, 17, 2);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (18, 18, 2);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (19, 19, 2);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (20, 20, 2);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (21, 21, 3);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (22, 21, 4);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (23, 22, 3);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (24, 22, 4);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (25, 22, 5);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (26, 23, 3);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (27, 23, 4);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (28, 24, 3);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (29, 25, 5);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (30, 26, 5);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (31, 27, 5);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (32, 28, 5);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (33, 29, 5);
INSERT INTO technology_stack_category_mapping (technology_stack_category_mapping_id, technology_stack_id, technology_stack_category_id) VALUES (34, 30, 5);



-- trust_grade 값 넣기.
INSERT INTO trust_grade (trust_grade_id, name, minimum_score, maximum_score, create_date, update_date) VALUES (1,'1등급', 5000, 1000000, CURRENT_DATE, CURRENT_DATE);
INSERT INTO trust_grade (trust_grade_id, name, minimum_score, maximum_score, create_date, update_date) VALUES (2,'2등급', 3000, 4999, CURRENT_DATE, CURRENT_DATE);
INSERT INTO trust_grade (trust_grade_id, name, minimum_score, maximum_score, create_date, update_date) VALUES (3,'3등급', 2000, 2999, CURRENT_DATE, CURRENT_DATE);
INSERT INTO trust_grade (trust_grade_id, name, minimum_score, maximum_score, create_date, update_date) VALUES (4,'4등급', 0, 1999, CURRENT_DATE, CURRENT_DATE);

-- project_member_auth 추가
INSERT INTO project_member_auth (project_member_auth_id, project_member_auth_name, milestone_change_yn, work_change_yn) VALUES (1,'매니저', true, false);
INSERT INTO project_member_auth (project_member_auth_id, project_member_auth_name, milestone_change_yn, work_change_yn) VALUES (2,'부매니저', false, false);
INSERT INTO project_member_auth (project_member_auth_id, project_member_auth_name, milestone_change_yn, work_change_yn) VALUES (3,'구성원', false, false);

--user 테스트 넣기.
-- INSERT INTO user(user_id, email, intro, nickname, password, profile_img_src, role, position_id, create_date, update_date) VALUES (1, 'test@naver.com', 'test', 'test', 'test', null, 'USER', 1, CURRENT_DATE, CURRENT_DATE);

--trust_score_type 값 넣기
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(1, NULL, '업무 완수', NULL, NULL, 'P', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(2, NULL, '업무 미흡', NULL, NULL, 'M', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(3, NULL, '회원가입', NULL, 200, 'P', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(4, NULL, '프로젝트 탈퇴', NULL, NULL, 'M', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(5, NULL, '프로젝트 강제탈퇴', NULL, NULL, 'M', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(6, 1, '1등급 업무 완수', '1등급', 50, 'P', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(7, 1, '2등급 업무 완수', '2등급', 40, 'P', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(8, 1, '3등급 업무 완수', '3등급', 30, 'P', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(9, 1, '4등급 업무 완수', '4등급', 20, 'P', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(10, 2, '1등급 업무 미흡', '1등급', 25, 'M', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(11, 2, '2등급 업무 미흡', '2등급', 20, 'M', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(12, 2, '3등급 업무 미흡', '3등급', 15, 'M', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(13, 2, '4등급 업무 미흡', '4등급', 10, 'M', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(14, 4, '1등급 프로젝트 탈퇴', '1등급', 100, 'M', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(15, 4, '2등급 프로젝트 탈퇴', '2등급', 200, 'M', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(16, 4, '3등급 프로젝트 탈퇴', '3등급', 300, 'M', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(17, 4, '4등급 프로젝트 탈퇴', '4등급', 400, 'M', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(18, 5, '1등급 프로젝트 강제탈퇴', '1등급', 200, 'M', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(19, 5, '2등급 프로젝트 강제탈퇴', '2등급', 400, 'M', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(20, 5, '3등급 프로젝트 강제탈퇴', '3등급', 600, 'M', 'N', '2023-11-20', '2023-11-20');
INSERT INTO trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(21, 5, '4등급 프로젝트 강제탈퇴', '4등급', 800, 'M', 'N', '2023-11-20', '2023-11-20');
INSERT INTO projectMatch.trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(22, NULL, '업무 지연', NULL, NULL, 'M', 'N', '2023-12-05', '2023-12-05');
INSERT INTO projectMatch.trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(23, 22, '1등급 업무 지연', '1등급', 50, 'M', 'N', '2023-12-05', '2023-12-05');
INSERT INTO projectMatch.trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(24, 22, '2등급 업무 지연', '2등급', 40, 'M', 'N', '2023-12-05', '2023-12-05');
INSERT INTO projectMatch.trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(25, 22, '3등급 업무 지연', '3등급', 30, 'M', 'N', '2023-12-05', '2023-12-05');
INSERT INTO projectMatch.trust_score_type
(trust_score_type_id, up_trust_score_type_id, trust_score_type_name, trust_grade_name, score, gubun_code, delete_status, create_date, update_date)
VALUES(26, 22, '4등급 업무 지연', '4등급', 20, 'M', 'N', '2023-12-05', '2023-12-05');