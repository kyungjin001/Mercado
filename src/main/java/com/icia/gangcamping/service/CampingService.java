package com.icia.gangcamping.service;

import com.icia.gangcamping.entity.CampingDetailEntity;
import com.icia.gangcamping.entity.CampingEntity;

import java.util.Optional;

public interface CampingService {
    void save(CampingEntity entity);

    Optional<CampingEntity> findById(long l);

    void saveDetail(CampingDetailEntity entity);
}