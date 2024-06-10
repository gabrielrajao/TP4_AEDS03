# Trabalho Prático 3 de AEDS3!

Para esse trabalho, implementamos backups compactados pelo algoritmo LZW no código do TP2 

**Alunos do Grupo:** 
* Luiza Dias Corteletti &nbsp;
* Gabriel Martins Rajão &nbsp;
* Diego Marchioni Alves dos Santos


# Métodos e Classes Criadas

**readISBN(String ISBN):** Método que recebe uma string ISBN e retorna um Livro ou NULL com base nessa string

**open():** Método que reabre os arquivos principais (fechamos os arquivos durante os processos de backup)

**BackUp(int versao, String arq):** Método do backup propriamente dito, recebe a versão do backup e a string com o nome do arquivo a ser compactado, ele lê 100 bytes do arquivo, os codifica e escreve o tamanho da mensagem codificada em uma short e depois escreve a mensagem codificada no arquivo de backup, depois ele repete o processo com os proximos 100 bytes, continuando até o fim do arquivo.

OBS.: Se o tamanho restante do arquivo for menor que 100 Bytes, é criado um array de tamanho igual ao tamanho restante

OBS2.: A variavel **TAMANHO_BYTES_BACKUP** armazena a quantidade de bytes pegos por vez (Está setada como 100 bytes)

**AtualizaVersao():** Método que pega a última versão de backup do arquivo index.db e atualiza para a nova versão, retornando a nova versão

**fazBackUp():** método fecha todos os arquivos abertos, depois executa o metodo **AtualizaVersao()** para conseguir a versão de backup mais recente e depois executa **BackUp(int versao, String arq)** para cada arquivo do banco de dados, por fim, ele reabre os arquivos fechados anteriormente 

**pegaVersao():** Retorna a última versão de backup, salva no index.db

**DecodificaBackUp(int versao, String arq):** Deleta o arquivo do banco de dados com o nome da varaivel **arq**, depois lê o arquivo de backup com o mesmo nome, primeiramente ele pega o tamanho da mensagem codificada, cria um array de bytes com esse tamanho e lê o arquivo até enche-lo, depois decodifica a mensagem e salva no arquivo do banco de dados padrão

**decodificaBackUp(int versao):** Fecha os arquivos abertos atualmente, depois executa **pegaVersao()** para conseguir a ultima versão do backup, se a versão recebida por parametro foi menor que 1 ou maior que a  versão recebida por **pegaVersao()**, ele printa no console "VERSAO NAO ENCONTRADA". Caso o parametro recebido seja valido, ele executa **DecodificaBackUp(int versao, String arq)** para cada arquivo do banco de dados e, por fim, reabre os arquivos.



## Perguntas

**-   Há uma rotina de compactação usando o algoritmo LZW para fazer backup dos arquivos?**

Sim, usamos o algortimo LZW feito em sala para compactar o backup dos arquivos

**-   Há uma rotina de descompactação usando o algoritmo LZW para recuperação dos arquivos?**

Sim, usamos o algortimo LZW feito em sala para descompactar o backup dos arquivos

**-   O usuário pode escolher a versão a recuperar?**

Sim, o usuário pode digitar a versão que ele deseja recuperar, as versões são sequenciais e a versão mais recente é salva em uma short no arquivo index.db dentro da pasta backup

**-   Qual foi a taxa de compressão alcançada por esse backup? (Compare o tamanho dos arquivos compactados com os arquivos originais)**

Temos um teste do backup disponibilizado na execução padrão de comandos ( Selecionada no programa atualmente, para mudar para a versão de menu, comente a chamada dos comandos e remova o // do método menu() ).
Nesse teste conseguimos uma diferença de 71 bytes, onde os arquivos originais pesaram 871 bytes e os de backup 800 bytes (Contando com o arquivo index.db)

![Imagem de Comparacao](https://github.com/DiegoMarchioni/TP3AED3/blob/main/ComparacaoBACKUPeNORMAL.png)

**-   O trabalho está funcionando corretamente?**

Sim, o trabalho está funcionando

**-   O trabalho está completo?**

Sim, o trabalho cumpre todos os requisitos do TP3

**-   O trabalho é original e não a cópia de um trabalho de um colega?**

Sim, fizemos o trabalho com base no nosso TP2 e no material disponibilizado em sala



# Métodos que estavam no TP2

**Menu na Main:** Menu na main que opera pela estrutura condicional switch case no console dando as opções de: inserir novo registro, buscar uma chave, deletar um registro e atualizar um registro para o usuário.
ATENÇÃO!!! A opção do menu não lida muito bem com acentuação, adicionamos um procedimento para execução dos comandos normalmente (sem menu)

**Classe ArquivoLivros:** Criamos essa classe para que tivessemos uma classe de arquivos não genérica, visto que o TP2 pede uma lista invertida de Livros

**Método Tamanho na classe Arquivo:** Adicionamos esse método para recuperar o último id do arquivo ( apenas para debug mesmo )

**Método Create na classe ArquivoLivros:** Neste método, criamos o objeto de livro no arquivo principal normalmente e, depois, criamos um índice para cada palavra do título do livro com base no id recebido pelo método create principal, para fazer isso, partimos o titulo em cada espaço, depois removemos pontos (tipo: ; : ! ? . , ) e utilizamos toLowerCase() para normalizar a palavra

**Método Update na classe ArquivoLivros:** Neste método, fazemos um update de um livro no arquivo principal normalmente, depois deletamos todos os indices da lista invertida que estavam associados ao título antigo e, por fim, adicionamos os indices do título novo do livro.

**Método Delete na classe ArquivoLivros:** Neste método, fazemos a deleção de um livro no arquivo principal normalmente e, depois, deletamos as palavras da listaInvertida associadas ao livro deletado uma por uma

**Método LimparString na classe ArquivoLivros:** Fiz esse método para melhorar na praticidade de limpar as strings, adicionei, também, a funcionalidade de remoção da acentuação

**Método Limpar na classe ArquivoLivros:** Esse método é auxiliar para o Read, ele quebra uma string em todos os espaços (" "), resultando um array de strings, depois remove pontos (tipo: ; : ! ? . , ) e utiliza toLowerCase() para normalizar a palavra. Por fim, o método checa se foi existem stopwords no array e as remove.

**Método de União na classe ArquivoLivros:** Esse método é responsável por realizar a união descrita no método READ, se ele receber os conjuntos (2,3,5) e (1,3,5), vai retornar (3,5) 

**Método Read na classe ArquivoLivros:** Neste método, recebemos uma string por parametros e separamos elas nos espaços em branco e depois limpamos ela, isso nos retorna um array de strings, se o array de strings conter apenas uma palavra, nós simplesmente fazemos uma pesquisa pelos livros que contém essa palavra e retornamos isso. Se o array conter mais de uma palavra, fazemos a interseção do conjunto de livros que possui cada uma das palavras. **Exemplo:** 
|Palavra| Livros  |
|--|--|
| Dados | 1, 2, 4  |
| Algoritmo | 1, 3, 4  |

Fazemos a união dos dois conjuntos e temos os livros 1 e 4, se adicionarmos a palavra abaixo:
|Palavra| Livros  |
|--|--|
| Estrutura | 4  |


Fazemos a união do conjunto 1 e 4 com o conjunto de Estrutura, resultando no livro 4 apenas.

**Método getStopwords na classe ArquivoLivros:** Baixamos um dataset com inúmeras stopwords (em português) do site Kaggle (https://www.kaggle.com/datasets/heeraldedhia/stop-words-in-28-languages), a função getStopwords pega todas as Stopwords no arquivo baixado e coloca em um ArrayList

**Método DEBUG:** Um método que criamos para printar todos os registros no arquivo principal e na lista invertido, só para ter certeza que está tudo nos conformes
