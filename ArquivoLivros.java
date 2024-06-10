

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.ArrayList;

import aeds3.LZW;
import aeds3.Arquivo;
import aeds3.ListaInvertida;

public class ArquivoLivros {
    Arquivo<Livro> arqLivros;
    ListaInvertida listaInvertida;
    ArrayList<String> listaStopwords;
    private final int TAMANHO_BYTES_BACKUP = 100;

    public ArquivoLivros() throws Exception{


        //criamos o objeto de arquivo de livros
        arqLivros = new Arquivo<>("livros", Livro.class.getConstructor());
        //criamos o objeto de lista invertida
        listaInvertida = new ListaInvertida(4, "dados/dicionario.listainv.db", "dados/blocos.listainv.db");
        //funcao para criar arraylist a partir do arquivo de stopwords
        listaStopwords = getStopwords();
    }

    //Cria a lista de stopwords
    ArrayList<String> getStopwords() throws Exception{
        //abre o arquivo db que contém as stopwords
        RandomAccessFile arqStopWords = new RandomAccessFile("./dados/estaticos/stopwords.db", "r");
        //vai para o inicio do arquivo
        arqStopWords.seek(0);
        // cria um array list de strings
        ArrayList<String> result = new ArrayList<String>();
        // enquanto existirem registros no arquivo, adicionamos as strings ao arraylist
        while(arqStopWords.length() > arqStopWords.getFilePointer()){
            result.add(arqStopWords.readUTF());
        }

        //fechamos o arquivo e retornamos o arraylist resultante
        arqStopWords.close();
        return result;
    }

    //DEBUG
    public void DEBUG() throws Exception{
        System.out.println("\n\n-----------------Printando Todos Os Registros---------------------");
        //programa para printar todos os registros e registros da lista invertida (teste)
        for(int i = 1; i <= arqLivros.tamanho(); i++){
            Livro obj = arqLivros.read(i);
            if(obj != null){
                System.out.println(obj.toString());
            } else{
                System.out.println("Registro deletado");
            }
        }

        listaInvertida.print();

        System.out.println("\n\n-----------------Fim Printando Todos Os Registros---------------------");
        
    }

    //Create
    public int create(Livro obj) throws Exception{


        //criamos o objeto no arquivo de livros
        int id = arqLivros.create(obj);

        //se a criação for um sucesso e nos retornar um id valido
        if(id > 0){
            //Separa as  palavras por espaço em branco
            String[] listaPalavras = obj.getTitulo().split(" ");

            //Para cada palavra na lista de palavras do titulo
            for( String palavra : listaPalavras){
                //Remove pontos e transforma a palavra em lowerCase (limpa as palavras)
                String limpa = limparPalavra(palavra);

                //Só criamos um indice na lista invertida se a palavra não for uma stopword
                if(listaStopwords.contains(limpa) == false){
                    listaInvertida.create(limpa, id);
                } 
            }
        }

        


        


        return id;
    }

    

    //Update
    public boolean update(Livro novoObj, String antigoIsbn)throws Exception{
        
        //Realizando a leitura sequencial no arquivo procurando com base no ISBN para retornar o id
        int id = arqLivros.readISBN(antigoIsbn);
        
        //atualizando o id do objeto recebido
        novoObj.setID(id);
        
        //buscamos o obj original no arquivo de livros
        Livro obj = arqLivros.read(novoObj.getID());

        // se o objeto lido for diferente de null, fazemos o update no arquivo principal e, se for um sucesso
        if( obj != null && arqLivros.update(novoObj) == true ){


            // ---------------------------------------------------------------
            // PARTE de delecao (DELETAMOS TODOS OS INDICES DO TITULO ANTIGO)
            // ---------------------------------------------------------------

            //Separa as  palavras por espaço em branco
            String[] listaPalavras = obj.getTitulo().split(" ");


            //Para cada palavra na lista de palavras do titulo
            for( String palavra : listaPalavras){
                //Remove pontos e transforma a palavra em lowerCase (limpa as palavras)
                String limpa = limparPalavra(palavra);

        

                //deletamos todas as palavras do titulo, com id 1 ( listainvertida trata as que nao existem)
                listaInvertida.delete(limpa, obj.getID());

                
            }

            // ---------------------------------------------------------------
            // PARTE de criacao (CRIAMOS OS INDICES DO TITULO NOVO)
            // ---------------------------------------------------------------

            //Separa as  palavras por espaço em branco
            listaPalavras = novoObj.getTitulo().split(" ");

            //Para cada palavra na lista de palavras do titulo
            for( String palavra : listaPalavras){
                //Remove pontos e transforma a palavra em lowerCase (limpa as palavras)
                String limpa = limparPalavra(palavra);
                

                //Só criamos um indice na lista invertida se a palavra não for uma stopword
                if(listaStopwords.contains(limpa) == false){
                    listaInvertida.create(limpa, novoObj.getID());
                } 
            }

            

            //retornamos true, se completar
            return true;
        }

        return false;
    }

    //Delete
    public boolean delete(String isbn)throws Exception{
        
        //Realizando a leitura sequencial no arquivo procurando com base no ISBN para retornar o id
        int id = arqLivros.readISBN(isbn);

        //Testando se algum id foi achado de acordo com o ISBN
        if(id == -1){
            return false;
        }

        //buscamos o obj original no arquivo de livros
        Livro obj = arqLivros.read(id);

        // se o objeto lido for diferente de null, fazemos a delecao no arquivo principal e, se for um sucesso
        if( obj != null && arqLivros.delete(id) == true ){


            //Separa as  palavras por espaço em branco
            String[] listaPalavras = obj.getTitulo().split(" ");


            //Para cada palavra na lista de palavras do titulo
            for( String palavra : listaPalavras){
                //Remove pontos e transforma a palavra em lowerCase (limpa as palavras)
                String limpa = limparPalavra(palavra);

        

                //deletamos todas as palavras do titulo, com id 1 ( listainvertida trata as que nao existem)
                listaInvertida.delete(limpa, id);

                
            }

            //retornamos true, se completar
            return true;

        }

        return false;
    }

    //Limpa uma palavra
    private String limparPalavra(String palavra){
        return palavra.replace(",", "")
        .replace(".", "")
        .replace(":", "")
        .replace(";", "")
        .replace("!", "")
        .replace("?", "")
        .toLowerCase()
        //cedilha
        .replace("ç", "c")
        //a
        .replace("á", "a")
        .replace("à", "a")
        .replace("â", "a")
        .replace("ã", "a")
        .replace("ä", "a")
        //e
        .replace("é", "e")
        .replace("è", "e")
        .replace("ê", "e")
        .replace("ë", "e")
        //i
        .replace("í", "i")
        .replace("ì", "i")
        .replace("î", "i")
        .replace("ï", "i")
        //o
        .replace("ó", "o")
        .replace("ò", "o")
        .replace("ô", "o")
        .replace("ö", "o")
        .replace("õ", "o")
        //u
        .replace("ú", "u")
        .replace("ù", "u")
        .replace("û", "u")
        .replace("ü", "u");
    }

    //Limpa a string de pesquisa
    private ArrayList<String> limpar(String frase){
        String[] lista = frase.split(" ");
        ArrayList<String> result = null;

        
        
        for(int i = 0; i < lista.length; i++){
            lista[i] = limparPalavra(lista[i]);

            if(listaStopwords.contains(lista[i]) == false){
                if(result == null){
                    result = new ArrayList<String>();
                }
                result.add(lista[i]);
            }

        }

        return result;
    }
    

    //realiza a união entre dois conjuntos de ints
    private int[] uniao(int[] primario, int[] secundario){
        int tamanho = 0;
        for(int i = 0; i < primario.length; i++){
            boolean found = false;
            for(int j = 0; j < secundario.length; j++){
                if(primario[i] == secundario[j]){
                    found = true;
                    j = secundario.length;
                }
            }
            if(found == false){
                primario[i] = -1;
            } else {
                tamanho++;
            }
        }

        if(tamanho > 0){
            int[] resultado = new int[tamanho];
            int index = 0;
            for(int i = 0; i < primario.length; i++){
                if(primario[i] != -1){
                    resultado[index++] = primario[i];
                }
            }
            return resultado;
        } else {
            return null;
        }

    }


    //Read
    public ArrayList<Livro> read(String pesquisa) throws Exception{

        //prepara o arrayList de livros para retorno
        ArrayList<Livro> result = null;


        //separa e limpa as palavras da string
        ArrayList<String> termos = limpar(pesquisa);

        if(termos.size() > 0){
            //realiza a pesquisa da string na listaInvertida e recebe os resultados
            int[] resultadopesq = listaInvertida.read(termos.get(0));


            //se forem encontrados resultados
            if(resultadopesq.length > 0){
                //se a pesquisa for de um termo apenas
                if(termos.size() == 1){
                    //criamos o arraylist de livros propriamente dito e salvamos na variavel de retorno
                    result = new ArrayList<Livro>();

                    //enquanto o resultado da pesquisa tiver objetos
                    for(int i = 0; i < resultadopesq.length; i++){
                        //pesquisamos o id do objeto encontrado no arquivo de livros
                        Livro l = arqLivros.read(resultadopesq[i]);
                        // se o livro for diferente de null
                        if(l != null){
                            //salvamos na variavel de retorno
                            result.add(l);
                        }
                    }
                }
                //se a pesquisa tiver mais de um termo
                else{
                    //enquanto tivermos termos disponiveis
                    for(int i = 1; i < termos.size() && resultadopesq!=null; i++){
                        //fazemos a pesquisa na listainvertida do termo secundario para a operacao 
                        int[] termosecundario = listaInvertida.read(termos.get(i));
                        //fazemos a uniao do termo primario com o termo secundario
                        resultadopesq = uniao(resultadopesq, termosecundario);
                    }

                    //se a união retornar ids
                    if(resultadopesq != null){
                        //criamos o arraylist de livros propriamente dito e salvamos na variavel de retorno
                        result = new ArrayList<Livro>();

                        //enquanto o resultado da pesquisa tiver objetos
                        for(int i = 0; i < resultadopesq.length; i++){
                            //pesquisamos o id do objeto encontrado no arquivo de livros
                            Livro l = arqLivros.read(resultadopesq[i]);
                            // se o livro for diferente de null
                            if(l != null){
                                //salvamos na variavel de retorno
                                result.add(l);
                            }
                        }
                    } 

                }
            }
        }

        //retorna a variavel result
        return result;

    }
    




    public void close() throws Exception{
        //fecha os arquivos abertos
        arqLivros.close();
        listaInvertida.close(); 
    }

    //Novos metodos

    public Livro readISBN(String ISBN) throws Exception{
        int id = arqLivros.readISBN(ISBN);
        return arqLivros.read(id);
    }

    private void open() throws Exception{
        //criamos o objeto de arquivo de livros
        arqLivros = new Arquivo<>("livros", Livro.class.getConstructor());
        //criamos o objeto de lista invertida
        listaInvertida = new ListaInvertida(4, "dados/dicionario.listainv.db", "dados/blocos.listainv.db");
    }


    private void BackUp(int versao, String arq)throws Exception{
                // Leitura do arquivo usando RandomAccessFile
                try {
                    File pasta = new File("./backup/"+versao+"/");
                    pasta.mkdir();
                    RandomAccessFile arquivo = new RandomAccessFile("./dados/"+arq+".db", "r");
                    RandomAccessFile arquivoesc = new RandomAccessFile("./backup/"+versao+"/"+arq+".db", "rw");
                    arquivo.seek(0);
                    arquivoesc.seek(0);
                    while (arquivo.getFilePointer() < arquivo.length()) {
                        long tamanhosoblong = arquivo.length() - arquivo.getFilePointer();
                        int tamanhosob = (int) tamanhosoblong;
                        byte[] ba = new byte[TAMANHO_BYTES_BACKUP];
                        if(tamanhosob < TAMANHO_BYTES_BACKUP){
                            ba = new byte[tamanhosob];
                        }
                        arquivo.read(ba);



                        byte[] mensagemCodificada = LZW.codifica(ba);


                        arquivoesc.writeShort(mensagemCodificada.length);
                        arquivoesc.write(mensagemCodificada);
                    }
                    arquivo.close();
                    arquivoesc.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }


    private int AtualizaVersao()throws Exception{
        int versao;
        RandomAccessFile arquivo = new RandomAccessFile("./backup/index.db", "rw");
        if(arquivo.length() < 4){
            arquivo.seek(0);
            versao = 1;
            arquivo.writeInt(1);
        } else{
            arquivo.seek(0);
            versao = arquivo.readInt();
            arquivo.seek(0);
            arquivo.writeInt(++versao);
            
        }
        arquivo.close();
        return versao;
    }

    public void fazBackUp() throws Exception{
        this.close();

        int versao = AtualizaVersao();


        //criacao de variaveis

        //

        BackUp(versao, "ArquivoDeExcluidos");
        BackUp(versao, "blocos.listainv");
        BackUp(versao, "dicionario.listainv");
        BackUp(versao, "livros");
        BackUp(versao, "livros.hash_c");
        BackUp(versao, "livros.hash_d");

        //
        this.open();

    }

    private int pegaVersao()throws Exception{
        int versao;
        RandomAccessFile arquivo = new RandomAccessFile("./backup/index.db", "r");
        if(arquivo.length() < 4){
            versao = 0;
        } else{
            arquivo.seek(0);
            versao = arquivo.readInt();
            
        }
        arquivo.close();
        return versao;

    }

    public void decodificaBackUp(int versao) throws Exception{
        this.close();
        int max = pegaVersao();
        if(versao > 0 && versao <= max){
            DecodificaBackUp(versao, "ArquivoDeExcluidos");
            DecodificaBackUp(versao, "blocos.listainv");
            DecodificaBackUp(versao, "dicionario.listainv");
            DecodificaBackUp(versao, "livros");
            DecodificaBackUp(versao, "livros.hash_c");
            DecodificaBackUp(versao, "livros.hash_d");
        } else{
            System.out.println("VERSAO NAO ENCONTRADA");
        }

        //
        this.open();

    }

    //RELER DPS
    private void DecodificaBackUp(int versao, String arq)throws Exception{
        // Leitura do arquivo usando RandomAccessFile
            new File("dados/"+arq+".db").delete();
            RandomAccessFile arquivoesc = new RandomAccessFile("./dados/"+arq+".db", "rw");
            RandomAccessFile arquivo = new RandomAccessFile("./backup/"+versao+"/"+arq+".db", "rw");
            arquivo.seek(0);
            arquivoesc.seek(0);

            while (arquivo.getFilePointer() < arquivo.length()) {
                short tam = arquivo.readShort();
                byte[] ba = new byte[tam];
                arquivo.read(ba);



                byte[] mensagemDecodificada = LZW.decodifica(ba);

                
                
                arquivoesc.write(mensagemDecodificada);
            }
            arquivo.close();
            arquivoesc.close();
}

    
}
