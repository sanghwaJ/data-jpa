package study.datajpa.dto;

import lombok.Data;

@Data // Getter와 Setter가 다 들어있기 때문에 실무에서는 @Data 사용을 자제하자
public class MemberDto {

    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }
}
