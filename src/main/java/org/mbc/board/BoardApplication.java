package org.mbc.board;

import jakarta.persistence.EntityListeners;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //main 메소드 실행시 감시작업 동작
//@EntityListeners(value= AuditingEntityListener.class) 와 세트로 동작
public class BoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
    }

}
