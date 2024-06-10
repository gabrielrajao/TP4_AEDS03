
import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;



import java.util.ArrayList;



class Principal {

  private static ArquivoLivros arqLivros;

  public static void menu(){

    


    Scanner console = new Scanner(System.in, "latin1");
      int opcao;



      do {

        //arqLivros.DEBUG();

        // imprimir menu
        System.out.println("\n\n-------------------------------");
        System.out.println("              MENU");
        System.out.println("-------------------------------");
        System.out.println("1 - Inserir");
        System.out.println("2 - Buscar");
        System.out.println("3 - Excluir");
        System.out.println("4 - Atualizar");
        System.out.println("5 - Criar Backup");
        System.out.println("6 - Decodificar Backup");
        System.out.println("7 - Busca ISBN");
        System.out.println("0 - Sair");
        try {
          opcao = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
          opcao = -1;
        }

        switch (opcao) {
          case 1: {// inserir livro
            System.out.println("\nINCLUSÃO");
            try {
              // ler os atributos do novo livro
              System.out.print("ISBN: "); 
              String isbn = console.nextLine();
              System.out.print("Nome: ");
              String nome = new String(console.nextLine().getBytes("UTF-8"), "UTF-8");




              System.out.print("Preco: ");
              float preco = Float.valueOf(console.nextLine());




              // criar novo objeto e envia-lo para a funcao da lista
              Livro liv = new Livro(-1, isbn, nome, preco);
              int livro = arqLivros.create(liv);
            } catch (Exception e) {
            }
          }
          break;

          case 2: {// buscar livro
            System.out.println("\nBUSCA");
            System.out.print("Chave de busca: ");
            try {
              // ler a chave de busca e criar uma lista com os resultados da procura
              String busca = console.nextLine();
              ArrayList<Livro> lista = arqLivros.read(busca);
              // imprimir resultados da consulta
              if (lista != null) {
                for (Livro l : lista) {
                  System.out.println(l.toString());
                }
              }
            } catch (Exception e) {
            }
          }
            break;

          case 3: {// deletar livro
            System.out.println("\nEXCLUSÃO");
            System.out.print("ISBN do livro a ser deletado: ");
            try {
              // ler id do registro a ser deletado e chamar a funcao responsável pela deleção
              String isbn = console.nextLine();
              arqLivros.delete(isbn);
            } catch (Exception e) {
            }
          }
            break;

          case 4: {// atualizar registro de livro
          System.out.println("\nATUALIZACAO"); 
          try {
            // ler atributos a serem atualizados
            System.out.print("ISBN do livro a ser atualizado: ");
            String antigoIsbn = console.nextLine();
            System.out.print("novo ISBN: ");
            String isbn = console.nextLine(); 
            System.out.print("novo Nome: ");
            String nome = console.nextLine();
            System.out.print("novo Preco: ");
            float preco = Float.valueOf(console.nextLine());
            // criar novo objeto e envia-lo para a funcao da lista
            Livro liv = new Livro(-1, isbn, nome, preco);
            arqLivros.update(liv, antigoIsbn);
          } catch (Exception e) {
          }
        }
            break;
          
          case 5: 
            System.out.println("\nBACKUP");
            try {
              // Funcao responsável por realizar a chamada da codificação
              arqLivros.fazBackUp();
            } catch (Exception e) {
              e.printStackTrace();
            }
            break;
          
          default: {
            
          }  
          break;
          case 6: 
          System.out.println("\nDECODIFICA BACKUP");
          try {
            System.out.print("Digite a versao desejada: ");
            int versao = Integer.valueOf(console.nextLine());
            // Funcao responsável por realizar a chamada da decodificação
            arqLivros.decodificaBackUp(versao);
          } catch (Exception e) {
            e.printStackTrace();
          }
          break;
          case 7:
          System.out.println("\nBUSCA ISBN");
          System.out.print("ISBN: ");
          try {
            // ler a chave de busca e criar uma lista com os resultados da procura
            String busca = console.nextLine();
            Livro lista = arqLivros.readISBN(busca);
            // imprimir resultados da consulta
            if (lista != null) {
                System.out.println(lista.toString());
            }
          } catch (Exception e) {
          }
          break;
          
        }

      } while (opcao != 0);
      console.close();
  }

  public static void executaComandos() throws Exception{

    
    Livro l2 = new Livro( "54214", "O Guerreiro Dragão", 55.99f);
    Livro l3 = new Livro( "54216", "Pequeno é o mundo sem você", 98.99f);
    Livro l4 = new Livro( "54220", "Da água para a pedra", 98.99f);
    Livro l5 = new Livro( "54265", "Sem água, apenas temos fogo", 98.99f);
    Livro l6 = new Livro( "54265", "Com água, apagamos o fogo", 98.99f);
    Livro l1 = new Livro("54210", "Tão pequeno quanto o mundo", 54.55f);

     
    arqLivros.create(l1);
    arqLivros.create(l2);
    arqLivros.create(l3);
    arqLivros.create(l4);
    arqLivros.create(l5);
    arqLivros.create(l6);

  
    
    
    
    
    

    arqLivros.fazBackUp();

    arqLivros.decodificaBackUp(1);
    

    arqLivros.DEBUG();


    
  


    /*
    //PRINTA TODOS OS REGISTROS 
    arqLivros.DEBUG();

    System.out.println("\n\n---------- Pesquisa1 -------------");
    for( Livro livro : arqLivros.read("Pequeno Mundo")){
      System.out.println(livro.toString());
    }

    System.out.println("\n\n---------- Pesquisa2 -------------");
    for( Livro livro : arqLivros.read("Água")){
      System.out.println(livro.toString());
    }

    System.out.println("\n\n---------- Pesquisa3 -------------");
    for( Livro livro : arqLivros.read("Sem você mundo")){
      System.out.println(livro.toString());
    }

    arqLivros.update(l6, "54265");

    arqLivros.DEBUG();

    arqLivros.delete("54210");

    arqLivros.DEBUG();
    */

    


  }




  public static void main(String args[]) {

    new File("dados/livros.db").delete();
    new File("dados/livros.hash_d.db").delete();
    new File("dados/livros.hash_c.db").delete();
    new File("dados/ArquivoDeExcluidos.db").delete();
    new File("dados/blocos.listainv.db").delete();
    new File("dados/dicionario.listainv.db").delete();

    try {

      // Abrindo arquivo para Livros
      arqLivros = new ArquivoLivros();


      //Temos duas opções de execução: o menu e escrever os códigos diretamente 
      //ATENCAO!!!!: O MENU NÂO ESTA LENDO ACENTUACAO CORRETAMENTE

      //menu();
      executaComandos();
      

      // Fim do programa
      arqLivros.close();
      

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  

}
