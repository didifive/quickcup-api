package br.dev.zancanela.quickcup_api.exception;

public class EntityNotFoundException extends QuickCupException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
