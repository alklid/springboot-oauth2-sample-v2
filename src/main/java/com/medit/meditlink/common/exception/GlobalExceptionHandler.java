package com.medit.meditlink.common.exception;

import com.medit.meditlink.common.model.ErrorCodeEnum;
import com.medit.meditlink.common.model.ErrorResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ModelMapper modelMapper;

    @ExceptionHandler({
            // GlobalException Define
            GlobalException.InvalidAPIVersion.class,
            GlobalException.InvalidSchemaVersion.class,
            GlobalException.AccessDenied.class,
            GlobalException.BadReqeust.class,

            // UserException Define
            UserException.NotFound.class
    })
    public final ResponseEntity<Object> GlobalHandleException(Exception ex, WebRequest request) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status;
        ErrorResponseDto.Error errorResponseDto = new ErrorResponseDto.Error(request);

        // GlobalException Handle
        if (ex instanceof GlobalException.InvalidAPIVersion) {
            status = HttpStatus.BAD_REQUEST;
            errorResponseDto.setStatus(status.value());
            errorResponseDto.setCode(ErrorCodeEnum.GlobalError.INVALID_API_VERSION.getCode());
            return this.DefaultHandler(status, errorResponseDto);
        }
        else if (ex instanceof GlobalException.InvalidSchemaVersion) {
            status = HttpStatus.FORBIDDEN;
            errorResponseDto.setStatus(status.value());
            errorResponseDto.setCode(ErrorCodeEnum.GlobalError.INVALID_SCHEMA_VERSION.getCode());
            return this.DefaultHandler(status, errorResponseDto);
        }
        else if (ex instanceof GlobalException.AccessDenied) {
            status = HttpStatus.FORBIDDEN;
            errorResponseDto.setStatus(status.value());
            errorResponseDto.setCode(ErrorCodeEnum.GlobalError.ACCESS_DENIED.getCode());
            return this.DefaultHandler(status, errorResponseDto);
        }
        else if (ex instanceof GlobalException.BadReqeust) {
            status = HttpStatus.BAD_REQUEST;
            errorResponseDto.setStatus(status.value());
            errorResponseDto.setCode(ErrorCodeEnum.GlobalError.BAD_REQUEST.getCode());
            return this.BadRequestHandler((GlobalException.BadReqeust) ex, status, errorResponseDto);
        }

        // UserException Handle
        else if (ex instanceof UserException.NotFound) {
            status = HttpStatus.NOT_FOUND;
            errorResponseDto.setStatus(status.value());
            errorResponseDto.setCode(ErrorCodeEnum.UserError.NOT_FOUND.getCode());
            return this.DefaultHandler(status, errorResponseDto);
        }

        else if (ex instanceof NoHandlerFoundException) {
            status = HttpStatus.NOT_FOUND;
            errorResponseDto.setStatus(status.value());
            errorResponseDto.setCode(ErrorCodeEnum.GlobalError.NO_HANDLER_FOUND.getCode());
            return this.DefaultHandler(status, errorResponseDto);
        }

        // Exception Handle
        else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorResponseDto.setStatus(status.value());
            errorResponseDto.setCode(ErrorCodeEnum.GlobalError.UNKNOWN_EXCEPTION.getCode());
            return this.DefaultHandler(status, errorResponseDto);
        }
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


    private ResponseEntity<Object> BadRequestHandler(final GlobalException.BadReqeust e,
                                                     final HttpStatus httpStatus,
                                                     ErrorResponseDto.Error errorResponseDto) {
        // BindingResult에서 어떤 field가 어떤 이유로 invalid되었는지 확인
        if (e.getBindingResult().hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fe : e.getBindingResult().getFieldErrors()) {
                errors.add("[" + fe.getField() + "] " + fe.getDefaultMessage());
            }
            errorResponseDto.setErrors(errors);
        }

        return new ResponseEntity<>(modelMapper.map(errorResponseDto, ErrorResponseDto.Response.class), httpStatus);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorResponseDto.Error errorResponseDto = new ErrorResponseDto.Error();
        errorResponseDto.setTimestamp(Instant.now());
        errorResponseDto.setStatus(status.value());
        errorResponseDto.setCode(ErrorCodeEnum.ResponseEntityError.getCode(ex));
        errorResponseDto.setPath(((ServletWebRequest) request).getRequest().getRequestURI());

        /*
        * ResponseEntityExceptionHandler 에서 MethodArgumentNotValidException의 경우,
        * BindingResult에서 어떤 field가 어떤 이유로 invalid되었는지 확인
        */
        if (ex instanceof MethodArgumentNotValidException) {
            // BindingResult
            if (((MethodArgumentNotValidException) ex).getBindingResult().hasErrors()) {
                List<String> errors = new ArrayList<>();
                for (FieldError fe : ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors()) {
                    errors.add("[" + fe.getField() + "] " + fe.getDefaultMessage());
                }
                errorResponseDto.setErrors(errors);
            }
        }

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(modelMapper.map(errorResponseDto, ErrorResponseDto.Response.class), headers, status);
    }

}
