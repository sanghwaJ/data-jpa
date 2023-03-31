package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberContoroller {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember(@PathVariable("id") Member member) {
        // 도메인 클래스 컨버터 기능으로 기존 코드에서 컨버팅 해주는 기능을 대신해 줌 (사실 권장하지 않음)
        return member.getUsername();
    }

    @GetMapping("members")
    public Page<MemberDto> list(Pageable pageable) {
        // ver 1.0
        // Page<Member> page = memberRepository.findAll(pageable);
        // // DTO로 변환
        // Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
        // return map;

        // ver 2.0
        // return memberRepository.findAll(pageable)
        //         .map(member -> new MemberDto(member.getId(), member.getUsername(), null));

        // ver 3.0
        // return memberRepository.findAll(pageable)
        //         .map(member -> new MemberDto(member);

        // ver 4.0
        return memberRepository.findAll(pageable)
                .map(MemberDto::new);
    }

    @PostConstruct
    public void init() {
        // memberRepository.save(new Member("userA"));
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" + i, i));
        }
    }

}
