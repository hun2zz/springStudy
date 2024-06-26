package com.study.springStudy.springmvc.chap03.repository;

import com.study.springStudy.springmvc.chap03.entity.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class ScoreSpringJdbcRepository implements ScoreRepository{
    private final JdbcTemplate template; // 필수


    @Override
    public boolean save(Score score) {
        String sql = "INSERT INTO tbl_score " +
                "(stu_name, kor, eng, math, total, average, grade) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return template.update(sql, score.getStuName(), score.getKor(), score.getEng(), score.getMath(), score.getTotal()
        ,score.getAverage(), score.getGrade().toString()) == 1;
    }

    @Override
    public List<Score> findAll(String sort) {
        String sql = "SELECT * FROM tbl_score " +sortCondition(sort);
        return template.query(sql, (rs, n) -> new Score(rs));
    }

    @Override
    public Score findOne(long stuNum) {
        String sql = "SELECT * FROM tbl_score WHERE stu_num = ?";
        return template.queryForObject(sql, (rs,n) -> new Score(rs), stuNum);
    }

    @Override
    public boolean delete(long sn) {
        String sql = "DELETE FROM tbl_score WHERE stu_num = ?";

        return template.update(sql, sn) == 1;
    }
    private String sortCondition(String sort) {
        String sortSql = "ORDER BY ";
        switch (sort) {
            case "num" :
                sortSql += "stu_num";
                break;
            case"name":
                sortSql += "stu_name";
                break;
            case"avg":
                sortSql += "average DESC";
                break;
        }
        return sortSql;
    }
    @Override
    public int[] findRankByStuNum(long stuNum) {
        String sql = "SELECT A.stu_num, A.rank, A.cnt" +
                " FROM (SELECT *, " +
                "           RANK() OVER (ORDER BY average DESC) AS rank, " +
                "           COUNT(*) OVER() AS cnt" +
                "       FROM tbl_score) A " +
                "WHERE A.stu_num = ?";
        return template.queryForObject(sql, (rs, n) -> new int[] {
                rs.getInt("rank"),
                rs.getInt("cnt")
        }, stuNum);
    }

    @Override
    public boolean updateScore(Score s) {// 자동으로 통합점수, 평균, grade가 바뀌는 게 아니기 때문에 수정해줘야함.
        String sql = "UPDATE tbl_score SET kor = ?, eng = ?, math = ?, total = ?, average = ?, grade = ? WHERE stu_num = ?";

        return template.update(sql, s.getKor(), s.getEng(), s.getMath(), s.getTotal(), s.getAverage(), s.getGrade().toString(), s.getStuNum()) == 1;
    }
}
