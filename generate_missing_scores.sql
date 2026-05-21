-- 为缺少成绩的学生生成随机成绩数据
-- 该脚本会为所有缺少成绩记录的学生-课程组合插入随机成绩

INSERT INTO student_course (student_id, course_id, name, score, point, credits, term, year)
SELECT
    s.id as student_id,
    c.id as course_id,
    c.name,
    ROUND(RAND() * 100) as score,
    CASE
        WHEN ROUND(RAND() * 100) >= 60 THEN ROUND((ROUND(RAND() * 100)/10 - 5), 2)
        ELSE 0
    END as point,
    CASE
        WHEN ROUND(RAND() * 100) >= 60 THEN c.credits
        ELSE 0
    END as credits,
    c.term,
    c.year
FROM student s
INNER JOIN course c ON s.profession = c.profession
WHERE NOT EXISTS (
    SELECT 1 FROM student_course sc
    WHERE sc.student_id = s.id AND sc.name = c.name
);
