package com.sandeep.medicalresearch.exception.handler;

import com.sandeep.medicalresearch.dto.ErrorDto;
import com.sandeep.medicalresearch.exception.MedicalResearchKafkaProducerException;
import com.sandeep.medicalresearch.exception.ProjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(SQLException.class)
  public ResponseEntity<ErrorDto> handleSqlExceptions(SQLException sqlException) {

    return new ResponseEntity<>(
        ErrorDto.builder()
            .message("Error Message:" + sqlException.getMessage())
            .code(HttpStatus.BAD_REQUEST.name())
            .build(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ProjectNotFoundException.class)
  public ResponseEntity<ErrorDto> handleProjectNotFoundException(
      ProjectNotFoundException projectNotFoundException) {
    return new ResponseEntity<>(
        ErrorDto.builder()
            .message("Error Message:" + projectNotFoundException.getMessage())
            .code(HttpStatus.NOT_FOUND.name())
            .build(),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MedicalResearchKafkaProducerException.class)
  public ResponseEntity<ErrorDto> handleKafkaProducerException(
          MedicalResearchKafkaProducerException medicalResearchKafkaProducerException) {
    return new ResponseEntity<>(
            ErrorDto.builder()
                    .message("Error Message:" + medicalResearchKafkaProducerException.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .build(),
            HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorDto> handleValidationExceptions(
      ConstraintViolationException constraintViolationException) {

    return new ResponseEntity<>(
        ErrorDto.builder()
            .message("Error Message:" + constraintViolationException.getCause().getMessage())
            .code(HttpStatus.BAD_REQUEST.name())
            .build(),
        HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return new ResponseEntity<>(
        ErrorDto.builder()
            .message("Error Message:" + ex.getMessage())
            .code(HttpStatus.METHOD_NOT_ALLOWED.name())
            .build(),
        HttpStatus.METHOD_NOT_ALLOWED);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return new ResponseEntity<>(
        ErrorDto.builder()
            .message("Error Message:" + ex.getMessage())
            .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.name())
            .build(),
        HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
      HttpMediaTypeNotAcceptableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return new ResponseEntity<>(
        ErrorDto.builder()
            .message("Error Message:" + ex.getMessage())
            .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.name())
            .build(),
        HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @Override
  protected ResponseEntity<Object> handleMissingPathVariable(
      MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return new ResponseEntity<>(
        ErrorDto.builder()
            .message("Error Message:" + ex.getMessage())
            .code(HttpStatus.BAD_REQUEST.name())
            .build(),
        HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return new ResponseEntity<>(
        ErrorDto.builder()
            .message("Error Message:" + ex.getMessage())
            .code(HttpStatus.BAD_REQUEST.name())
            .build(),
        HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleServletRequestBindingException(
      ServletRequestBindingException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return super.handleServletRequestBindingException(ex, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleConversionNotSupported(
      ConversionNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return super.handleConversionNotSupported(ex, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return super.handleTypeMismatch(ex, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return super.handleHttpMessageNotReadable(ex, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotWritable(
      HttpMessageNotWritableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return super.handleHttpMessageNotWritable(ex, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return new ResponseEntity<>(
        ErrorDto.builder()
            .message("Error Message:" + ex.getMessage())
            .code(HttpStatus.BAD_REQUEST.name())
            .build(),
        HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
      MissingServletRequestPartException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return super.handleMissingServletRequestPart(ex, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(
      BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return super.handleBindException(ex, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return super.handleNoHandlerFoundException(ex, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
      AsyncRequestTimeoutException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest webRequest) {
    return super.handleAsyncRequestTimeoutException(ex, headers, status, webRequest);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return new ResponseEntity<>(
        ErrorDto.builder()
            .message("Error Message:" + ex.getCause().getMessage())
            .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
            .build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
