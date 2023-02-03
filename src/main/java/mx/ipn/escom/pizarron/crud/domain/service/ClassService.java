package mx.ipn.escom.pizarron.crud.domain.service;

import mx.ipn.escom.pizarron.crud.adapter.dto.request.ClassRequestDto;
import mx.ipn.escom.pizarron.crud.adapter.entity.ClassEntity;
import mx.ipn.escom.pizarron.crud.util.exceptions.BusinessException;

import java.security.NoSuchAlgorithmException;

public interface ClassService {
    ClassEntity createClass(ClassRequestDto classRequestDto) throws BusinessException, NoSuchAlgorithmException;

    ClassEntity updateClass(String key, ClassRequestDto classRequestDto) throws BusinessException;

    ClassEntity getClass(String key) throws BusinessException;

    String getFullName(ClassEntity classEntity);
}
