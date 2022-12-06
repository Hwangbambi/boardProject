package com.sparta.hanghaeboard.entity;

import com.sparta.hanghaeboard.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter //값을 가져올 때 사용
@Entity //이 클래스를 Entity로 쓰겠다/ JPA를 사용 : 매핑된 관계를 이용해서 SQL을 생성하고 실행
@NoArgsConstructor //기본 생성자를 생성해줌, 우리가 사용해서 선언한게 아니라 JPA가 우리가 알게 모르게 사용하기 때문에 없으면 에러발생
public class Board extends Timestamped{

    @Id //기본키(PK)를 지정
    @GeneratedValue(strategy = GenerationType.AUTO)
    //Id값을 따로 할당하지 않아도 데이터베이스가 자동으로 AUTO_INCREMENT를 하여 기본키를 생성 (1씩 증가)
    private Long id;

    @Column(nullable = false) //NOT NULL 의미, TRUE 라면 NULL 허용
    private String title;

    @Column(nullable = false)
    private String contents;

    //@Column(nullable = false)
    //private Long password;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false) //member 테이블의 pk와 연결 됨
    private Member member;

    public Board(BoardRequestDto boardRequestDto, Member member) {
        this.title = boardRequestDto.getTitle();
        this.contents = boardRequestDto.getContents();
        this.member = member;
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.contents = boardRequestDto.getContents();
    }
}
