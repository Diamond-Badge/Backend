package org.diamond_badge.footprint.advice;

import javax.servlet.http.HttpServletRequest;

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
}
