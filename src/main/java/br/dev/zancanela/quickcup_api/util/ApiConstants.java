package br.dev.zancanela.quickcup_api.util;

public class ApiConstants {

    public static final int UMA_HORA_EM_SEGUNDOS = 3600;
    public static final String API_REST_ROOT_URN = "/api/v1/**";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String EMPRESA = "empresa";
    public static final String FUNCIONAMENTO = "funcionamento";
    public static final String CADASTROS = "cadastros";
    public static final String PEDIDOS = "pedidos";

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

    public static final String VIEW_INDEX = "index.html";

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

    public static final String VIEW_CLIENTE_LISTA_HTML = "cliente/lista.html";

    public static final String VIEW_REDIRECT_ATENDIMENTO = "redirect:/pedido/atendimento";
    public static final String VIEW_PEDIDO_ATENDIMENTO_HTML = "pedido/atendimento.html";
    public static final String API_TITLE = "QuickCup API";
    public static final String API_DESCRIPTION = "Api para sistema de entregas";
    public static final String API_VERSION = "0.0.1-SNAPSHOT";
    public static final String API_LICENSE = "MIT License";
    public static final String API_LICENSE_URL = "https://mit-license.org/";
    public static final String CONTACT_NAME = "Luis Zancanela";
    public static final String CONTACT_URL = "https://zancanela.dev.br";
    public static final String QUICKCUP_API_KEY_SECURITY_SCHEMES = "apiKey";


    private ApiConstants() {
        throw new IllegalStateException("Utility class");
    }
}
