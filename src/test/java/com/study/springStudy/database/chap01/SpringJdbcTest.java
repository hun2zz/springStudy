package com.study.springStudy.database.chap01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SpringJdbcTest {
        @Autowired
    SpringJdbc springJdbc;

        //단위 테스트 프레임워크 : JUnit5
    // 테스트 == 단언 ( Assertion)

        @Test
        @DisplayName("사람의 정보를 입력하면 데이터베이스에 반드시 저장되ㅏ어야 한다.")
    void saveTest () {
            //gwt 패턴
            //given : 테스트에 주어질 데이터
            Person person = new Person(8, "팔백이", 8);

            //when : 테스트 상황
            int save = springJdbc.save(person);
            //then : 테스트 결과 단언
            assertEquals(1, save);
        }
        @Test
        @DisplayName("아이디가 주어지면 해당 아이디에 사람정보가 데이터베이스로부터 삭제되어야한다.")
        void deleteTest() {
            //given
            long id = 77;
            //when
            boolean delete = springJdbc.delete(id);
            //then
            assertTrue(delete);
        }

        @Test
        @DisplayName("아이디가 주어지면 해당 아이디에 입력한 나이와 이름이 수정되어어야 한다. ")
        void updateTest() {
            //given
            Person person = new Person(77, "진상훈", 13);
            //when
            boolean update = springJdbc.update(person);
            //then
            assertTrue(update);
        }
        
        @Test
        @DisplayName("사람 정보를 전체조회하면 결과 건수는 4건이다. 첫번째 사람의 이름은 팔백이이다.")
        void findAllTest() {
            //given
            List<Person> all = springJdbc.findAll();
            //when
            all.forEach(System.out::println);

            //then
            assertEquals(4, all.size());
            assertEquals("팔백이",all.get(0).getPersonName());
        }
        
        @Test
        @DisplayName("사람 정보를 아이디로 단일조회시 아이디가 300인 이름은 삼백이가 나옴.")
        void findOne() {
            //given
            long id = 300;

            //when
            Person oNe = springJdbc.findONe(id);

            //then
            System.out.println("oNe = " + oNe);
            assertNotNull(oNe);
            assertEquals("삼백이", oNe.getPersonName());
            assertEquals(32, oNe.getPersonAge());
        }
        


}
