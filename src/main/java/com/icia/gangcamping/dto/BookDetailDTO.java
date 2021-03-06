package com.icia.gangcamping.dto;

import com.icia.gangcamping.entity.BookEntity;


import com.icia.gangcamping.entity.CampingEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailDTO {

    private Long bookId;
    private Long memberId;
    private Long campingId;
    private Date bookCheckIn;
    private Date bookCheckOut;
    private LocalDateTime createTime;
    private String campingName;
    private String campingFileName;
    private int bookPrice;
    private String bookName;
    private boolean check;

    public static BookDetailDTO toBookDetailDTO(BookEntity bookEntity) {
        BookDetailDTO bookDetailDTO = new BookDetailDTO();
        bookDetailDTO.setBookId(bookEntity.getBookId());
        bookDetailDTO.setBookName(bookEntity.getBookName());
        bookDetailDTO.setBookPrice(bookEntity.getBookPrice());
        bookDetailDTO.setMemberId(bookEntity.getMemberEntity().getMemberId());
        bookDetailDTO.setCampingId(bookEntity.getCampingEntity().getCampingId());
        bookDetailDTO.setBookCheckIn(bookEntity.getBookCheckIn());
        bookDetailDTO.setBookCheckOut(bookEntity.getBookCheckOut());
        bookDetailDTO.setCreateTime(bookEntity.getCreateTime());
        bookDetailDTO.setCampingName(bookEntity.getCampingEntity().getCampingName());
        return bookDetailDTO;
    }

}
