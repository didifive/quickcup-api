package br.dev.zancanela.quickcup_api.util;

import br.dev.zancanela.quickcup_api.exception.BadRequestBodyException;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BindingErrorTest {

    @Test
    void testCheckBindingResultErrorWithErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        assertThatThrownBy(() -> BindingError.checkBindingResultError(bindingResult))
                .isInstanceOf(BadRequestBodyException.class)
                .hasMessageContaining("field: defaultMessage");
    }

    @Test
    void testCheckBindingResultErrorWithoutErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        BindingError.checkBindingResultError(bindingResult);
    }
}
