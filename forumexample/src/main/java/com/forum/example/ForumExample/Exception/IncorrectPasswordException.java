package com.forum.example.ForumExample.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Incorrect password!")
public class IncorrectPasswordException extends RuntimeException {


}
