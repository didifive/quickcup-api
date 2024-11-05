package br.dev.zancanela.quickcup_api.util;

public class ApiConstants {

    public static final String EMPRESA = "empresa";


    public static final String MV_OBJECT_CURRENT_PAGE = "currentPage";
    public static final String MV_OBJECT_MENSAGEM_SUCESSO = "mensagemSucesso";
    public static final String MV_OBJECT_EMPRESA = EMPRESA;
    public static final String MV_OBJECT_EMPRESA_REQUEST = "empresaRequest";


    public static final String VIEW_INDEX = "/index.html";

    public static final String VIEW_REDIRECT_EMPRESA = "redirect:/empresa";
    public static final String VIEW_EMPRESA_DETALHE_HTML = "empresa/detalhe.html";
    public static final String VIEW_EMPRESA_FORM = "empresa/form.html";


    private ApiConstants() {
        throw new IllegalStateException("Utility class");
    }
}
