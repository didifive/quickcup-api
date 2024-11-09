package br.dev.zancanela.quickcup_api.util;

public class ApiConstants {

    public static final String EMPRESA = "empresa";
    public static final String FUNCIONAMENTO = "funcionamento";
    public static final String CADASTROS = "cadastros";


    public static final String MV_OBJECT_CURRENT_PAGE = "currentPage";
    public static final String MV_OBJECT_MENSAGEM_ERRO = "mensagemErro";
    public static final String MV_OBJECT_MENSAGEM_SUCESSO = "mensagemSucesso";

    public static final String MV_OBJECT_EMPRESA = EMPRESA;
    public static final String MV_OBJECT_EMPRESA_REQUEST = "empresaRequest";

    public static final String MV_OBJECT_FUNCIONAMENTO_SEMANAL_LISTA = "listaFuncionamentoSemanal";
    public static final String MV_OBJECT_FUNCIONAMENTO_SEMANAL_REQUEST = "funcionamentoSemanalRequest";
    public static final String MV_OBJECT_FUNCIONAMENTO_ESPECIAL_REQUEST = "funcionamentoEspecialRequest";
    public static final String MV_OBJECT_FUNCIONAMENTO_ESPECIAL_LISTA = "listaFuncionamentoEspecial";
    public static final String MV_OBJECT_FUNCIONAMENTO_ESPECIAL_TIPO_LISTA = "listaTipoFuncionamentoEspecial";

    public static final String MV_OBJECT_LISTA_GRUPOS = "listaGrupos";
    public static final String MV_OBJECT_GRUPO_REQUEST = "grupoRequest";

    public static final String MV_OBJECT_LISTA_PRODUTOS = "listaProdutos";
    public static final String MV_OBJECT_PRODUTO_REQUEST = "produtoRequest";

    public static final String MV_OBJECT_LISTA_CLIENTES = "listaClientes";

    public static final String MV_OBJECT_PEDIDOS_NOVOS = "pedidosNovos";
    public static final String MV_OBJECT_PEDIDOS_CONFIRMADOS = "pedidosConfirmados";
    public static final String MV_OBJECT_PEDIDOS_EM_PREPARO = "pedidosEmPreparo";
    public static final String MV_OBJECT_PEDIDOS_EM_ENTREGA = "pedidosEmEntrega";

    public static final String VIEW_INDEX = "/index.html";

    public static final String VIEW_REDIRECT_EMPRESA = "redirect:/empresa";
    public static final String VIEW_EMPRESA_DETALHE_HTML = "empresa/detalhe.html";
    public static final String VIEW_EMPRESA_FORM_HTML = "empresa/form.html";

    public static final String VIEW_REDIRECT_FUNCIONAMENTO = "redirect:/funcionamento";
    public static final String VIEW_FUNCIONAMENTO_LISTA_HTML = "funcionamento/lista.html";
    public static final String VIEW_FUNCIONAMENTO_FORM_SEMANAL_HTML = "funcionamento/form-semanal.html";
    public static final String VIEW_FUNCIONAMENTO_FORM_ESPECIAL_HTML = "funcionamento/form-especial.html";

    public static final String VIEW_REDIRECT_GRUPO = "redirect:/grupo";
    public static final String VIEW_GRUPO_LISTA_HTML = "grupo/lista.html";
    public static final String VIEW_GRUPO_FORM_HTML = "grupo/form.html";

    public static final String VIEW_REDIRECT_PRODUTO = "redirect:/produto";
    public static final String VIEW_PRODUTO_LISTA_HTML = "produto/lista.html";
    public static final String VIEW_PRODUTO_FORM_HTML = "produto/form.html";


    private ApiConstants() {
        throw new IllegalStateException("Utility class");
    }
}
