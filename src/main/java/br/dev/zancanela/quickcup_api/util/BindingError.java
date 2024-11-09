package br.dev.zancanela.quickcup_api.util;

import br.dev.zancanela.quickcup_api.exception.BadRequestBodyException;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class BindingError {

    public static void checkBindingResultError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestBodyException(
                    bindingResult.getFieldErrors().stream()
                            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                            .collect(Collectors.joining("||")));
        }
    }

    private BindingError() {
        throw new IllegalAccessError("Utility Class");
    }

}
