package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Automate {
    private HashSet<Lettre> alphabet ;
    private Etat etatInitail ;
    private HashMap<Etat, ArrayList<Transition>> instructions ;
    private HashSet<Etat> etatsFinaux ;
    private HashMap<String,Etat> tousEtats ;

    public Automate() {
        instructions = new HashMap<Etat, ArrayList<Transition>>();
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
                instructions.put(etat, new ArrayList<Transition>());
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
                    instructions.put(etat, new ArrayList<Transition>());
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
            ArrayList<Transition> tr = instructions.get(e);
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
                            tr.add(new Transition(e, s, etatdst));
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
                                tr.add(new Transition(e, s, etatdst));
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
        etat.setAccessible(true);
        for (Transition t: this.instructions.get(etat)
        ) {
            if((t.getEtatDest().getAccessible()==false) && (t.getEtatDest() != etat ))
            { defineEtatAccessible(t.getEtatDest());}

        }
    }
    public void reduireAutomate(){
        this.etatInitail.setAccessible(true);
        for (Transition t: this.instructions.get(etatInitail)
             ) {
            defineEtatAccessible(t.getEtatDest());

        }

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
                System.out.println(e.getName()+" accessible : "+e.getAccessible());
            }
        }
}
