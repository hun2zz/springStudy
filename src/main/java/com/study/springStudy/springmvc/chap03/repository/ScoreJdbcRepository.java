package com.study.springStudy.springmvc.chap03.repository;

import com.study.springStudy.springmvc.chap03.entity.Score;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//@Component
public class ScoreJdbcRepository implements ScoreRepository{
    private String url = "jdbc:mariadb://localhost:3306/spring5";
    private String username = "root";
    private String password = "mariadb";

    public ScoreJdbcRepository() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(Score score) {
        try (Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "INSERT INTO tbl_score " +
                    "(stu_name, kor, eng, math, total, average, grade) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, score.getStuName());
            pstmt.setInt(2, score.getKor());
            pstmt.setInt(3, score.getEng());
            pstmt.setInt(4, score.getMath());
            pstmt.setInt(5, score.getTotal());
            pstmt.setDouble(6, score.getAverage());
            pstmt.setString(7, score.getGrade().toString());
            int result = pstmt.executeUpdate();
            if (result == 1 ) return true;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Score> findAll(String sort) {
        List<Score> scoreList = new ArrayList<>();
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM tbl_score " +sortCondition(sort);
            System.out.println("sql = " + sql);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Score score = new Score(resultSet);
                scoreList.add(score);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return scoreList;
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
    public Score findOne(long stuNum) {
        List<Score> scoreList = new ArrayList<>();
        try (Connection conn = connect()){
            String sql = "SELECT * FROM tbl_score WHERE stu_num = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, stuNum);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Score score = new Score(resultSet);
                scoreList.add(score);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return scoreList.get(0);
    }

    @Override
    public int[] findRankByStuNum(long stuNum) {
        return new int[0];
    }

    @Override
    public boolean delete(long sn) {
        try (Connection conn = connect()){
            String sql = "DELETE FROM tbl_score WHERE stu_num = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, sn);
            int i = preparedStatement.executeUpdate();

            if(i == 1) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    @Override
//    public void removeOne(long stuNum) {
//        try(Connection conn = connect()) {
//            String sql = "DELETE FROM tbl_score WHERE stu_num = ?";
//            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setLong(1, stuNum);
//            preparedStatement.executeUpdate();
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url,username, password);
    }
}
