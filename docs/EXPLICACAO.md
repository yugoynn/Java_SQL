# Explicacao das regras implementadas

## Cadastro de ninjas (Q1)
Tela `TelaCadastroNinja` com JTextField para nome e o clã, `JComboBox` para a vila,
rank e natureza de chakra e os seus status. Os dados sao inseridos via `NinjaDAO` e a
listagem aparece em uma JTable. Tambem ha edicao e exclusao pela mesma tela.

## Cadastro de missoes (Q2)
Tela `TelaCadastroMissao` com JTextField, JTextArea para a descricao e JComboBox
para rank, vila e status. Suporta inserir, alterar, excluir e listar. O rank so
aceita D, C, B, A ou S, validado no `MissaoService` e tambem por CHECK no banco.

## Relacionamento ninja/missao (Q3)
Tabela intermediaria `tb_ninja_missao` representa o muitos para muitos. A tela
`TelaVincularMissao` usa tres JComboBox (ninja, missao, funcao). A constraint
UNIQUE (id_ninja, id_missao) no banco e a checagem em `NinjaMissaoDAO.vinculoExiste`
impedem vincular o mesmo ninja duas vezes a mesma missao.

## Regra de negocio em Java (Q4)
A validacao de rank fica em `NinjaMissaoService.podeParticipar`:

- Genin: missoes D, C
- Chunin: missoes D, C, B
- Jounin: missoes D, C, B, A, S
- Kage: missoes A, S

Se a regra for violada, o service lanca uma excecao e a tela exibe a mensagem
com JOptionPane. O vinculo nao e salvo no banco nesse caso.

## Totalizadores (Q5)
`TotalizadorService` calcula a partir das tabelas: ninjas por vila, por rank e
por natureza de chakra; missoes por rank e por status; ninjas vinculados a
missoes; e missoes sem nenhum ninja vinculado. Cada totalizador e exibido em
JTable e salvo em `tb_totalizador_ninja` com a data de geracao.

## Views (Q6)
Criadas no script `003_views.sql`:

- vw_ninja_missoes
- vw_total_ninjas_por_vila
- vw_total_missoes_por_rank
- vw_missoes_sem_ninjas

A tela `TelaConsultaViews` permite escolher qualquer uma das quatro e exibe o
resultado em JTable, montando colunas dinamicamente a partir dos metadados.

## Organizacao em camadas (Q8)
O codigo SQL fica isolado nas classes DAO. As telas (view) nunca acessam o banco
diretamente: elas chamam os services, que aplicam as regras e delegam aos DAOs.
Os models carregam apenas os dados das entidades.
