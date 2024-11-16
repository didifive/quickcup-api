package br.dev.zancanela.quickcup_api.exception;

import br.dev.zancanela.quickcup_api.dto.api.response.ApiErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class CustomRestExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler( { QuickCupException.class } )
    public ResponseEntity<ApiErrorDto> handleQuickCupApiException(QuickCupException e, HttpServletRequest request){
        ApiErrorDto err = new ApiErrorDto(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro no sistema :(",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(err.status()).body(err);
    }

    @ExceptionHandler( { UnauthorizedException.class } )
    public ResponseEntity<ApiErrorDto> handleQuickCupApiException(UnauthorizedException e, HttpServletRequest request){
        ApiErrorDto err = new ApiErrorDto(
                Instant.now(),
                HttpStatus.FORBIDDEN.value(),
                "Acesso proibido",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(err.status()).body(err);
    }

    @ExceptionHandler( { BadRequestBodyException.class } )
    public ResponseEntity<ApiErrorDto> handleBadRequestBodyException(BadRequestBodyException e, HttpServletRequest request){
        ApiErrorDto err = new ApiErrorDto(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro na requisição",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(err.status()).body(err);
    }

}
