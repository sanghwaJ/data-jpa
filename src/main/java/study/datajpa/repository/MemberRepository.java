package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3By();

    // @Query(name = "Member.findByUsername") // @Query 부분을 빼도 실행됨
    List<Member> findByUsername(@Param("username") String username);

    // 실무에서 많이 쓰는 기능
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    // 실무에서 자주 사용
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    // 컬렉션
    List<Member> findListByUsername(String username);

    // 단건
    Member findMemberByUsername(String username);

    // 단건 Optional
    Optional<Member> findOptionalByUsername(String username);

    // 페이징
    // @Query(value = "select m from Member m left join m.team t",
    //         countQuery = "select count(m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);
    // Slice<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true) // update 쿼리 시, @Modifying 필수 추가, (clearAutomatically = true)는 em.clear()와 같은 기함
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override // findAll 메서드를 오버라이드하여 재정의
    @EntityGraph(attributePaths = {"team"}) // left join fetch와 같은 역할
    List<Member> findAll();

    // EntityGraph + JPQL도 가능
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    // @EntityGraph(attributePaths = {"team"})
    @EntityGraph("Member.all") // @NamedEntityGraph
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}
