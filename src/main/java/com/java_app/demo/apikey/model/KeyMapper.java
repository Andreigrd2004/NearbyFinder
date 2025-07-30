package com.java_app.demo.apikey.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface KeyMapper {

  KeyMapper INSTANCE = Mappers.getMapper(KeyMapper.class);

  KeyDto apiKeyToKeyDto(ApiKey key);
}
