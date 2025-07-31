package com.java_app.demo.user.mapper;

import com.java_app.demo.user.CustomUser;
import com.java_app.demo.user.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserDto UserToUserDto(CustomUser customUser);
}
