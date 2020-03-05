package sample;

import graphvizapi.Graphviz;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Automate {
    private HashSet<Lettre> alphabet ;
    private Etat etatInitail ;
    //private HashMap<Etat, ArrayList<Transition>> instructions ; <- ancienne solution
    private HashMap<Etat, HashMap<Etat,ArrayList<Transition>>> instructions ;
    private HashSet<Etat> etatsFinaux ;
    private HashMap<String,Etat> tousEtats ;

    public Automate(HashSet<Lettre> alphabet, Etat etatInitail, HashMap<Etat, HashMap<Etat, ArrayList<Transition>>> instructions, HashSet<Etat> etatsFinaux, HashMap<String, Etat> tousEtats) {
        this.alphabet = alphabet;
        this.etatInitail = etatInitail;
        this.instructions = instructions;
        this.etatsFinaux = etatsFinaux;
        this.tousEtats = tousEtats;
    }

    public Automate() {
        instructions = new HashMap<Etat, HashMap<Etat,ArrayList<Transition>>>();
        alphabet = new HashSet<Lettre>();
        etatsFinaux = new HashSet<Etat>();
        tousEtats = new  HashMap<String,Etat>();
        Boolean etatInitailDonne = false;
        // lecture d'alphabet :****************************************************************************************

        int i = 1;
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez donner l'alphabet de votre automate lettre par lettre ( . pour s'arrêter) :");
        char m = sc.next().charAt(0);
        while (m != '.') {
            alphabet.add(new Lettre(m));
            i++;
            System.out.println(i + " éme lettre : ");
            m = sc.next().charAt(0);
        }
        i = 0;
        // lecture des états : ****************************************************************************************

        System.out.println("Veuillez donner les noms des états de votre automate un par un ( . pour s'arrêter) :");
        String s = sc.next();
        while (!s.equals(".")) {
            Etat etat = new Etat(s);
            if (etatInitailDonne) {
                System.out.println("est ce que c'est un état final ? (Y/N)");
                m = sc.next().charAt(0);
                if (m == 'Y') {
                    etat.setEstFinal(true);
                    etatsFinaux.add(etat);
                }
                tousEtats.put(etat.getName(),etat);
                instructions.put(etat, new HashMap<Etat,ArrayList<Transition>>());
            } else if (!etatInitailDonne) {
                System.out.println("est ce que c'est un état initial ? (Y/N)");
                m = sc.next().charAt(0);
                if (m == 'Y') {
                    etat.setEstInitial(true);
                    System.out.println("est ce que c'est un état final ? (Y/N)");
                    char n = sc.next().charAt(0);
                    if (n == 'Y') {
                        etat.setEstFinal(true);
                        etatsFinaux.add(etat);
                    }
                    tousEtats.put(etat.getName(),etat);
                    this.etatInitail = etat;
                    instructions.put(etat, new HashMap<Etat,ArrayList<Transition>>());
                    etatInitailDonne = true ;
                }
            }
            i++;
            System.out.println(i+" éme état : ");
            s = sc.next();
        }
        for (String e: tousEtats.keySet()
        ) {
            System.out.println(e);
        }
        for (Etat e: instructions.keySet()
        ) {
            System.out.println(e.getName()+" accessible : "+e.getAccessible());
        }
        // lecture des différentes transitions :*******************************************************************
        int j;
        String nomDst ;
        Etat etatdst ;
        boolean nomDestination = false;
        boolean stop = false;
        for (Etat e: instructions.keySet()
        ) {
            HashMap<Etat,ArrayList<Transition>> tr = instructions.get(e);
            System.out.println("Transitions à partir l'état "+e.getName());
            System.out.println("lettre ( . pour l'epsilon , = pour s'arrêter ) : ");
            s= sc.next();
            while (!s.equals("=")){
                if(s.equals(".")) {
                    System.out.println("Nom de l'état destination ?");
                    nomDestination = false;
                    while (!nomDestination) {
                        nomDst = sc.next();
                        if (tousEtats.containsKey(nomDst)) {
                            etatdst = tousEtats.get(nomDst);
                            if (!tr.containsKey(etatdst))
                            { tr.put(etatdst,new ArrayList<Transition>()) ;
                            }
                            tr.get(etatdst).add(new Transition(e, s, etatdst));
                            nomDestination = true;
                            System.out.println("prochaine transition ( = pour s'arrêter ) : ");
                        } else {
                            System.out.println("cette état n'existe pas ! Veuillez corriger :");
                        }
                    }
                }
                else { // test de conformité du mot avec l'alphabet :
                    j = 0;
                    stop = false ;
                    while (j < s.length() && !stop) {
                        System.out.println(s.charAt(j));
                        if (!this.alphabet.contains(new Lettre(s.charAt(j))))
                        {
                            System.out.println("la lettre "+s.charAt(j)+" n'appartient pas à l'aphabet ! Veuillez corriger");
                            stop = true ;
                        }
                        j++;
                    }
                    if(!stop)
                    {
                        nomDestination = false;
                        System.out.println("Nom de l'état destination ?");
                        while (!nomDestination) {
                            nomDst = sc.next();
                            if (tousEtats.containsKey(nomDst)) {
                                etatdst = tousEtats.get(nomDst);
                                if (!tr.containsKey(etatdst))
                                { tr.put(etatdst,new ArrayList<Transition>()) ;
                                }
                                tr.get(etatdst).add(new Transition(e, s, etatdst));
                                nomDestination = true;
                            } else {
                                System.out.println("cette état n'existe pas ! Veuillez corriger");
                            }
                        }
                        System.out.println("prochaine transition ( = pour s'arrêter ) : ");
                    }

                }
                s= sc.next();
            }
        }
        }

    public void defineEtatAccessible(Etat etat) // une méthode récursive qui défint tous les successeurs d'un état accessible comme accessibles s'ils ne sont pas déja visités
    {
        tousEtats.get(etat.getName()).setAccessible(true);
        etat.setAccessible(true);
        for (Etat t: this.instructions.get(etat).keySet()
        ) {
            if((t.getAccessible()==false) && (t!= etat ))
            { defineEtatAccessible(t);}

        }
    }
    public void defineEtatCoaccessible(Etat etat) // une méthode récursive qui défint tous les prédeccesseurs d'un état coaccessible comme coaccessibles s'ils ne sont pas déja visités et ce n'est pas le meme état
    {
        tousEtats.get(etat.getName()).setCoaccessible(true);
        etat.setCoaccessible(true);
        for (Etat t: this.instructions.keySet()
        ) {
            if((t.getCoaccessible()==false) && (t!= etat ))
            { if(this.instructions.get(t).containsKey(etat)) // si dans cet état il y une transition vers notre état qui est co-accessible
                defineEtatCoaccessible(t);}

        }
    }
    public Automate reduireAutomate(){
        // marquage des états accessible : en allant de  l'état initial vers tous les successeurs possibles
        this.etatInitail.setAccessible(true);
        for (Etat t: this.instructions.get(etatInitail).keySet()
             ) {
            defineEtatAccessible(t);

        }
        // marquage des états co-accessible : en allant des états finaux vers tous les prédécesseurs possibles
        for (Etat ef :this.etatsFinaux
             ) {

            defineEtatCoaccessible(ef);
        }
         HashMap<Etat, HashMap<Etat,ArrayList<Transition>>> instructionsR = new HashMap<Etat, HashMap<Etat,ArrayList<Transition>>>() ;
         HashSet<Etat> etatsFinauxR = new HashSet<Etat>() ;
         HashMap<String,Etat> tousEtatsR = new  HashMap<String,Etat>() ;
        for (Etat e: this.instructions.keySet()
             ) {

            if(tousEtats.get(e.getName()).getCoaccessible() && tousEtats.get(e.getName()).getAccessible()) {
                //elimination des transitions vers des états non accessible ou non co accessible
                HashMap<Etat,ArrayList<Transition>> tr = new HashMap<Etat,ArrayList<Transition>>() ;
                for (Etat c: instructions.get(e).keySet()
                     ) {
                    if(tousEtats.get(c.getName()).getCoaccessible() && tousEtats.get(c.getName()).getAccessible()){
                        tr.put(c,instructions.get(e).get(c));
                    }
                }
                instructionsR.put(e,tr);
            tousEtatsR.put(e.getName(),e);
            if(e.EstFinal()){etatsFinauxR.add(e);}
            }

        }
        return new Automate(this.alphabet,this.etatInitail,instructionsR,etatsFinauxR,tousEtatsR);

    }
        public void afficherAutomate()
        {
            System.out.println("alpha :");
            for (Lettre l: alphabet
                 ) {
                System.out.println("->"+l.getLettre());

            }
            System.out.println("etat init "+etatInitail.getName());
            System.out.println("etats finaux ");
            for (Etat e: etatsFinaux
                 ) {
                System.out.println("etat fin "+e.getName());
            }
            System.out.println("all");
            for (String s: tousEtats.keySet()
                 ) {
                System.out.println(s);
            }
            for (Etat e: instructions.keySet()
            ) {
                System.out.println(e.getName()+" accessible : "+e.getAccessible()+" coaccessible : "+e.getCoaccessible());
            }
        }
        public void dessinerAutomate(String nomFile, String type){
            Graphviz gv = new Graphviz();
            gv.addln(gv.start_graph());

            for (Etat e:instructions.keySet()
                 ) {
                for (Etat f:instructions.get(e).keySet()
                     ) {
                    for (Transition tr: instructions.get(e).get(f)
                         ) {
                        gv.addln(tr.getEtatSrc().getName()+" -> "+tr.getEtatDest().getName());
                    }
                }

            }
            gv.addln(gv.end_graph());
            File out = new File("C:/graphViz/"+nomFile+"."+ type);
            gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );

        }
}
