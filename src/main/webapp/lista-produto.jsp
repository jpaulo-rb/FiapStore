<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>FiapStore</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./resources/css/bootstrap.css">
</head>
<body>
<%@include file="header.jsp" %>
<div class="container">
    <div class="mt-5 ms-5 me-5">

        <div class="card mb-3">
            <div class="card-header">
                LISTA DE PRODUTOS
            </div>
            <c:if test="${not empty mensagem}">
                <div class="alert alert-success ms-2 me-2 mt-2"> ${mensagem} </div>
            </c:if>

            <c:if test="${not empty erro}">
                <div class="alert alert-danger ms-2 me-2 mt-2"> ${erro} </div>
            </c:if>
            <div class="card-body">
                <h5 class="card-title">Gestão de produdos eficiente</h5>
                <p class="card-text">Mantenha os dados dos seus produtos sempre atualizados e acessíveis.</p>
                <table class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th>Nome</th>
                        <th class="text-end">Valor</th>
                        <th class="text-end">Quantidade</th>
                        <th class="text-center">Data de fabricação</th>
                        <th class="text-center">Categoria</th>
                        <th class="text-center">Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${produtos}" var="produto">
                        <tr>
                            <td>${produto.nome}</td>
                            <td class="text-end">${produto.valor}</td>
                            <td class="text-end">${produto.quantidade}</td>
                            <td class="text-center">
                                <fmt:parseDate
                                        value="${produto.dataCriacao}"
                                        pattern="yyyy-MM-dd"
                                        var="dataCriacaoFmt"/>
                                <fmt:formatDate
                                        value="${dataCriacaoFmt}"
                                        pattern="dd/MM/yyyy"/>
                            </td>
                            <td class="text-center">
                                <c:if test="${produto.categoria.categoria == null}">
                                    -
                                </c:if>
                                <c:if test="${produto.categoria.categoria != null}">
                                    ${produto.categoria.categoria}
                                </c:if>
                            </td>
                            <td class="text-center">
                                <div class="d-flex justify-content-center">
                                    <c:url value="produtos" var="link">
                                        <c:param name="acao" value="abrir-form-edicao"/>
                                        <c:param name="id" value="${produto.id}"/>
                                    </c:url>
                                    <a href="${link}" class="btn btn-primary m-2">Editar</a>
                                    <!-- Botão para abrir o modal -->

                                    <button
                                            type="button"
                                            class="btn btn-danger m-2"
                                            data-bs-toggle="modal"
                                            data-bs-target="#excluirModal"
                                            onclick="idExcluir.value = ${produto.id}; nomeExcluir.value = '${produto.nome}'"
                                    > Excluir
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <a href="produtos?acao=abrir-form-cadastro" class="btn btn-primary">Adicionar produto</a>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div
        class="modal fade"
        id="excluirModal"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1
                        class="modal-title fs-5"
                        id="exampleModalLabel">
                    Confirmar Exclusão
                </h1>
                <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close">
                    X
                </button>
            </div>
            <div class="modal-body">
                <h4>Você confirma a exclusão deste produto?</h4>
                <p><strong>Atenção!</strong> Esta ação é irreversível.</p>
            </div>
            <div class="modal-footer">

                <form action="produtos" method="post">
                    <input
                            type="hidden"
                            name="acao"
                            value="excluir">
                    <input
                            type="hidden"
                            name="idExcluir"
                            id="idExcluir">
                    <input
                            type="hidden"
                            name="nomeExcluir"
                            id="nomeExcluir">
                    <button
                            type="button"
                            class="btn btn-secondary"
                            data-bs-dismiss="modal">
                        Não
                    </button>
                    <button
                            type="submit"
                            class="btn btn-danger">
                        Sim
                    </button>
                </form>

            </div>
        </div>
    </div>
</div>
<%--    fim modal--%>

<%@include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>