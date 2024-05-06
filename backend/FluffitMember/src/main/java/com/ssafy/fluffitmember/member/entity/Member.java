package com.ssafy.fluffitmember.member.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private int coin;

    @Column(nullable = false)
    private int battlePoint;

    @CreatedDate
    @Column(name = "join_date", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    private int mflupet_id;

    @Builder
    public Member(String socialId, String nickname, int coin, int battlePoint, int mflupetId) {
        this.socialId = socialId;
        this.nickname = nickname;
        this.coin = coin;
        this.battlePoint = battlePoint;
        this.mflupet_id = mflupetId;
    }
    public static Member of(String socialId, String nickname, int coin, int battlePoint, int mflupetId) {
        return builder()
                .socialId(socialId)
                .nickname(nickname)
                .coin(coin)
                .battlePoint(battlePoint)
                .mflupetId(mflupetId)
                .build();
    }

}