-- users
select u.id,
       u.loggin,
       u.password,
       u.nickname,
       u.full_name,
       u.email,
       u.phone,
       u.created_at,
       u.updated_at,
       u.upd_user_id,
       u.deleted_at,
       u.del_user_id,
       u.is_deleted
from users u
where u.loggin = :loggin
  and u.is_deleted = false;

--roles
select r.id,
       r.name
from roles r
         inner join (select user_id,
                            role_id
                     from user_roles
                     where user_id = :user_id) ur
                    on r.id = ur.user_id;

--courses
select *
from courses c
         left join users au
                   on c.author_id = au.id
         left join course_rating cr
                   on c.id = cr.course_id
where c.title like :title
  and au.full_name like :full_name
  and cr.avg_score between :min_score and :max_score
  and c.duration between :min_duration and :max_duration
  and c.is_deleted = false;

--user courses
select *
from user_courses uc
         inner join courses c
                    on c.id = uc.course_id
                        and uc.user_id = :user_id
                        and c.is_deleted = false
         left join course_statuses cs
                   on uc.course_status_id = cs.id;

--course modules
select *
from modules m
         inner join course_modules cm
                    on cm.module_id = m.id
                        and cm.course_id = :course_id
                        and m.is_deleted = false;

--module themes
select *
from themes t
         inner join module_themes mt
                    on t.id = mt.theme_id
                        and mt.module_id = :module_id
                        and t.is_deleted = false;
