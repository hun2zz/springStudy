package com.study.springStudy.springmvc.chap04.comon;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// 페이지 화면 렌더링에 필요한 정보들을 계산~ ^_^
@Getter @ToString
@EqualsAndHashCode
public class PageMaker {

    //한 화면에 페이지를 몇개씩 배치할 것인지?
    private static final int PAGE_COUNT = 10;


    //페이지 시작번호와 끝번호
    private  int begin, end, finalPage;

    //현재 페이지 정보
    private Page pageInfo;
    //총 게시물 수
    private int totalCount;

//    private boolean click;

    // 이전 , 다음버튼 활성화 여부
    private boolean prev, next;

    public PageMaker(Page page, int totalCount) {
        this.pageInfo = page;
        this.totalCount = totalCount;
        makePageInfo();
    }
    //페이지 생성에 필요한 데이터를 만드는 알고리즘
    private void makePageInfo() {

        //1. end 값을 먼저 계산
        /*
            지금 사용자가 7 페이지를 보고 있다면
            페이지 구간 1~10 구간

            지금 사용자가 24페이지를 보고 있다면
            페이지 구간은 21~30 구간

            5개씩 페이지를 배치하는 경우는 또 다름
            7페이지인 경우 : 6~10 구간이고,
            24페이지인 경우 : 21~25 구간임.



             / 공식: (올림 (현재 사용자가 위치한 페이지넘버 / 한 화면에 보여줄 페이지 수)) * 한 화면에 보여줄 페이지 수
                                    7 / 10 하면 0.7 올림하면 1 * 10 은 10임.
                                    24 페이지면
                                    24/ 10 2.4 올림하면 3 * 10 하면 30.

         */
        this.end = (int)Math.ceil(pageInfo.getPageNo() / (double)PAGE_COUNT) * PAGE_COUNT;
//                              int / int 이므로 한 쪽을 double로 형 변환.
//                              여기서 올림처리하면 (i).0 이 나오는데, this.end는 int 타입이므로
//                              마지막에서 int로 형변환을 다시 해줌.


        //2. begin 구하기
        this.begin = this.end  - PAGE_COUNT + 1;

        //3. db에 저장된 값에서 마지막에 도달했을 때 end 값 보정을 해야함
        /*
            총 게시물이 237개이고 한 화면에 게시물을 10개씩 배치하고 있다면,
            1~10 페이지 구간에는 게시물이 총 100개가 들어감
            11~20 페이지 구간에는 또 100개가 들어감
            21~30 페이지 구간에는 37개가 들어갈 것임.

            -> 그래서 과연 마지막 구간에서 end 값이 30이 맞는가 ?
            -> 실제로는 마지막 페이지 구간이 24로 보정되어야 함.

            //마지막 페이지 번호를 구하는 공식

            게시물이 351개 한 페이지당 게시물 10개씩 배치
            끝페이지 번호 36페이지

            공식은
              올림 ( 총 게시물 수 / 한 페이지당 배치할 게시물 수 )

         */

        this.finalPage = (int)Math.ceil((double)totalCount/ pageInfo.getAmount());

        //마지막 구간에서 end 값을 finalPage 값으로 보정함.
        if(finalPage < this.end) {
            this.end = finalPage;
        }

        // 4. 이전버튼 활성화 여부
        this.prev = begin != 1;

        // 5. 다음 버튼 활성화 여부
        this.next = begin < finalPage;


    }

}
