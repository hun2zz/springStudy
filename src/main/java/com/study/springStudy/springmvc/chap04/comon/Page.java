package com.study.springStudy.springmvc.chap04.comon;


import lombok.*;

@Getter @ToString @EqualsAndHashCode
@AllArgsConstructor
public class Page {
    private int pageNo; // 클라이언트가 요청한 페이지 번호
    private int amount; // 클라이언트가 요청한 한 페이지당 게시물 목록 수



    /*
    만약에 한 페이지에 게시ㅏ물을 10개씩 렌더링한다면
    1페이지 -> LIMIt 0, 6
    2페이지 -> LIMIt 6, 6
    3페이지 -> LIMIt 12, 6

    만약에 한 페이지에 게시물을 N개씩 렌더링한다면
    1페이지 -> LIMIT 0, N
    2페이지 -> LIMIT 6, N
    3페이지 -> LIMIT 12, N
    N페이지 -> LIMIT (M -1 ) * N, N

     */

    public Page() {
        this.pageNo = 1;
        this.amount = 6;
    }

    public int getPageStart() {
        return (this.pageNo -1) * this.amount;
    }

    public void setPageNo(int pageNo) {
        if (pageNo < 1 || pageNo > Integer.MAX_VALUE) {
            this.pageNo = 1;
            return;
        }
        this.pageNo = pageNo;
    }

    public void setAmount(int amount) {
        if (amount < 6 || amount > 60) {
            this.amount = 6;
            return;
        }
        this.amount = amount;
    }
}
