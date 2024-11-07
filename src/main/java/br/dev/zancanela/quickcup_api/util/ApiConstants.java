package br.dev.zancanela.quickcup_api.util;

public class ApiConstants {

    public static final String EMPRESA = "empresa";
    public static final String FUNCIONAMENTO = "funcionamento";


    public static final String MV_OBJECT_CURRENT_PAGE = "currentPage";
    public static final String MV_OBJECT_MENSAGEM_ERRO = "mensagemErro";
    public static final String MV_OBJECT_MENSAGEM_SUCESSO = "mensagemSucesso";

    public static final String MV_OBJECT_EMPRESA = EMPRESA;
    public static final String MV_OBJECT_EMPRESA_REQUEST = "empresaRequest";

    public static final String MV_OBJECT_FUNCIONAMENTO_SEMANAL_LISTA = "listaFuncionamentoSemanal";
    public static final String MV_OBJECT_FUNCIONAMENTO_SEMANAL_REQUEST = "funcionamentoSemanalRequest";
    public static final String MV_OBJECT_FUNCIONAMENTO_SEMANAL_REQUEST_LISTA = "listaFuncionamentoSemanalRequest";
    public static final String MV_OBJECT_FUNCIONAMENTO_ESPECIAL_REQUEST = "funcionamentoEspecialRequest";
    public static final String MV_OBJECT_FUNCIONAMENTO_ESPECIAL_LISTA = "listaFuncionamentoEspecial";
    public static final String MV_OBJECT_FUNCIONAMENTO_ESPECIAL_TIPO_LISTA = "listaTipoFuncionamentoEspecial";


    public static final String VIEW_INDEX = "/index.html";

    public static final String VIEW_REDIRECT_EMPRESA = "redirect:/empresa";
    public static final String VIEW_EMPRESA_DETALHE_HTML = "empresa/detalhe.html";
    public static final String VIEW_EMPRESA_FORM_HTML = "empresa/form.html";

    public static final String VIEW_REDIRECT_FUNCIONAMENTO = "redirect:/funcionamento";
    public static final String VIEW_FUNCIONAMENTO_LISTA_HTML = "funcionamento/lista.html";
    public static final String VIEW_FUNCIONAMENTO_FORM_SEMANAL_HTML = "funcionamento/form-semanal.html";
    public static final String VIEW_FUNCIONAMENTO_FORM_ESPECIAL_HTML = "funcionamento/form-especial.html";


    private ApiConstants() {
        throw new IllegalStateException("Utility class");
    }
}
