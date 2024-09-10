# Loja-apps <img src="./assets/icons/store.png?raw=true" width="64" height="64">

Um projeto Java de uma possível loja de aplicativos, com o objetivo de permitir que usuários vejam, busquem e instalem diversos programas.

---

## Objetivo

<p>O Projeto tem como objetivo implementar um sistema semelhante a uma loja de aplicativos, permitindo que o usuário gerencie aplicativos na própria loja e também na máquina local.</p>
<p>Por enquanto, o sistema lida apenas com abstrações de cada modelo de dados. Contudo, é possível realizar adaptações para que uma API externa seja utilizada ou até para que programas reais sejam instalados na máquina local.</p>


---

## Funcionalidades



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