package org.mbc.board.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mbc.board.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest //메소드용 테스트 동작
@Log4j2 // 로그용
public class BoardRepositoryTests {
    // 영속성 계층에 테스트용

    @Autowired //생성자 자동 주입
    private BoardRepository boardRepository;

    @Test
    public void testInsert(){
        // 데이터베이스에 데이터 주입(c) 테스트 코드
        IntStream.rangeClosed(1,100).forEach(i ->{
            // i 변수에 1~99까지 100개의 정수를 반복해서 생성
            Board board = Board.builder()
                    .title("제목...."+i) //board.setTitle()
                    .content("내용..."+i) //board.setContent()
                    .writer("user"+(i % 10)) //board.setWriter()
                    .build(); //@Builder 용 (세터 대신 좀 더 간단하고 가독성 좋게)
            //log.info(board);
            Board result = boardRepository.save(board); //데이터베이스에 기록하는 코드
                                        //.save 메서드는 jpa에서 상속한 메서드로 값을 저장하는 용도
                                        // 이미 값이 있으면 update를 진행함.
            log.info("게시물번호: "+result.getBno() + "게시물의 제목: " + result.getTitle());
        } //forEach 종료
        ); //IntStream. 종료
    } // testInsert

    @Test
    public void testSelect(){
        Long bno = 100L; //게시물 번호가 100인 개체 확인

        Optional<Board> result = boardRepository.findById(bno); //
        // null 값이 나올 경우를 대비한 객체
        //                                      .findByID(bno); -> select * from board where bno = bno;

        Board board = result.orElseThrow(); //값이 있으면 입력
        log.info(bno+"가 데이터 베이스에 존재합니다.");
        log.info(board);

        //Hibernate:
        //    select
        //        b1_0.bno,
        //        b1_0.content,
        //        b1_0.moddate,
        //        b1_0.regdate,
        //        b1_0.title,
        //        b1_0.writer
        //    from
        //        board b1_0
        //    where
        //        b1_0.bno=?

    } // testSelect 종료

    @Test
    public void testUpdate(){
        Long bno = 100L; // 100번 게시물 가져와서 수정 후 테스트 종료

        Optional<Board> result = boardRepository.findById(bno); //bno를 찾아 result에 넣음
        Board board = result.orElseThrow(); // 가져온 값이 있으면 board타입 객체에 넣음
        board.change("수정 테스트 제목", "수정 테스트 내용"); //제목과 내용만 수정 가능한 메소드
        boardRepository.save(board); // pk값이 null이면 insert , pk값이 null이 아니면 update

        //Hibernate:
        //    select
        //        b1_0.bno,
        //        b1_0.content,
        //        b1_0.moddate,
        //        b1_0.regdate,
        //        b1_0.title,
        //        b1_0.writer
        //    from
        //        board b1_0
        //    where
        //        b1_0.bno=?
        //Hibernate:
        //    select
        //        b1_0.bno,
        //        b1_0.content,
        //        b1_0.moddate,
        //        b1_0.regdate,
        //        b1_0.title,
        //        b1_0.writer
        //    from
        //        board b1_0
        //    where
        //        b1_0.bno=?
        //Hibernate:
        //    update
        //        board
        //    set
        //        content=?,
        //        moddate=?,
        //        title=?,
        //        writer=?
        //    where
        //        bno=?
    }

    @Test
    public void testDelete(){
        Long bno = 1L;

        boardRepository.deleteById(bno); // Delete from board where bno = bno

        //Hibernate:
        //    select
        //        b1_0.bno,
        //        b1_0.content,
        //        b1_0.moddate,
        //        b1_0.regdate,
        //        b1_0.title,
        //        b1_0.writer
        //    from
        //        board b1_0
        //    where
        //        b1_0.bno=?
        //Hibernate:
        //    delete
        //    from
        //        board
        //    where
        //        bno=?
    }

    @Test
    public void testPaging(){
        // .findAll() : 모든 리스트를 출력하는 메소드 select * from board;
        // 전체 리스트에 페이징과 정렬 기법 추가

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());
        //                                      시작번호, 페이지당 데이터 개수
        //                                                            번호를 내림차순으로 정렬

       

        // select
        //        b1_0.bno,
        //        b1_0.content,
        //        b1_0.moddate,
        //        b1_0.regdate,
        //        b1_0.title,
        //        b1_0.writer 
        //    from
        //        board b1_0 
        //    order by
        //        b1_0.bno desc (bno를 기준으로 내림차순 정렬)
        //    limit
        //        ?, ?
        //Hibernate: 
        //    select
        //        count(b1_0.bno) board 전체 리스트 수를 알아옴.
        //    from
        //        board b1_0
        Page<Board> result = boardRepository.findAll(pageable);
        //1장의 종이에 Board 객체를 가지고 있는 결과는 result에 담김.
        //Page 클래스는 다음페이지 존재 여부, 이전페이지 존재 여부, 전체 데이터 개수, 등등..계산을 함
                                                            //0,10   4,5      19,5
        log.info("전체 게시물 수: "+result.getTotalElements()); //99  //99      //99
        log.info("총 페이지 수: "+result.getTotalPages()); //10      //20       //20
        log.info("현재 페이지 번호: "+result.getNumber()); //0        //4        //19
        log.info("페이지당 데이터 수: "+result.getSize()); //10       //5        //5
        log.info("다음 페이지 여부: "+result.hasNext()); //true      //true      //false
        log.info("시작 페이지 여부: "+result.isFirst()); //true      //false     //false


        List<Board> BoardList = result.getContent(); //페이징처리된 내용을 가져옴

        BoardList.forEach(board -> log.info(board));
        //forEach는 인덱스를 사용하지 않고 앞에서부터 객체를 리턴함
        //                      board -> log.info(board)
        //                           람다식: 1개의 명령어가 있을 때 확용

    }
} //class
