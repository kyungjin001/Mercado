package com.icia.gangcamping.service;

import com.icia.gangcamping.dto.*;
import com.icia.gangcamping.entity.AnswerEntity;
import com.icia.gangcamping.entity.MemberEntity;
import com.icia.gangcamping.entity.ProductEntity;
import com.icia.gangcamping.entity.QuestionEntity;
import com.icia.gangcamping.repository.AnswerRepository;
import com.icia.gangcamping.repository.MemberRepository;
import com.icia.gangcamping.repository.ProductRepositroy;
import com.icia.gangcamping.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final MemberRepository mr;
    private final ProductRepositroy pr;
    private final QuestionRepository qr;
    private final AnswerRepository ar;


    @Override
    public Long save(CommentSaveDTO commentSaveDTO) {
        ProductEntity productEntity = pr.findById(commentSaveDTO.getProductId()).get();
        MemberEntity memberEntity = mr.findByMemberEmail(commentSaveDTO.getCommentWriter());
        QuestionEntity questionEntity = QuestionEntity.toSaveEntity(commentSaveDTO, memberEntity, productEntity);
        return qr.save(questionEntity).getQuestionId();
    }

    //μνλ¬Έμ
    @Override
    public List<CommentDetailDTO> findAll(Long productId) throws ParseException {
        ProductEntity productEntity = pr.findById(productId).get();
        List<QuestionEntity> questionEntityList = productEntity.getQuestionEntityList();
        List<CommentDetailDTO> commentList = new ArrayList<>();
        int i = 0;
        int size = 0;
        if (!questionEntityList.isEmpty()) {
            for (QuestionEntity q : questionEntityList) {
                if (q.getAnswerEntityList() != null){
                size = q.getAnswerEntityList().size();
                }
                CommentDetailDTO commentDetailDTO = CommentDetailDTO.toCommentDetailDTO(q);
                LocalDateTime commentT = q.getCreateTime();

                String date1 = java.sql.Timestamp.valueOf(commentT).toString().substring(0, 19);
               if(i<size) {
                   String qna = q.getAnswerEntityList().get(i).getAnswerContents();
                   commentDetailDTO.setAnswerContents(qna);
               }
                   commentDetailDTO.setCommentT(date1);
                   System.out.println(commentDetailDTO);
                   commentList.add(commentDetailDTO);

                   i++;
            }
        }
        return commentList;
    }

    @Override
    public void deleteById(Long questionId) {
        qr.deleteById(questionId);
    }


    //κ΄λ¦¬μ μνλ¬Έμλ¦¬μ€νΈ
    @Override
    public List<CommentDetailDTO> findAll1() {
        List<QuestionEntity> questionEntityList = qr.findAll();
        List<CommentDetailDTO> commentList = new ArrayList<>();
        for (QuestionEntity q : questionEntityList) {

            commentList.add(CommentDetailDTO.toCommentDetailDTO(q)); //νμ€λ‘ κ°λ₯


        }
        return commentList;


    }

    @Override
    public Long save1(AnswerSaveDTO answerSaveDTO) {
        Optional<QuestionEntity> questionEntity = qr.findById(answerSaveDTO.getQuestionId());
        AnswerEntity answerEntity = AnswerEntity.toSaveEntity(answerSaveDTO, questionEntity.get());
        return ar.save(answerEntity).getAnswerId();
    }

    @Override
    public List<AnswerDetailDTO> findAll2(Long productId) {
        List<AnswerEntity> answerEntityList = ar.findAll();
        List<AnswerDetailDTO> answer = new ArrayList<>();
        for (AnswerEntity a : answerEntityList) {



            AnswerDetailDTO answerDetailDTO = AnswerDetailDTO.toAnswerDetailDTO(a);
            LocalDateTime commentT = a.getCreateTime();

//            String date2 = java.sql.Timestamp.valueOf(commentT).toString().substring(0, 19);
//

//            answerDetailDTO.setCommentT(date2);
            System.out.println(answerDetailDTO);
            answer.add(answerDetailDTO); //νμ€λ‘ κ°λ₯






//            answer.add(AnswerDetailDTO.toAnswerDetailDTO(a)); //νμ€λ‘ κ°λ₯


        }
        return answer;
    }


}
