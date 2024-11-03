package br.dev.zancanela.quickcup_api.exception;

public class DataIntegrityViolationException extends QuickCupException {
    public DataIntegrityViolationException(String message) {
        super(message);
    }
}
