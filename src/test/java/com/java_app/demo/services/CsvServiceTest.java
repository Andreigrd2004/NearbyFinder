package com.java_app.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.java_app.demo.csv.Impl.CsvServiceImpl;
import com.java_app.demo.user.CustomUser;
import com.java_app.demo.user.UserRepository;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class CsvServiceTest {

  @Mock private UserRepository userRepository;

  @InjectMocks CsvServiceImpl csvService;

  @Test
  public void testWriteUser_ShouldSendResponse() throws UnsupportedEncodingException {
    MockHttpServletResponse response = new MockHttpServletResponse();
    response.setContentType("text/csv;charset=utf-8");
    response.setHeader("Content-Disposition", "attachment; filename=\"users.csv\"");

    CustomUser user1 =
        new CustomUser(
            1,
            "awdwa",
            "wadawd",
            "adwad",
            "awdfwba",
            true,
            true,
            new HashSet<>(Collections.singleton("ROLE_USER")),
            null,
            null,
            true,
            true);
    List<CustomUser> list = new ArrayList<>();
    list.add(user1);

    String expectedContent =
            "Id, Email, Username, Enabled, Expired, Roles"
            + System.lineSeparator()
            + "1,awdwa,awdfwba,true,true,ROLE_USER"
            + System.lineSeparator();

    when(userRepository.findAll()).thenReturn(list);

    csvService.writeUsers(response);

    assertEquals("text/csv; charset=utf-8", response.getContentType());
    assertEquals("attachment; filename=\"users.csv\"", response.getHeader("Content-Disposition"));
    assertEquals(expectedContent, response.getContentAsString());
  }
}
