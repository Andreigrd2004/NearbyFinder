package com.java_app.demo.admin;

import com.java_app.demo.apikey.model.KeyDto;
import com.java_app.demo.user.dtos.UserDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomTransferAdmin {
  String message;
  HttpStatus status;
  List<UserDto> users;
  List<KeyDto> keys;

  public CustomTransferAdmin(String message, HttpStatus status) {
    this.message = message;
    this.status = status;
  }

  public CustomTransferAdmin(List<?> list, HttpStatus status) {
    Object firstElement = list.getFirst();
    if (firstElement instanceof UserDto) {
      this.users = list.stream().map(e -> (UserDto) e).collect(Collectors.toList());
    } else if (firstElement instanceof KeyDto) {
      this.keys = list.stream().map(e -> (KeyDto) e).collect(Collectors.toList());
    } else {
      throw new IllegalArgumentException("Not a supported object type" + firstElement.getClass());
    }
    this.status = status;
  }
}
