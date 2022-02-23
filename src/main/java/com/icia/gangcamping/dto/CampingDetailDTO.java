package com.icia.gangcamping.dto;

import com.icia.gangcamping.entity.CampingDetailEntity;
import com.icia.gangcamping.entity.CampingEntity;
import lombok.Data;

@Data
public class CampingDetailDTO {

    private String campingName;

    private String campingAddr;

    private String campingInfo;

    private String campingFileName;

    private int campingLikeCount;

    public static CampingDetailDTO toCampingDetailDTO(CampingEntity entity) {
        CampingDetailDTO camping = new CampingDetailDTO();
        System.out.println("campingEntity : "+entity);
        camping.setCampingAddr(entity.getCampingAddr());
        camping.setCampingInfo(entity.getCampingInfo());
        camping.setCampingName(entity.getCampingName());
        camping.setCampingFileName(entity.getCampingFileName());
        return camping;
    }
}