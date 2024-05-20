package com.study.springStudy.springmvc.chap03.service;

import com.study.springStudy.springmvc.chap03.dto.ScoreDetailResponseDto;
import com.study.springStudy.springmvc.chap03.dto.ScoreListResponseDto;
import com.study.springStudy.springmvc.chap03.dto.ScoreModifyRequestDto;
import com.study.springStudy.springmvc.chap03.dto.ScorePostDto;
import com.study.springStudy.springmvc.chap03.entity.Score;
import com.study.springStudy.springmvc.chap03.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
     컨트롤러와 레파지퇼 사이에 위치하여 중간 처리를 담당

     -- 트랜 잭션 처리 ,데이터 가공 처리....

     - 의존 관계
     Controller - Service- > Repository 순으로 의존한다.
 */
@RequiredArgsConstructor
@Service
public class ScoreService {

    private final ScoreRepository repository;

    //목록 조회 중간처리
    // - DB에서 조회한 성적 정보 목록은 민감한 정보를 모두 포함하고 있는데,
    // 이를 컨트롤러에게 직접 넘기면 보안상 불필요한 정보까지 화면으로 넘어갈 수 있음.
    // 그렇기에 숨길 건 숨기고 뺄 건 빼는 데이터 가공을 여기서 처리 함.
   public List<ScoreListResponseDto> getList(String sort) {
        List<Score> scoreList = repository.findAll(sort);
                return scoreList.stream().map(ScoreListResponseDto::new).collect(Collectors.toList());
    }


    //저장 중간처리
    public boolean insert (ScorePostDto s) {
       return repository.save(new Score(s));
    }

    //삭제 중간처리
    public boolean deleteScore(long stuNum) {
       return repository.delete(stuNum);
    }

    //개별 조회 중간처리
    public ScoreDetailResponseDto retrieve(long stuNum) {
        Score scoreL = repository.findOne(stuNum);
//        model.addAttribute("s", scoreL);
        int[] result= repository.findRankByStuNum(stuNum);


        ScoreDetailResponseDto scoreDetailResponseDto = new ScoreDetailResponseDto(scoreL, result[0], result[1]);
        return scoreDetailResponseDto;

    }


    //수정 완료를 위해서 서비스 클래스에서
    // dto를 entity로 변환하는 작업.
    public void update(ScoreModifyRequestDto dto) {
       repository.updateScore(new Score(dto));
    }

    // 개별 수정 처리
}
