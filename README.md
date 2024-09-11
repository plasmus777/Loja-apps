# Loja-apps <img src="./assets/icons/store.png?raw=true" width="64" height="64">

Um projeto Java de uma possível loja de aplicativos, permitindo que usuários vejam, busquem, instalem, removam e atualizem diversos programas.

---

## Objetivo e Motivação
<p>O Projeto tem como objetivo implementar um sistema semelhante a uma loja de aplicativos, permitindo que o usuário gerencie aplicativos na própria loja e também na máquina local.</p>
<p>Por enquanto, o sistema lida apenas com abstrações de cada modelo de dados. Contudo, é possível realizar adaptações para que uma API externa seja utilizada ou até para que programas reais sejam instalados na máquina local.</p>
<p>A ideia principal é permitir que usuários de computadores possam utilizar softwares mais atualizados de maneira mais prática, facilitando a manutenção de múltiplos dispositivos, obtendo mais atualizações de segurança e evitando possíveis problemas gerados por uma instalação manual indevida.</p>

---

## Funcionalidades

### Aplicativos
- Listar aplicativos disponíveis na loja;
- Buscar por um aplicativo utilizando um identificador ou termos de pesquisa;
#### Funcionalidades disponíveis quando um usuário está conectado:
- Listar aplicativos instalados;
- Instalar aplicativos;
- Desinstalar aplicativos;
- Atualizar todos os aplicativos instalados;
#### Funcionalidades disponíveis quando um usuário editor está conectado:
- Cadastrar um aplicativo na loja;
- Atualizar um aplicativo na loja (do editor conectado);
- Apagar um aplicativo da loja (do editor conectado).

### Usuários
- Cadastrar um novo usuário (comum ou editor);
- Atualizar um usuário existente (requer e-mail e senha do usuário);
- Apagar um usuário existente (requer e-mail e senha do usuário);
- Buscar por um usuário utilizando um identificador ou termos de pesquisa;
- Listar usuários existentes.

### Login
- Tem o propósito de conectar um usuário para desbloquear funcionalidades do sistema.
- Ao conectar, gera um token de acesso que evita ao usuário ter que utilizar a senha dentro de um período de um dia, ou até que outro usuário faça login.

---

## Design

<p>O sistema foi criado com uma arquitetura que pode ser adaptada para lidar com APIs RESTful, garantindo maior flexibilidade nas possíveis melhorias a serem desenvolvidas e aplicações em maior escala.</p>
<p>No momento, as classes utilizadas no desenvolvimento do projeto podem ser divididas em:</p>

<details>

<summary> Implementação do Projeto</summary>

| Pacote                 | Classe              | Função                                                                                                                                                                                                                                                                                                        |
|------------------------|---------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| main                   | Main                | Início da aplicação, onde as variáveis e objetos utilizados durante a execução do programa são salvos.                                                                                                                                                                                                        |
| helper                 | InputHelper         | Classe utilitária, desenvolvida para facilitar a obtenção de entradas de dados válidos pelo terminal.                                                                                                                                                                                                         |
| model.application      | Application         | Classe modelo desenvolvida para representar um aplicativo, salvando todas as informações que um aplicativo na loja pode conter (como título, descrição, versão, editor, url com informações, etc.).                                                                                                           |
| model.application      | Category            | Enum modelo desenvolvido para representar as diversas categorias que a loja de aplicativos pode conter.                                                                                                                                                                                                       |
| model.authentication   | AuthToken           | Classe modelo que representa um token de autenticação do sistema, que pode ser utilizado para realizar login em conjunto a um e-mail que representa um usuário. Possui validade de um dia no sistema, e após esse período, torna-se expirado, necessitando que o usuário realize login com a senha novamente. |
| model.user             | User                | Classe modelo que representa um usuário no sistema, contendo um apelido, e-mail e token de autenticação.                                                                                                                                                                                                      |
| model.user             | Publisher           | Classe modelo que define um editor (usuário que pode publicar aplicativos). Contém informações extras como o nome da agência e o status de verificação.                                                                                                                                                       |
| repository             | Database            | Interface que define um objeto representativo de uma base de dados, podendo armazenar, atualizar, remover, buscar e listar objetos de um tipo genérico T.                                                                                                                                                     |
| repository             | ApplicationDatabase | Classe que define uma base de dados para o tipo Application (aplicativo).                                                                                                                                                                                                                                     |
| repository             | UserDatabase        | Classe que define uma base de dados para o tipo User (usuário).                                                                                                                                                                                                                                               |
| service                | Service             | Interface que define um objeto representativo de uma camada de serviço, lidando com métodos semelhantes a uma base de dados e contendo mais métodos de interação e busca contendo dois objetos de tipos genéricos T1 e T2.                                                                                    |
| service.application    | ApplicationService  | Classe que implementa Service<Application, Publisher> (aplicação e editor), lidando com ApplicationDatabase para garantir a integridade dos dados salvos/retornados.                                                                                                                                          |
| service.user           | UserService         | Classe que implementa Service<User, AuthToken> (usuário e token de autenticação), lidando com UserDatabase para garantir a integridade dos dados salvos/retornados.                                                                                                                                           |
| service.authentication | AuthService         | Classe criada para lidar com mecanismos de autenticação de usuários do programa, com métodos de login, logout e verificações do usuário conectado.                                                                                                                                                            |
| view | View                | Classe abstrata criada para definir uma visualização, isto é, mecanismo de interação com o usuário através do terminal. Fornece apenas dois métodos, show(exibir) e cleanTerminal(limpar terminal).                                                                                                           |
| view | ApplicationView     | Subclasse de View, que implementa a interação do usuário com as funcionalidades relacionadas aos aplicativos da loja.                                                                                                                                                                                         |
| view | AuthView            | Subclasse de View, que implementa a interação do usuário com as funcionalidades relacionadas aos mecanismos de autenticação/conexão de usuários.                                                                                                                                                              |
| view | MainView            | Subclasse de View, que implementa a interação do usuário com as outras visualizações do programa, servindo como um menu inicial.                                                                                                                                                                              |
| view | UserView            | Subclasse de View, que implementa a interação do usuário com as funcionalidades relacionadas a lidar com dados de usuários da loja.                                                                                                                                                                           |

</details>

---

## Download, compilação e execução
> [!WARNING]
> Para baixar, compilar e executar este programa, é necessário instalar: [Git](https://git-scm.com/downloads) e o [JDK](https://www.oracle.com/java/technologies/downloads/).

Utilizando um terminal, é possível baixar, compilar e executar este programa em sua máquina local com os seguintes comandos:

1 - Baixe o código deste repositório para a sua máquina:
```
git clone https://github.com/plasmus777/Loja-apps.git
```

2 - Compile o projeto:
```
cd Loja-apps

javac -cp "lib/*" -d bin $(Get-ChildItem -Recurse -Filter *.java | % { $_.FullName })
```

3 - Execute o projeto:
```
java -cp "bin;lib/*" com.github.plasmus777.main.Main
```