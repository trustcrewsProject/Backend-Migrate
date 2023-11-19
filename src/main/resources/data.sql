

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
INSERT INTO technology_stack_category (technology_stack_category_id,name) VALUES (3,'안드로이드');
INSERT INTO technology_stack_category (technology_stack_category_id,name) VALUES (4,'IOS');
INSERT INTO technology_stack_category (technology_stack_category_id,name) VALUES (5,'데브옵스');
INSERT INTO technology_stack_category (technology_stack_category_id,name) VALUES (6,'디자이너');

-- technology_stack 값 넣기.
-- 기술 스택 프론트엔드 넣기
INSERT INTO technology_stack (technology_stack_id, name, technology_stack_category_id) VALUES (1,'REACT', 1);
INSERT INTO technology_stack (technology_stack_id, name, technology_stack_category_id) VALUES (2,'TYPESCRIPT', 1);
INSERT INTO technology_stack (technology_stack_id, name, technology_stack_category_id) VALUES (3,'JAVASCRIPT', 1);
INSERT INTO technology_stack (technology_stack_id, name, technology_stack_category_id) VALUES (4,'VUE', 1);
INSERT INTO technology_stack (technology_stack_id, name, technology_stack_category_id) VALUES (5,'NEXTJS', 1);

-- 기술 스택 백엔드 넣기
INSERT INTO technology_stack (technology_stack_id, name, technology_stack_category_id) VALUES (6,'NODEJS', 2);
INSERT INTO technology_stack (technology_stack_id, name, technology_stack_category_id) VALUES (7,'JAVA', 2);
INSERT INTO technology_stack (technology_stack_id, name, technology_stack_category_id) VALUES (8,'SPRING', 2);
INSERT INTO technology_stack (technology_stack_id, name, technology_stack_category_id) VALUES (9,'KOTLIN', 2);
INSERT INTO technology_stack (technology_stack_id, name, technology_stack_category_id) VALUES (10,'NESTJS', 2);

-- 기술 스택 안드로이드 넣기
INSERT INTO technology_stack (technology_stack_id, name, technology_stack_category_id) VALUES (11,'ANDROID', 3);

-- 기술 스택 IOS 넣기
INSERT INTO technology_stack (technology_stack_id, name, technology_stack_category_id) VALUES (12,'SWIFT', 4);

-- 기술 스택 데브옵스 넣기
INSERT INTO technology_stack (technology_stack_id, name, technology_stack_category_id) VALUES (13,'JENKINS', 5);
INSERT INTO technology_stack (technology_stack_id, name, technology_stack_category_id) VALUES (14,'KUBERNETES', 5);

-- 기술 스택 디자이너 넣기
INSERT INTO technology_stack (technology_stack_id, name, technology_stack_category_id) VALUES (15,'FIGMA', 6);

-- trust_grade 값 넣기.
INSERT INTO trust_grade (trust_grade_id, name, score, create_date, update_date) VALUES (1,'1등급', 7000, CURRENT_DATE, CURRENT_DATE);
INSERT INTO trust_grade (trust_grade_id, name, score, create_date, update_date) VALUES (2,'2등급', 5000, CURRENT_DATE, CURRENT_DATE);
INSERT INTO trust_grade (trust_grade_id, name, score, create_date, update_date) VALUES (3,'3등급', 3000, CURRENT_DATE, CURRENT_DATE);
INSERT INTO trust_grade (trust_grade_id, name, score, create_date, update_date) VALUES (4,'4등급', 2000, CURRENT_DATE, CURRENT_DATE);

-- project_member_auth 추가
INSERT INTO project_member_auth (project_member_auth_id, project_member_auth_name, milestone_change_yn, work_change_yn) VALUES (1,'매니저', true, false);
INSERT INTO project_member_auth (project_member_auth_id, project_member_auth_name, milestone_change_yn, work_change_yn) VALUES (2,'부매니저', false, false);
INSERT INTO project_member_auth (project_member_auth_id, project_member_auth_name, milestone_change_yn, work_change_yn) VALUES (3,'구성원', false, false);

--user 테스트 넣기.
INSERT INTO user(user_id,email,intro,nickname,password,profile_img_src,role,trust_score,position_id,trust_grade_id, create_date, update_date) VALUES (1,'test@naver.com','test','test','test',null,'USER',0,1,4,CURRENT_DATE, CURRENT_DATE);