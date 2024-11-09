package br.dev.zancanela.quickcup_api.exception;

public class BadRequestBodyException extends QuickCupException {
    public BadRequestBodyException(String message) {
        super(message);
    }
}
