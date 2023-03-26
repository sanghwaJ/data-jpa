package study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

// 객체의 입장에서 공통 매핑 정보가 필요할 때 사용 (생성자, 생성시간과 같이 모든 엔티티가 공통으로 가져가야 하는 경우 사용)
// 주의할 점! @MappedSuperclass는 진짜 상속 관계 매핑을 의미하는 것이 아님, 즉 JpaBaseEntity는 엔티티가 아니고 테이블과 매핑도 되지 않음
@MappedSuperclass
@Getter
public class JpaBaseEntity {

    @Column(updatable = false) // update 금지
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist // persist 하기 전 동작
    public void perPersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate // update 하기 전 동작
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
