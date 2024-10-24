package br.com.Jose;

import br.com.Jose.dao.ClienteMapDAO;
import br.com.Jose.dao.IClienteDAO;
import br.com.Jose.domain.Cliente;

import javax.swing.*;

public class App {

    private static IClienteDAO iClienteDAO;

    public static void main(String[] args) {
        iClienteDAO = new ClienteMapDAO();

        String opcao = "";

        while (true) {
            opcao = JOptionPane.showInputDialog(
                    null,
                    "Digite 1 para cadastro, 2 para consultar, 3 para excluir, 4 para alteração ou 5 para sair",
                    "Cadastro",
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (isOpcaoSair(opcao)) {
                sair();
            }

            if (!isOpcaoValida(opcao)) {
                JOptionPane.showMessageDialog(null, "Opção inválida! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                continue; // Reinicia o loop se a opção for inválida
            }

            // Executa as ações com base na opção escolhida
            if (isCadastro(opcao)) {
                String dados = JOptionPane.showInputDialog(null, "Digite os dados do cliente separados por vírgula, conforme exemplo: Nome, CPF, Telefone, Endereço, Número, Cidade, Estado", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
                cadastrar(dados);
            } else if (isConsultar(opcao)) {
                String dados = JOptionPane.showInputDialog(null,
                        "Digite o CPF para consultar",
                        "Consultar", JOptionPane.INFORMATION_MESSAGE);

                consultar(dados);
            } else if ("3".equals(opcao)) {
                JOptionPane.showMessageDialog(null, "Exclusão de cliente ainda não implementada", "Em Desenvolvimento", JOptionPane.INFORMATION_MESSAGE);
            } else if ("4".equals(opcao)) {
                JOptionPane.showMessageDialog(null, "Alteração de cliente ainda não implementada", "Em Desenvolvimento", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private static void consultar(String dados) {
        if (dados == null || dados.trim().isEmpty() || dados.length() != 11 || !dados.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "CPF inválido. Certifique-se de que o CPF contém 11 dígitos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Long cpf = Long.parseLong(dados);
            Cliente cliente = iClienteDAO.consultar(cpf);

            if (cliente != null) {
                JOptionPane.showMessageDialog(null, "Cliente encontrado: " + cliente.toString(), "Consulta", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não encontrado com o CPF informado.", "Erro", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "CPF inválido. Certifique-se de que o CPF contém apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean isConsultar(String opcao) {
        return "2".equals(opcao);
    }

    private static void cadastrar(String dados) {
        String[] dadosSeparados = dados.split(",");

        if (dadosSeparados.length < 7) {
            JOptionPane.showMessageDialog(null, "Dados insuficientes. Certifique-se de inserir Nome, CPF, Telefone, Endereço, Número, Cidade e Estado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(
                dadosSeparados[0].trim(), dadosSeparados[1].trim(), dadosSeparados[2].trim(),
                dadosSeparados[3].trim(), dadosSeparados[4].trim(), dadosSeparados[5].trim(), dadosSeparados[6].trim()
        );

        boolean isCadastrado = iClienteDAO.cadastrar(cliente);
        if (isCadastrado) {
            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Cliente já se encontra cadastrado", "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static boolean isCadastro(String opcao) {
        return "1".equals(opcao);
    }

    private static boolean isOpcaoSair(String opcao) {
        return "5".equals(opcao);
    }

    private static void sair() {
        JOptionPane.showMessageDialog(null, "Tchau!", "Sair", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private static boolean isOpcaoValida(String opcao) {
        return "1".equals(opcao) || "2".equals(opcao) || "3".equals(opcao) || "4".equals(opcao) || "5".equals(opcao);
    }
}
