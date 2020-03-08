package sample;

import graphvizapi.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

/*
Remember: you just need to change the values of two parameters in the code: cfgProp and TEMP_DIR. in Graphviz.java class
which is an API that calls dot.exe
and creat and modify a file named config.properties (modify dot's location)
 */
public class Main  {


    public static void main(String[] args) {
        System.out.println("*********       Ecole nationale supérieure d'informatique         *********");
        System.out.println("*************       TP THP 2019-2020                          *************");
        System.out.println("**** Enoncé : Programmer différentes opéations sur les automates       ****");
        System.out.println("**** Réalisé par : Ben messaoud Mohammed Issam Daoud        G08|SB     ****");
        System.out.println("***************************************************************************");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("1- Construction de l'automate ....");
        Automate automate = new Automate();
       // automate.afficherAutomate();
        automate.dessinerAutomate("1 automate","pdf");
        System.out.println("2- Réduction de l'automate ....");
        Automate automateR =  automate.reduireAutomate();
      //  automateR.afficherAutomate();
        automateR.dessinerAutomate("2 automate réduit","pdf");
        System.out.println("3- Construction de l'automate partiellement généralisé   ....");
        Automate automateRT = automateR.eliminerLongueTr();
        automateRT.dessinerAutomate("3 automate réduit partiellement généralisé","pdf");
        System.out.println("4- Construction de l'automate simple   ....");
        Automate automateRTS = automateRT.eliminerSpontane();
        automateRTS.dessinerAutomate("4 automate réduit simple","pdf");
        //automate.dessinerAutomate("0 automate 2","pdf");
        System.out.println("5- Déterminisation de l'automate simple   ....");
        Automate automateRTSD = automateRTS.determiner();
        automateRTSD.dessinerAutomate("5 automate réduit simple déterministe","pdf");
        System.out.println("6- Rendre l'automate complet   ....");
        automateRTSD.rendreComplet();
        automateRTSD.dessinerAutomate("6 automate réduit simple déterministe complet","pdf");
        System.out.println("7- Lecture des mots ....");
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez donner un mot ( = pour s'arrêter) :");
        String m = sc.next();
        while (!m.equals("=")) {
            System.out.println(automateRTSD.readWord(m));
            System.out.println("Veuillez donner un mot ( = pour s'arrêter) :");
            m = sc.next();
        }
        System.out.println("8- Construction de l'automate du complement   ....");
         Automate automateC = automateRTSD.complement();
        //automateC.afficherAutomate();
        automateC.dessinerAutomate("7 automate complement","pdf");
        System.out.println("9- Construction de l'automate du miroire  ....");
        Automate automateM = automateRT.miroire();
        //  automateM.afficherAutomate();
        automateM.dessinerAutomate("8 automate miroire","pdf");

/*
        automate.dessinerAutomate("automate 2","pdf");
        Automate automateD = automate.determiner();
        automateD.afficherAutomate();
        automateD.dessinerAutomate("automate deterministe","pdf");
        Automate automateR =  automate.reduireAutomate();
        automateR.afficherAutomate();
        automateR.dessinerAutomate("automate réduit","pdf");
        Automate automateF = automate.eliminerLongueTr();
       automateF.afficherAutomate();
       automateF.dessinerAutomate("automate fermuture","pdf");
       Automate automateS = automate.eliminerSpontane();
       automateS.dessinerAutomate("automate spontane","pdf");

        /*

        Graphviz gv = new Graphviz();                           //Graphviz Object.
        /* generation de graph c bn -> generation de la dot format */
        //String dotFormat = graph.genDotStringByGraph();
       // String dotFormat="1->2;1->3;1->4;4->5;4->6;6->7;5->7;3->8;3->6;8->7;2->8;2->5;";
     /*   gv.addln(gv.start_graph());
       // gv.add(dotFormat);
        gv.addln("S0 -> S1");
        gv.addln("S0 -> S2");gv.addln("S0 -> S3");gv.addln("S1 -> S2");gv.addln("S2 -> S3");
        gv.addln("S3 -> S3");
        gv.addln(gv.end_graph());
        // String type = "gif";
        String type = "pdf";
         gv.increaseDpi();
        File out = new File("C:/graphViz/result"+"."+ type);
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );*/

    }

}
