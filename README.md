# Trabalho Prático 4 de AEDS3!

Para esse trabalho, implementamos codificação de colunas e de Vigenère no código do TP3 

**Alunos do Grupo:** 
* Luiza Dias Corteletti &nbsp;
* Gabriel Martins Rajão &nbsp;
* Diego Marchioni Alves dos Santos


# Métodos e Classes Criadas

**vigenereCod:** Recebe o vetor de dados a ser codificado e a chave de codificação. Realiza a codificação de Vigenère por meio do módulo da soma do valor do dado e da chave. Retorna o vetor de dados codificado. 

**vigenereDecod:** Recebe o vetor de dados a ser decodificado e a chave de codificação. Realiza a decodificação pela equação matemática de módulo de 256 do valor do dado - valor da chave + 256. Retorna o vetor de dados decodificado. 

**insertionSort:**  Ordena as colunas da matriz da cifra de colunas em ordem crescente se baseando nas letras da chave. 

**colunasCod:** Recebe o vetor de dados a ser codificado e a chave de codificação. Coloca os dados em uma matriz e depois os lê na ordem das chaves recolocando-os no vetor e o retornando. 

**colunasDecod:** Recebe o vetor de dados a ser decodificado e a chave de codificação. Coloca os dados nas colunas da matriz e os lê na ordem horizontal retornando os dados decodificados. 

**printB:** Recebe um vetor de dados e os imprime.

**cod:** Codifica os dados os enviando primeiro para a função de codificação de Vigenère e depois para a função de codificação em colunas, imprimindo e retornando o resultado no final.

**decod:** Decodifica os dados os enviando primeiro para a função de codificação em colunas e depois para a função de codificação de Vigenère, imprimindo e retornando o resultado no final.

**Mudanças em toByteArray e fromByteArray** Agoras as funções são responsáveis por chamar a função de codificação e decodificação respectivamente, salvando no arquivo apenas a versão codificada dos dados. 


## Perguntas

**- Há uma função de cifragem em todas as classes de entidades, envolvendo pelo menos duas operações diferentes e usando uma chave criptográfica?**

Sim, todas as classes de entidades passam pela função de cifragem e decifragem, passando por duas operações diferentes (Veginère e Colunas) com a mesma chave. 

**- Uma das operações de cifragem é baseada na substituição e a outra na transposição?**

Sim, são usadas a cifra de Veginère (substituição) e cifra de colunas (transposição).

**-   O trabalho está funcionando corretamente?**

Sim, o trabalho está funcionando

**-   O trabalho está completo?**

Sim, o trabalho cumpre todos os requisitos do TP4

**-   O trabalho é original e não a cópia de um trabalho de um colega?**

Sim, fizemos o trabalho com base no nosso TP2 e no material disponibilizado em sala



# Métodos que estavam no TP3

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

**readISBN(String ISBN):** Método que recebe uma string ISBN e retorna um Livro ou NULL com base nessa string

**open():** Método que reabre os arquivos principais (fechamos os arquivos durante os processos de backup)

**BackUp(int versao, String arq):** Método do backup propriamente dito, recebe a versão do backup e a string com o nome do arquivo a ser compactado, ele lê 100 bytes do arquivo, os codifica e escreve o tamanho da mensagem codificada em uma short e depois escreve a mensagem codificada no arquivo de backup, depois ele repete o processo com os proximos 100 bytes, continuando até o fim do arquivo.

OBS.: Se o tamanho restante do arquivo for menor que 100 Bytes, é criado um array de tamanho igual ao tamanho restante

OBS2.: A variavel **TAMANHO_BYTES_BACKUP** armazena a quantidade de bytes pegos por vez (Está setada como 100 bytes)

**AtualizaVersao():** Método que pega a última versão de backup do arquivo index.db e atualiza para a nova versão, retornando a nova versão

**fazBackUp():** método fecha todos os arquivos abertos, depois executa o metodo **AtualizaVersao()** para conseguir a versão de backup mais recente e depois executa **BackUp(int versao, String arq)** para cada arquivo do banco de dados, por fim, ele reabre os arquivos fechados anteriormente 

**pegaVersao():** Retorna a última versão de backup, salva no index.db

**DecodificaBackUp(int versao, String arq):** Deleta o arquivo do banco de dados com o nome da varaivel **arq**, depois lê o arquivo de backup com o mesmo nome, primeiramente ele pega o tamanho da mensagem codificada, cria um array de bytes com esse tamanho e lê o arquivo até enche-lo, depois decodifica a mensagem e salva no arquivo do banco de dados padrão

**decodificaBackUp(int versao):** Fecha os arquivos abertos atualmente, depois executa **pegaVersao()** para conseguir a ultima versão do backup, se a versão recebida por parametro foi menor que 1 ou maior que a  versão recebida por **pegaVersao()**, ele printa no console "VERSAO NAO ENCONTRADA". Caso o parametro recebido seja valido, ele executa 

**DecodificaBackUp(int versao, String arq)** para cada arquivo do banco de dados e, por fim, reabre os arquivos.

