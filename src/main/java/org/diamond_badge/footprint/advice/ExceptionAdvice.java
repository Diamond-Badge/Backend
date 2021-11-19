package org.diamond_badge.footprint.advice;

import javax.servlet.http.HttpServletRequest;

import org.diamond_badge.footprint.advice.exception.ComunicationException;
import org.diamond_badge.footprint.advice.exception.InvalidRefreshTokenException;
import org.diamond_badge.footprint.advice.exception.NotExpiredTokenYetException;
import org.diamond_badge.footprint.advice.exception.UserNotFoundException;
import org.diamond_badge.footprint.model.CommonResult;
import org.diamond_badge.footprint.service.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {
	private final ResponseService responseService;

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult defaultException(HttpServletRequest request,Exception e){
		return responseService.getFailResult(500,"실패");
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult userNotFoundException(HttpServletRequest request, UserNotFoundException e) {
		return responseService.getFailResult(HttpStatus.NOT_FOUND.value(), "사용자가 존재하지 않습니다.");
	}

	@ExceptionHandler(ComunicationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult comunicationException(HttpServletRequest request, ComunicationException e) {
		return responseService.getFailResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "통신 중 오류가 발생했습니다.");
	}

	@ExceptionHandler(NotExpiredTokenYetException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult notExpiredTokenYetException(HttpServletRequest request, NotExpiredTokenYetException e) {
		return responseService.getFailResult(HttpStatus.BAD_REQUEST.value(), "아직 유효한 토큰입니다.");
	}

	@ExceptionHandler(InvalidRefreshTokenException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult invalidRefreshTokenException(HttpServletRequest request, InvalidRefreshTokenException e) {
		return responseService.getFailResult(HttpStatus.NOT_FOUND.value(), "유효하지않은 Refresh Token 입니다.");
	}
}
