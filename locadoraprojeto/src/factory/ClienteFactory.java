package factory;

import javax.naming.directory.InvalidAttributesException;

import model.Cliente;

public class ClienteFactory {
    public static Cliente criar(String nome, String cpf, String telefone, String endereco, String email)
            throws InvalidAttributesException {
        nome = validaTexto(nome);
        cpf = validaTexto(cpf);
        telefone = validaTexto(telefone);
        endereco = validaTexto(endereco);
        email = validaTexto(email);

        validaCampoObrigatorio(nome, "Nome é obrigatório");
        validaCampoObrigatorio(endereco, "Endereço é obrigatório");

        validaCPF(cpf);
        validaTelefone(telefone);
        validaEmail(email);

        return new Cliente(nome, cpf, telefone, endereco, email, 0);
    }

    private static String validaTexto(String texto) {
        return texto == null ? "" : texto.trim();
    }

    private static void validaCampoObrigatorio(String campo, String mensagemErro) throws InvalidAttributesException {
        if (campo.isEmpty())
            throw new InvalidAttributesException(mensagemErro);
    }

    private static void validaCPF(String cpf) throws InvalidAttributesException {
        if (cpf.isEmpty()) {
            throw new InvalidAttributesException("CPF é obrigatório");
        }

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            throw new InvalidAttributesException("CPF deve conter 11 dígitos");
        }

        boolean todosDigitosIguais = true;
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                todosDigitosIguais = false;
                break;
            }
        }

        if (todosDigitosIguais) {
            throw new InvalidAttributesException("CPF inválido (dígitos repetidos)");
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }

        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito > 9) {
            primeiroDigito = 0;
        }

        if (primeiroDigito != (cpf.charAt(9) - '0')) {
            throw new InvalidAttributesException("CPF inválido (primeiro dígito verificador incorreto)");
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }

        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito > 9) {
            segundoDigito = 0;
        }

        if (segundoDigito != (cpf.charAt(10) - '0')) {
            throw new InvalidAttributesException("CPF inválido (segundo dígito verificador incorreto)");
        }
    }

    private static void validaTelefone(String telefone) throws InvalidAttributesException {
        if (telefone.isEmpty()) {
            throw new InvalidAttributesException("Telefone é obrigatório");
        }

        String telefoneNumerico = telefone.replaceAll("[^0-9]", "");

        if (telefoneNumerico.length() < 8 || telefoneNumerico.length() > 14) {
            throw new InvalidAttributesException("Telefone deve conter entre 8 e 14 dígitos");
        }

        if (telefoneNumerico.startsWith("0") && telefoneNumerico.length() < 10) {
            throw new InvalidAttributesException("Formato de telefone inválido");
        }
    }

    private static void validaEmail(String email) throws InvalidAttributesException {
        if (email.isEmpty()) {
            throw new InvalidAttributesException("E-mail é obrigatório");
        }

        int posicaoArroba = email.indexOf('@');

        if (posicaoArroba == -1 || posicaoArroba == 0 || posicaoArroba == email.length() - 1) {
            throw new InvalidAttributesException("E-mail deve conter um @ em posição válida");
        }

        String parteLocal = email.substring(0, posicaoArroba);
        if (parteLocal.isEmpty()) {
            throw new InvalidAttributesException("E-mail deve ter conteúdo antes do @");
        }

        String dominio = email.substring(posicaoArroba + 1);

        if (dominio.isEmpty()) {
            throw new InvalidAttributesException("E-mail deve ter um domínio após o @");
        }

        if (dominio.indexOf('.') == -1) {
            throw new InvalidAttributesException("Domínio do e-mail deve conter pelo menos um ponto");
        }

        if (dominio.startsWith(".") || dominio.endsWith(".")) {
            throw new InvalidAttributesException("Domínio do e-mail contém ponto em posição inválida");
        }

        String[] partesDominio = dominio.split("\\.");
        String extensao = partesDominio[partesDominio.length - 1];
        if (extensao.length() < 2) {
            throw new InvalidAttributesException("Extensão do domínio do e-mail deve ter pelo menos 2 caracteres");
        }
    }
}