package sample;

import graphvizapi.*;

import java.io.File;
import java.io.FileOutputStream;

/*
Remember: you just need to change the values of two parameters in the code: cfgProp and TEMP_DIR. in Graphviz.java class
which is an API that calls dot.exe
and creat and modify a file named config.properties (modify dot's location)
 */
public class Main  {


    public static void main(String[] args) {

        Automate automate = new Automate();
        automate.afficherAutomate();
        Automate automateR =  automate.reduireAutomate();
        automateR.afficherAutomate();
        automate.dessinerAutomate("automate","pdf");
        automateR.dessinerAutomate("automate réduit","pdf");
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
