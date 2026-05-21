package com.szh.sms.controller.User;

import com.szh.sms.dto.User;
import com.szh.sms.service.User.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  @Test
  void loginReturnsUnauthorizedWhenCredentialsAreInvalid() {
    Map<String, Object> condition = new HashMap<>();
    condition.put("username", "wrong");
    condition.put("password", "wrong");
    condition.put("level", "0");
    when(userService.getStudentInfo(anyMap())).thenReturn(null);

    ResponseStatusException exception = assertThrows(
        ResponseStatusException.class,
        () -> userController.getStudentInfo(condition));

    assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    verify(userService, never()).getToken(any(User.class), anyLong());
  }
}
