package com.medit.meditlink.common.exception;

import com.medit.meditlink.common.model.ErrorCodeEnum;
import com.medit.meditlink.common.model.ErrorResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ModelMapper modelMapper;

    @ExceptionHandler({
            // GlobalException Define
            GlobalException.InvalidAPIVersion.class,
            GlobalException.AccessDenied.class,
            GlobalException.BadReqeust.class,

            // UserException Define
            UserException.NotFound.class
    })
    public final ResponseEntity<Object> GlobalHandleException(Exception ex, WebRequest request) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status;
        ErrorResponseDto.Error errorResponseDto = new ErrorResponseDto.Error();

        // GlobalException Handle
        if (ex instanceof GlobalException.InvalidAPIVersion) {
            status = HttpStatus.BAD_REQUEST;
            errorResponseDto.setStatus(status.value());
            errorResponseDto.setCode(ErrorCodeEnum.GlobalError.INVALID_API_VERSION.getCode());
            errorResponseDto.setPath(request.getContextPath());
            return this.DefaultHandler(status, errorResponseDto);
        }
        else if (ex instanceof GlobalException.AccessDenied) {
            status = HttpStatus.FORBIDDEN;
            errorResponseDto.setStatus(status.value());
            errorResponseDto.setCode(ErrorCodeEnum.GlobalError.ACCESS_DENIED.getCode());
            errorResponseDto.setPath(request.getContextPath());
            return this.DefaultHandler(status, errorResponseDto);
        }
        else if (ex instanceof GlobalException.BadReqeust) {
            status = HttpStatus.BAD_REQUEST;
            errorResponseDto.setStatus(status.value());
            errorResponseDto.setCode(ErrorCodeEnum.GlobalError.BAD_REQUEST.getCode());
            errorResponseDto.setPath(request.getContextPath());
            return this.BadRequestHandler((GlobalException.BadReqeust) ex, status, errorResponseDto);
        }

        // UserException Handle
        else if (ex instanceof UserException.NotFound) {
            status = HttpStatus.NOT_FOUND;
            errorResponseDto.setStatus(status.value());
            errorResponseDto.setCode(ErrorCodeEnum.UserError.NOT_FOUND.getCode());
            errorResponseDto.setPath(request.getContextPath());
            return this.DefaultHandler(status, errorResponseDto);
        }

        // Exception Handle
        else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorResponseDto.setStatus(status.value());
            errorResponseDto.setCode(ErrorCodeEnum.GlobalError.UNKNOWN_EXCEPTION.getCode());
            errorResponseDto.setPath(request.getContextPath());
            return this.DefaultHandler(status, errorResponseDto);
        }
    }


    private ResponseEntity<Object> BadRequestHandler(final GlobalException.BadReqeust e,
                                                     final HttpStatus httpStatus,
                                                     ErrorResponseDto.Error errorResponseDto) {
        //BindingResult
        if (e.getBindingResult().hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError oe : e.getBindingResult().getAllErrors()) {
                errors.add(oe.getDefaultMessage());
            }
            errorResponseDto.setErrors(errors);
        }

        return new ResponseEntity<>(modelMapper.map(errorResponseDto, ErrorResponseDto.Response.class), httpStatus);
    }


    private ResponseEntity<Object> DefaultHandler(final HttpStatus httpStatus,
                                                  ErrorResponseDto.Error errorResponseDto) {
        return this.DefaultHandler(null, httpStatus, errorResponseDto);
    }


    private ResponseEntity<Object> DefaultHandler(final Exception e,
                                                   final HttpStatus httpStatus,
                                                   ErrorResponseDto.Error errorResponseDto) {
        return new ResponseEntity<>(modelMapper.map(errorResponseDto, ErrorResponseDto.Response.class), httpStatus);
    }







}
