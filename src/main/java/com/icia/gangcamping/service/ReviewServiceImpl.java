package com.icia.gangcamping.service;

import com.icia.gangcamping.dto.ReviewDetailDTO;
import com.icia.gangcamping.dto.ReviewSaveDTO;
import com.icia.gangcamping.entity.CampingEntity;
import com.icia.gangcamping.entity.MemberEntity;
import com.icia.gangcamping.entity.ReviewEntity;
import com.icia.gangcamping.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository rr;
    private final CampingService cs;
    private final MemberService ms;
    @Override
    public void save(ReviewSaveDTO reviewSaveDTO) {
        System.out.println("reviewController-save-reviewSaveDTO:" + reviewSaveDTO);
        CampingEntity campingEntity = cs.findByCampingName1(reviewSaveDTO.getCampingName());
        MemberEntity memberEntity = ms.findByMemberEmail(reviewSaveDTO.getReviewWriter());

        ReviewEntity entity = ReviewEntity.toReviewEntity(reviewSaveDTO,campingEntity,memberEntity);
        rr.save(entity);
    }

    @Override
    public ReviewDetailDTO findById(int rnd) {
       Optional<ReviewEntity> entity =  rr.findById((long) rnd);
       ReviewDetailDTO dto = ReviewDetailDTO.toDetailDTO(entity.get());
        return dto;
    }
}
