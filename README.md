# Loja-apps <img src="./assets/icons/store.png?raw=true" width="64" height="64">

Um projeto Java de uma possível loja de aplicativos, com o objetivo de permitir que usuários vejam, busquem e instalem diversos programas.

---

## Objetivo

<p>O Projeto tem como objetivo implementar um sistema semelhante a uma loja de aplicativos, permitindo que o usuário gerencie aplicativos na própria loja e também na máquina local.</p>
<p>Por enquanto, o sistema lida apenas com abstrações de cada modelo de dados. Contudo, é possível realizar adaptações para que uma API externa seja utilizada ou até para que programas reais sejam instalados na máquina local.</p>


---

## Funcionalidades

## Aplicativos
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

## Usuários
- Cadastrar um novo usuário (comum ou editor);
- Atualizar um usuário existente (requer e-mail e senha);
- Apagar um usuário existente (requer e-mail e senha);
- Buscar por um usuário utilizando um identificador ou termos de pesquisa;
- Listar usuários existentes.

## Login
- Tem o propósito de conectar um usuário para desbloquear funcionalidades do sistema.
- 

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