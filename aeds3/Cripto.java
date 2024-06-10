package aeds3;

import java.lang.Math;



public class Cripto {

    // SUBSITUICAO VIGENERE

    private static byte[] vigenereCod(byte[] dado, String chave) {
        byte[] key = chave.getBytes();

        for (int i = 0; i < dado.length; i++) {
            int op = (dado[i] + key[i % key.length]) % 256;
            dado[i] = (byte) op;
        }

        return dado;
    }

    private static byte[] vigenereDecod(byte[] dado, String chave) {
        byte[] key = chave.getBytes();

        for (int i = 0; i < dado.length; i++) {
            int op = (dado[i] - key[i % key.length] + 256) % 256;
            dado[i] = (byte) op;
        }
        return dado;
    }

    private static void insertionSort(int[][] array) {

        for (int j = 1; j < array.length; j++) {
            int[] temp = array[j];
            int i = j - 1;
            while (i > -1 && array[i][0] > temp[0]) {
                array[i + 1] = array[i];
                i = i - 1;
            }
            array[i + 1] = temp;
        }

    }



    private static byte[] colunasCod(byte[] dado, String chave) {


        byte[] key = chave.getBytes();

        int tamdic = key.length;
        if(tamdic > dado.length){
            tamdic = dado.length;
        }


        int[][] dic = new int[tamdic][ 2 ];

        for (int i = 0; i < tamdic; i++) {
            dic[i][0] = key[i];
            dic[i][1] = i;
        }

        double size = Math.ceil((double) dado.length/tamdic);
        Byte[][] matriz = new Byte[tamdic][(int) size];
        if(dado.length < ((int) size * tamdic)){
            int quantidade = ( (int) size * tamdic) - dado.length ;
            for(int i = 0; i < quantidade; i++){
                matriz[tamdic - 1 - i] = new Byte[(int) size - 1];
            }
        }
        int prim = 0;
        int sec = 0;
        for(int i = 0; i < dado.length; i++){
            matriz[sec][prim] = dado[i];
            sec = (sec+1)%key.length;
            if(sec == 0){
                prim++;
            }
        }



        insertionSort(dic);
        int index = 0;
        for(int i = 0; i < dic.length; i++){
            int j = 0;
            while(matriz[dic[i][1]].length > j && matriz[dic[i][1]][j] != null){
                dado[index++] = matriz[dic[i][1]][j++];
            }
        }


        return dado;

    }

    private static byte[] colunasDecod(byte[] dado, String chave) {
        byte[] key = chave.getBytes();

        int tamdic = key.length;
        if(tamdic > dado.length){
            tamdic = dado.length;
        }


        int[][] dic = new int[tamdic][ 2 ];

        for (int i = 0; i < tamdic; i++) {
            dic[i][0] = key[i];
            dic[i][1] = i;
        }

        double size = Math.ceil((double) dado.length/tamdic);


        Byte[][] matriz = new Byte[tamdic][(int) size];
        if(dado.length < ((int) size * tamdic)){
            int quantidade = ( (int) size * tamdic) - dado.length ;
            for(int i = 0; i < quantidade; i++){
                matriz[tamdic - 1 - i] = new Byte[(int) size - 1];
            }
        }
        insertionSort(dic);
        
        int index = 0;
        int prim = 0;
        while(tamdic > prim){
            int m = dic[prim][1];
            for(int i = 0; i < matriz[m].length && index < dado.length; i++){
                matriz[m][i] = dado[index++];
            }
            prim++;
        }






        prim = 0;
        int sec = 0;
        for(int i = 0; i < dado.length && matriz[prim].length > sec; i++){
            dado[i] = matriz[prim][sec];
            prim = (prim + 1)%matriz.length;
            if(prim == 0){
                sec = sec + 1;
            }
        }
        


        return dado;

    }


    private static void printB (byte[] dado){
        System.out.print("[ ");
        for(byte b: dado){
            System.out.print(b + " ");
        }
        System.out.println("]\n");
    }

    public static byte[] cod(byte[] dado, String chave) {
        System.out.println("CODIFICANDO DADOS\n");
        printB(dado); System.out.println(" -> ");
        dado = vigenereCod(dado, chave);
        dado = colunasCod(dado, chave);
        printB(dado);
        return dado;
    }

    public static byte[] decod(byte[] dado, String chave) {
        System.out.println("DECODIFICANDO DADOS\n");
        printB(dado); System.out.println(" -> ");
        dado = colunasDecod(dado, chave); 
        dado = vigenereDecod(dado, chave);
        printB(dado);
        return dado;
    }



    
}