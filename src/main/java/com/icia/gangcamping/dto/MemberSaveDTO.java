package com.icia.gangcamping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveDTO {

    @NotBlank(message = "이메일은 필수입니다.")
    @Length(min = 5, max = 30, message = "5~30자로 입력해주세요")
    private String memberEmail;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Length(min = 8, max = 30, message = "8~30자로 입력해주세요")
    private String memberPw;

    @NotBlank(message = "이름은 필수입니다.")
    private String memberName;

    @NotBlank(message = "주소는 필수입니다.")
    private String memberAddr;

    @NotBlank(message = "전화번호는 필수입니다.")
    private String memberPhone;

}
