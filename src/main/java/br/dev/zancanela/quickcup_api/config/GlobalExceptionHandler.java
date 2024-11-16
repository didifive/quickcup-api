package br.dev.zancanela.quickcup_api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateInputException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TemplateInputException.class)
    public String handleTemplateInputException(TemplateInputException ex) {
        logger.error("Template error: {}", ex.getMessage());
        return "error/500";
    }
}