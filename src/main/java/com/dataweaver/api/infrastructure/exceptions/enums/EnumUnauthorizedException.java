package com.dataweaver.api.infrastructure.exceptions.enums;

public enum EnumUnauthorizedException {
    USER_ALREADY_REGISTERED("Usuário já cadastrado"),
    USER_INACTIVE("Usuário não está ativo"),
    WRONG_CREDENTIALS("Usuário ou senha incorretos"),
    CNPJ_INVALID("CNPJ inválido"),
    CNPJ_CPF_INVALID("CPF/CNPJ inválido"),
    CPF_INVALID("CPF inválido"),
    CPF_ALREADY_REGISTERED("CPF já cadastrado"),
    CNPJ_ALREADY_REGISTERED("CNPJ já cadastrado"),
    RG_ALREADY_REGISTERED("RG já cadastrado"),
    ADMIN_CANNOT_BE_DEACTIVATED("Usuário admin não pode ser desativado"),
    USER_ROLE_UNAUTHORIZED("Vocẽ não tem acesso para realizar esta operação"),
    RECEIVE_TITLE_ALREADY_PAID("O título já foi pago e não é permitido mais alterações"),
    RECEIVE_TITLE_ALREADY_CANCELED("O título já foi cancelado e não é permitido mais alterações"),
    CONTRACT_ALREADY_CANCELED("O contrato já foi cancelado e não é permitido mais alterações"),
    MATURITY_DATE_INVALID("A data de vencimento deve ser superior a data atual"),
    FIRST_INSTALLMENT_DATE_INVALID("A data do primeiro vencimento deve ser superior a data atual"),
    LOGIN_INVALID("O login deve ser um e-mail válido");
    private final String message;

    EnumUnauthorizedException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
