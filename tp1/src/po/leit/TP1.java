package po.leit;

import po.leit.ui.Le;
import po.leit.ui.MyCommand;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TP1<notaMax, notaMin, notaAvg, soma, cont> {

    private static MyCommand interC;
    static final int MAX_ALUNOS = 35;
    private static int alunosLidos=0;
    private static int notaMax = 0;
    private static int notaMin = 0;
    private static int notaAvg = 0;

    private static String[] nomeAlunos = new String[MAX_ALUNOS];
    private static int[] notasAlunos = new int[MAX_ALUNOS];

    public static void main(String[] args) {
        boolean querSair=false;

        interC=new MyCommand();

        do {
            interC.limparEcra();
            interC.showPrompt();
            String[] cmdEscrito = interC.lerComando();
            ArrayList<String> cmd = interC.validarComando(cmdEscrito);

            if (cmd == null) {
                interC.showMsg("Comando inválido. Digite help para ajuda");

            } else {
                if  ( cmd.get(0).equalsIgnoreCase("carregar") ) {
                    alunosLidos = loadData(nomeAlunos, "turmaLeit.txt");
                    int notA = loadData(notasAlunos);
                    if ( alunosLidos != notA ) {
                        System.out.println("alunos = " + alunosLidos);
                        System.out.println("notaA = " + notA);
                        interC.showMsg("Erro carregando dados");
                    }
                        
                    else

                        interC.showMsg("Dados carregados OK!");
                } else if (cmd.get(0).equalsIgnoreCase("listar") ) {
                    mostrarAlunos();

                } else if (cmd.get(0).equalsIgnoreCase("paginar") ) {
                    String input = JOptionPane.showInputDialog("Nũmeros estudantes por pãgina :");
                    int numeroU = Integer.parseInt(input);
                    mostrarAlunos(numeroU);

                } else if (cmd.get(0).equalsIgnoreCase("mostrarp") ) {
                    mostrarPauta();

                } else if (cmd.get(0).equalsIgnoreCase("mostrarr") ) {
                    mostraResumo();

                } else if (cmd.get(0).equalsIgnoreCase("top") ) {
                    mostrarTop();

                } else if (cmd.get(0).equalsIgnoreCase("pesquisarnome") ) {
                    String nomePesq = JOptionPane.showInputDialog("O que procura  :");
                    pesquisar(nomePesq);

                } else if (cmd.get(0).equalsIgnoreCase("pesquisarnota") ) {
                    String vaPesq = JOptionPane.showInputDialog("O que procura  :");
                    int notaPesq = Integer.parseInt(vaPesq);
                    pesquisar(notaPesq);
                } else if (cmd.get(0).equalsIgnoreCase("help") ) {
                    interC.showHelp();

                } else if (cmd.get(0).equalsIgnoreCase("terminar") ) {
                    querSair = true;
                }
            }

        } while (!querSair);

    }

    /**
     * Método implementado por Prof. Não devem alterar. Este método recebe
     * como parâmetros um array e um ficheiro
     * Lẽ cada linha do ficheiro e guarda no array. Retorna o número
     * de linhas que forma lidas do ficheiro.
     * @param lAlunos
     * @param nomeFicheiro
     * @return quantos nomes foram lidos do ficheiro -1 se não possível ler ficheiro
     */
    public static int loadData(String[] lAlunos, String nomeFicheiro) {
        Scanner in = null;
        File inputFile = new File(nomeFicheiro);
        //PrintWriter out = new PrintWriter(outputFileName);
        try {
            in = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int i = 0;
        while (in.hasNextLine()) {
            String nomeAl = in.nextLine();
            if ( (nomeAl != null) && !(nomeAl.isBlank()) && !(nomeAl.isEmpty() ) ) {
                lAlunos[i] = nomeAl;
                i++;
            }

        }
        in.close();
        return i;
    }

    /**
     * Método implementado por Prof. Não devem alterar. Este método recebe
     * como parâmetros um array de inteiros e vai gerar aleatoriamente valores inteiros entre
     * 0 e 20 que representam a nota de cada aluno.
     * @param lNotas
     * @return how much name was read from the files -1 if was not able to read the file
     */
    public static int loadData(int[] lNotas) {
        Random rand = new Random();
        int cont = 0;
        for (cont=0; cont < alunosLidos; cont++) {
            int randomNum = rand.nextInt(20) + 1;
            notasAlunos[cont] = randomNum;
        }
        return cont;
    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá listar todos os nomes dos alunos guardados no array nomesAlunos.
     * O método deverá verificar se já foi carregado os dados para o array. Se sim mostra os
     * nomes dos alunos. Senão deve mostrar a mensagem "Não há dados"
     * @param
     * @return
     */
    public static void mostrarAlunos() {
        if (nomeAlunos[0] == null) {
            System.out.println("Não ha dados");
        } else {
            for (int w = 0; w <= nomeAlunos.length - 1; w++) {
                if (nomeAlunos[w] != null) {
                    int cont = w + 1;
                    System.out.println(" " + cont + "Aluno:" + nomeAlunos[w]);
                }
            }
        }
        interC.showMsg("");
    }

    /**
     * Método a ser implementando no TP1
     * O método deverá listar todos os nomes dos alunos guardados no array nomesAlunos.
     * O método deverá verificar se já foi carregado os dados para o array. Se sim mostra os
     * nomes dos alunos. Senão deve mostrar a mensagem "Não há dados".
     * Neste método os dados não são mostrados todos de uma só vez. Devem ser apresentados até encher a tela.
     * Vamos supor que 10 nomes enchem a tela. Então deverá ser apresentados de 10 em 10. Esse número
     * que indica quantos nomes enchem a tela é um parâmetro do método.
     * @param tela é um inteiro que indica quantos alunos são mostrados.
     */
    public static void mostrarAlunos(int tela) {
       boolean mudarPagina = false;
        int pagina = 0;
        if (nomeAlunos[0] == null) {
            interC.showMsg("não ha dados");
        } else {
            System.out.printf("%-2s %-25s", "codigo", " Nome do estudante");
            System.out.print("\n");
            int contador = 1;
            for (String nome : nomeAlunos) {
                if (nome != null && nome.length() > 0) {
                    if (mudarPagina) {
                        System.out.printf("%-2s %-25s", "codigo", " Nome do estudante");
                        System.out.print("\n");
                        mudarPagina = false;
                    }
                    System.out.printf("%06d %-25s", contador++, nome.trim().strip());
                    System.out.print("\n");
                } else {
                    continue;
                }
                if (pagina == tela) {
                    interC.showMsg("\n Enter para continuar...");
                    pagina = 0;
                    mudarPagina = true;
                }
            }
        }
       Le.umChar();



    /*if(nomeAlunos[0]==null){
        System.out.println("Não ha dados");
    }
    int cont=0, cod=1;
    int salto = (alunosLidos/tela)+1;

    for(int i=cont;i<=salto;i++){

        System.out.println("Codigo"+" "+"Nome Estudante");
        System.out.println();
        for (int n = cont ; n<((n*tela)< nomeAlunos.length ? n = tela-1) ; n++) {
            final DecimalFormat decimal = new DecimalFormat("00000");
            if(nomeAlunos[i] == null)
                break;

            System.out.println(decimal.format(cont++)+" ");
            System.out.printf("%-18s ",nomeAlunos[i]);
            System.out.println();
        }
        cont +=tela;

        interC.showMsg("Enter para continuar");
    }*/


    }


    /**
     * Método a ser implementando no TP1.
     * O método deverá percorrer o array de notas, calcular o valor da média aritmética de notas, a nota máximo e
     * a nota mínima.
     * Os valores calculados devem ser guaraddos na variáveis notaAVG (média),
     * notaMax (nota máxima) e notaMin(nota mínima)
     * Devem validar se o array de notas tem elementos. Se estiver vazio devem somente apresentar
     * a mensagem "Não há dados"
     */
    private static void calcularMaxMinAvg() {
        int soma=0, cont=0, w;
        if(nomeAlunos[0]==null){
            System.out.println("Não ha dados");
        }
        else{
            for(w=0; w<= notasAlunos.length-1; w++)
                if(nomeAlunos[0]!=null){
                    if(notasAlunos[w]>notaMax){
                        notaMax= notasAlunos[w];
                    }
                    else if(notasAlunos[w] < notaMin) {
                        notaMin = notasAlunos[w];
                }
                    soma+= notasAlunos[w];
                    cont++;

                }

            }
            notaAvg=soma/cont;
            System.out.println("Nota maxima :" +notaMax);
            System.out.println("Nota minima :" +notaMin);
            System.out.println("Nota média :" +notaAvg);
    }




    /**
     * Método a ser implementando no TP1.
     * O método deverá apresentar um resumo da avaliação;
     * Nota máxima, Nota mínima, Nota média. Número de alunos com nota superior a média e número de alunos com
     * nota inferior a média.
     * a mensagem "Não há dados"
     */
    public static void mostraResumo() {
        int notasuperior = 0, notainferior = 0, w;
        calcularMaxMinAvg();
        for (w = 0; w <= notasAlunos.length - 1; w++) {
            if (nomeAlunos[0] != null) {
                if (notasAlunos[w] >= notaAvg) {
                    notasuperior += 1;
                } else {
                    notainferior += 1;
                }
            }

        }
        System.out.println("Alunos com nota abaixo da média:" +notainferior);
        System.out.println("Alunos com nota acima da média:" +notasuperior);
        interC.showMsg(" ");

    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá apresentar o nome dos três alunos que têm as melhores notas.
     */
    public static void mostrarTop() {
        int[] topAlunos = new int[MAX_ALUNOS];
        topAlunos = notasAlunos;
        String[] nomeAlunos = new String[MAX_ALUNOS];
        nomeAlunos = nomeAlunos;
        for (int w = 0; w <= topAlunos.length - 1; w++) {
            int min = w;
            for ( w = w + 1; w < topAlunos.length; w++) {
                if (topAlunos[w] < topAlunos[min])
                    min = w;
            }
            int temp = topAlunos[min];

            /**topAlunos[min] = topAlunos[w];
            nomeAlunos[min] = nomeAlunos[w];
            topAlunos[w] = temp;*/

        }

        System.out.println("As melhores notas são: ");
        System.out.println("Aluno:" +nomeAlunos[nomeAlunos.length-1] + "nota:" +topAlunos[topAlunos.length-1]);
        System.out.println("Aluno:" +nomeAlunos[nomeAlunos.length-2] + "nota:" +topAlunos[topAlunos.length-2]);
        System.out.println("Aluno:" +nomeAlunos[nomeAlunos.length-3] + "nota:" +topAlunos[topAlunos.length-3]);
        interC.showMsg(" ");

    }
    /**
     * Método a ser implementando no TP1.
     * Apresentar a pauta com nomes dos alunos e á frente cada nome a respectiva nota obtida.
     */
    public static void mostrarPauta() {
        if(nomeAlunos[0]==null){
            System.out.println("Não ha dados");
        }
        else

            for (int w = 0; w <= nomeAlunos.length - 1; w++) {
                if (nomeAlunos[w] != null) {
                    int num = w + 1;
                    System.out.println(num + "Aluno/a :" + nomeAlunos[w] + ", nota:" + notasAlunos[w]);
                }
            }


        }

    /**
     * Método a ser implementando no TP1
     * Apresentar para um aluno específico em que o nome é dado como parâmetro a nota de avaliação
     * @param nome é uma string contendo o nome do aluno que queremos apresentar a sua nota
     * @return
     */
    public static void mostrarDetalhesAluno(String nome) {
        interC.showMsg("A ser implementado ...");

    }

    /**
     * Método a ser implementando no TP1
     * O método deverá pedir um nome e pesquisar o array de nomes. Caso existir ou caso existem nomes
     * parecidos apresentar a lista de nomes. Nomes parecidos são nomes que iniciam com as mesmas duas ou três
     * primeiras letras. Ou apelidos iguais.
     */
    public static void pesquisar(String nome){
        if(nomeAlunos[0]==null){
        System.out.println("Não ha dados");
        }
        else{
        for(int w=0;w<=nomeAlunos.length-1;w++){
        int num=w+1;
        if(nomeAlunos[w]!=null&&nome.contains(nomeAlunos[w])){

            System.out.println(num+"Aluno:"+nomeAlunos[w]+" , nota:"+notasAlunos[w]);
         }
        }

        }
        interC.showMsg(" ");
        }

    /**
     * Método a ser implementando no TP1
     * O método deverá pedir um nome e pesquisar o array de nomes. Caso existir ou caso existem nomes
     * parecidos apresentar a lista de nomes. Nomes parecidos são nomes que iniciam com as mesmas duas ou três
     * primeiras letras. Ou apelidos iguais.
     */
    public static void pesquisar(int nota){
        if(nomeAlunos[0]==null){
        System.out.println("Não ha dados");
        }
        else{
        for(int w=0;w<=notasAlunos.length-1;w++){
        int num=w+1;
        if(nomeAlunos[w]!=null&&nota==notasAlunos[w]){
        System.out.println(num+"aluno/a:"+nomeAlunos[w]+" ,nota"+notasAlunos[w]);
        }
        }
        }
        interC.showMsg(" ");
        }
    private String[] searchByName(String nome) {
        return null;
    }

}
