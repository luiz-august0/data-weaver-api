package com.dataweaver.api.infrastructure.exceptions.enums;

public enum EnumGenericsException {
    GENERATE_TOKEN("Ocorreu um erro ao gerar o token de acesso"),
    GENERATE_REFRESH_TOKEN("Ocorreu um erro ao gerar o token de atualização"),
    GENERATE_RECOVERY_TOKEN("Ocorreu um erro ao gerar o token de recuperação"),
    VALIDATE_TOKEN("Ocorreu um erro ao validar o token"),
    EXPIRED_SESSION("Sessão expirada, realize o login novamente"),
    TOKEN_WITHOUT_SCHEMA("Não foi possível localizar schema no token"),
    INVALID_TENANT("Não foi possível localizar o tenant de acesso informado"),
    LOGIN_WITHOUT_TENANT("Não foi informado o tenant para a realização do login"),
    VALIDATION_ERROR_DATE_REPORT_FILTER("Valor padrão de filtro com tipo data deve seguir um desses valores: -30, +60, START_MONTH, END_MONTH ou 2000-01-01T00:00:00"),
    VALIDATION_ERROR_NUMBER_REPORT_FILTER("Valor padrão de filtro com tipo número deve ser um número"),
    VALIDATION_ERROR_NUMBER_BOOLEAN_FILTER("Valor padrão de filtro com tipo booleano deve seguir um desses valores: true ou false"),
    USER_NOT_FOUND("Usuário não encontrado"),
    EXPIRED_RECOVERY("Recuperação expirada, realize a solicitação novamente");


    private final String message;

    EnumGenericsException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
