package com.icia.gangcamping.service;

import com.icia.gangcamping.dto.MemberDetailDTO;
import com.icia.gangcamping.dto.MemberLoginDTO;
import com.icia.gangcamping.dto.MemberSaveDTO;
import com.icia.gangcamping.dto.MemberUpdateDTO;
import com.icia.gangcamping.entity.MemberEntity;
import com.icia.gangcamping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO memberSaveDTO) {
        MemberEntity memberEntity = MemberEntity.saveMember(memberSaveDTO);

        return mr.save(memberEntity).getMemberId();
    }

    @Override
    public MemberEntity findByMemberEmail(String memberEmail) {
        return null;
    }

    @Override
    public Optional<MemberEntity> findByMemberId(Long memberId) {
        return Optional.empty();
    }

    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());
        if(memberEntity != null){
            if (memberEntity.getMemberPw().equals(memberLoginDTO.getMemberPw())){
                return true;
            }else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public Long findByMemberId(String memberEmail) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberEmail);
        Long memberId = memberEntity.getMemberId();
        return memberId;
    }

    @Override
    public MemberDetailDTO findByEmail(String memberEmail) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberEmail);
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
        return memberDetailDTO;
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
        MemberEntity memberEntity = mr.findById(memberId).get();
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);

        return memberDetailDTO;
    }

    @Override
    public String pwMailCheck(String memberEmail) {
        MemberEntity pwMailCheck = mr.findByMemberEmail(memberEmail);

        if(pwMailCheck != null){
            return "ok";
        }else{
            return "no";
        }
    }

    @Override
    public Long update(MemberUpdateDTO memberUpdateDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateMember(memberUpdateDTO);
        return mr.save(memberEntity).getMemberId();
    }

    @Override
    public String emailDp(String memberEmail) {
        MemberEntity result = mr.findByMemberEmail(memberEmail);
        if(result == null)
            return "ok";
        else
            return "no";
    }

    @Override
    public Long updateAddr(MemberUpdateAddrDTO memberUpdateAddrDTO) {
        return null;
    }


}
