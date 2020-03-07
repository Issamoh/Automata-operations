package sample;

import graphvizapi.Graphviz;

import java.io.File;
import java.util.*;
public class Automate {
    private HashSet<Lettre> alphabet ;
    private Etat etatInitail ;
    //private HashMap<Etat, ArrayList<Transition>> instructions ; <- ancienne solution
    private HashMap<Etat, HashMap<Etat,HashMap<String, HashSet<Transition>>>> instructions ;
    private HashSet<Etat> etatsFinaux ;
    private HashMap<String,Etat> tousEtats ;
    public Automate(HashSet<Lettre> alphabet, Etat etatInitail, HashMap<Etat, HashMap<Etat, HashMap<String,HashSet<Transition>>>> instructions, HashSet<Etat> etatsFinaux, HashMap<String, Etat> tousEtats) {
        this.alphabet = alphabet;
        this.etatInitail = etatInitail;
        this.instructions = instructions;
        this.etatsFinaux = etatsFinaux;
        this.tousEtats = tousEtats;

    }

    public Automate() {
        instructions = new HashMap<Etat, HashMap<Etat,HashMap<String,HashSet<Transition>>>>();
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
                instructions.put(etat, new HashMap<Etat,HashMap<String,HashSet<Transition>>>());
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
                    instructions.put(etat, new HashMap<Etat,HashMap<String,HashSet<Transition>>>());
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
            HashMap<Etat,HashMap<String,HashSet<Transition>>> tr = instructions.get(e);
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
                            { tr.put(etatdst,new HashMap<String,HashSet<Transition>>()) ;
                            }
                            if(!tr.get(etatdst).containsKey(s)) {tr.get(etatdst).put(s,new HashSet<Transition>());}
                            tr.get(etatdst).get(s).add(new Transition(e, s, etatdst));
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
                                { tr.put(etatdst,new HashMap<String,HashSet<Transition>>()) ;
                                }
                                if(!tr.get(etatdst).containsKey(s)) {tr.get(etatdst).put(s,new HashSet<Transition>());}
                                tr.get(etatdst).get(s).add(new Transition(e, s, etatdst));
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
         HashMap<Etat, HashMap<Etat,HashMap<String,HashSet<Transition>>>> instructionsR = new HashMap<Etat, HashMap<Etat,HashMap<String,HashSet<Transition>>>>() ;
         HashSet<Etat> etatsFinauxR = new HashSet<Etat>() ;
         HashMap<String,Etat> tousEtatsR = new  HashMap<String,Etat>() ;
        for (Etat e: this.instructions.keySet()
             ) {

            if(tousEtats.get(e.getName()).getCoaccessible() && tousEtats.get(e.getName()).getAccessible()) {
                //elimination des transitions vers des états non accessible ou non co accessible
                HashMap<Etat,HashMap<String,HashSet<Transition>>> tr = new HashMap<Etat,HashMap<String,HashSet<Transition>>>() ;
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
    public Automate eliminerLongueTr(){
        // élimination des transitions avec des mots de longueur supérieur de 1
        HashMap<Etat, HashMap<Etat,HashMap<String,HashSet<Transition>>>> instructionsF = (HashMap<Etat, HashMap<Etat,HashMap<String,HashSet<Transition>>>>) instructions.clone() ;
                //new HashMap<Etat, HashMap<Etat,HashMap<String,HashSet<Transition>>>>() ;
        HashSet<Etat> etatsFinauxF = (HashSet<Etat>) etatsFinaux.clone() ;
        HashMap<String,Etat> tousEtatsF = (HashMap<String,Etat>) tousEtats.clone();
        for (Etat e:instructions.keySet() // pour chaque état
        ) {
            for (Etat f:instructions.get(e).keySet() // pour chaque état destinataire
            ) {
                for (String str:instructions.get(e).get(f).keySet() // pour chaque mot de transition
                ) { // si la transition se fait avec un mot w tq |w| > 1 éclater la transition
                    if(str.length()>1){
                        System.out.println("am here");
                    for (Transition tr: instructions.get(e).get(f).get(str)
                    ) {
                        Etat sauv ;
                        int j = str.length()-1 ;
                        Etat etattmp = new Etat(tr.getEtatSrc().getName()+"_"+j);
                        etattmp.setAccessible(tr.getEtatDest().getAccessible());
                        etattmp.setCoaccessible(tr.getEtatDest().getCoaccessible());
                        etattmp.setEstInitial(false);
                        etattmp.setEstFinal(false);
                        tousEtatsF.put(etattmp.getName(),etattmp);
                        instructionsF.put(etattmp,new HashMap<Etat,HashMap<String, HashSet<Transition>>>());
                        instructionsF.get(etattmp).put(tr.getEtatDest(),new HashMap<String,HashSet<Transition>>());
                        instructionsF.get(etattmp).get(tr.getEtatDest()).put(String.valueOf(str.charAt(j)),new HashSet<Transition>());
                        instructionsF.get(etattmp).get(tr.getEtatDest()).get(String.valueOf(str.charAt(j))).add(new Transition(etattmp,String.valueOf(str.charAt(j)),tr.getEtatDest()));
                        sauv =  etattmp;
                        for (int k = (str.length()-2); k > 0 ; k--) {
                            etattmp = new Etat(tr.getEtatSrc().getName()+"_"+k);
                            etattmp.setAccessible(sauv.getAccessible());
                            etattmp.setCoaccessible(sauv.getCoaccessible());
                            etattmp.setEstInitial(false);
                            etattmp.setEstFinal(false);
                            tousEtatsF.put(etattmp.getName(),etattmp);
                            instructionsF.put(etattmp,new HashMap<Etat,HashMap<String, HashSet<Transition>>>());
                            instructionsF.get(etattmp).put(sauv, new HashMap<String, HashSet<Transition>>());
                            instructionsF.get(etattmp).get(sauv).put(String.valueOf(str.charAt(k)),new HashSet<Transition>());
                            instructionsF.get(etattmp).get(sauv).get(String.valueOf(str.charAt(k))).add(new Transition(etattmp,String.valueOf(str.charAt(k)),sauv));
                            sauv =  etattmp;
                            }
                        instructionsF.get(e).put(sauv,new HashMap<String, HashSet<Transition>>());
                        instructionsF.get(e).get(sauv).put(String.valueOf(str.charAt(0)),new HashSet<Transition>());
                        instructionsF.get(e).get(sauv).get(String.valueOf(str.charAt(0))).add(new Transition(e,String.valueOf(str.charAt(0)),sauv)) ;
                        instructionsF.get(e).get(f).get(str).remove(tr);
                        sauv = null ;
                    }

                    }

                    }}
                }
        return new Automate(this.alphabet,this.etatInitail,instructionsF,etatsFinauxF,tousEtatsF);
                }
        public Automate   eliminerSpontane(){
            // élimination des transitions spontanées qui sont représentées avec "."
            // l'dée est de chercher pour chaque état s'il peut transiter vers un autre avec epsilon, si c le cas copier toutes les transitions de ce dernier et recommencer de nouveau car il se peut parmi ces nouvelles transitions il y une avec epsilon.
            HashMap<Etat, HashMap<Etat,HashMap<String,HashSet<Transition>>>> instructionsS = (HashMap<Etat, HashMap<Etat,HashMap<String,HashSet<Transition>>>>) instructions.clone() ;
            //new HashMap<Etat, HashMap<Etat,HashMap<String,HashSet<Transition>>>>() ;
            HashSet<Etat> etatsFinauxS = (HashSet<Etat>) etatsFinaux.clone() ;
            HashMap<String,Etat> tousEtatsS = (HashMap<String,Etat>) tousEtats.clone();
            boolean pasmodif = true;
            do {
                for (Etat e : instructionsS.keySet() // pour chaque état
                ) {
                    pasmodif = true;
                    for (Etat f : instructionsS.get(e).keySet() // pour chaque état destinataire
                    ) {
                        if (instructionsS.get(e).get(f).containsKey(".")) {
                            for (Transition tr : instructionsS.get(e).get(f).get(".")
                            ) {
                                if(f.EstFinal()){if(!e.EstFinal()){etatsFinauxS.add(e);e.setEstFinal(true);}}
                                for (Etat ets : instructionsS.get(f).keySet()
                                ) {
                                    for (String chaine : instructionsS.get(f).get(ets).keySet()
                                    ) {
                                        for (Transition trans : instructionsS.get(f).get(ets).get(chaine)
                                        ) {
                                            if (!instructionsS.get(e).containsKey(trans.getEtatDest())) {
                                                instructionsS.get(e).put(trans.getEtatDest(), new HashMap<String, HashSet<Transition>>());
                                            }
                                            if (!instructionsS.get(e).get(trans.getEtatDest()).containsKey(trans.getLettreTr())) {
                                                instructionsS.get(e).get(trans.getEtatDest()).put(trans.getLettreTr(), new HashSet<Transition>());
                                            }
                                            instructionsS.get(e).get(trans.getEtatDest()).get(trans.getLettreTr()).add(new Transition(e, trans.getLettreTr(), trans.getEtatDest()));
                                        }
                                    }
                                }
                                System.out.println("i am deleting "+tr.getEtatSrc().getName()+" " + tr.getLettreTr() + " -> " + tr.getEtatDest().getName());
                                instructionsS.get(e).get(f).get(".").remove(tr);
                                if (instructionsS.get(e).get(f).get(".").isEmpty())
                                    instructionsS.get(e).get(f).remove(".");
                                pasmodif = false;
                                break;
                            }
                        }
                        if (!pasmodif) break;
                    }

                    if (!pasmodif) break;
                }
            }
            while (!pasmodif);
            return new Automate(this.alphabet, this.etatInitail, instructionsS, etatsFinauxS, tousEtatsS);
        }
        public Automate determiner()
        {

            HashMap<Etat, HashMap<Etat,HashMap<String,HashSet<Transition>>>> instructionsD = new HashMap<Etat, HashMap<Etat,HashMap<String,HashSet<Transition>>>>();
            HashSet<Etat> etatsFinauxD = new HashSet<Etat>()  ;
            HashMap<String,Etat> tousEtatsD = new HashMap<String,Etat>();
            Deque<EtatMultiple> pile = new ArrayDeque<EtatMultiple>();
            TreeSet<Etat> ss = new TreeSet<Etat>() ;
            ss.add(this.etatInitail);
            EtatMultiple etaM = new EtatMultiple(ss) ;
            System.out.println(etaM.getName());
            pile.push(etaM);
            tousEtatsD.put(this.etatInitail.getName(),this.etatInitail);
            instructionsD.put(this.etatInitail,new HashMap<Etat,HashMap<String,HashSet<Transition>>>());
            while (!pile.isEmpty())
            {
                EtatMultiple etatMultiple = pile.peekFirst();
                System.out.println(etatMultiple.getName());
                pile.removeFirst();
                if(instructionsD.get(tousEtatsD.get(etatMultiple.getName())).isEmpty()) // ie cet état est non traité déja
                {
                    for (Lettre l : this.alphabet
                    ) {
                        System.out.println("lettre "+l.getLettre());
                        ss = new TreeSet<Etat>() ;
                        boolean estfinal = false;
                        for (Etat etat : etatMultiple.getComposants()
                        ) {
                            System.out.println(" etat "+etat.getName());
                            for (Etat etatdst : instructions.get(tousEtats.get(etat.getName())).keySet()
                            ) {
                                System.out.println("etat dest "+etatdst.getName());
                                if (instructions.get(tousEtats.get(etat.getName())).get(tousEtats.get(etatdst.getName())).containsKey(String.valueOf(l.getLettre()))) {
                                    System.out.println("am adding "+etatdst.getName());
                                    ss.add(etatdst);
                                    if (etatdst.EstFinal()) {
                                        estfinal = true;
                                    }
                                }
                            }
                        }
                        for (Etat es: ss
                             ) {
                            System.out.println("ss_"+es.getName());
                        }
                        if(!ss.isEmpty()) {
                            etaM = new EtatMultiple(ss);
                            System.out.println("new etatMultiple " + etaM.getName());
                            Etat nouvelEtat = new Etat(etaM.getName());
                            nouvelEtat.setEstFinal(estfinal);
                            if (estfinal) {
                                etatsFinauxD.add(nouvelEtat);
                            }
                            if (!tousEtatsD.containsKey(etaM.getName())) { // l'etat destinataire trouvé  n'existe pas déja

                                tousEtatsD.put(nouvelEtat.getName(), nouvelEtat);
                                instructionsD.put(nouvelEtat, new HashMap<Etat, HashMap<String, HashSet<Transition>>>());
                            }
                            instructionsD.get(tousEtatsD.get(etatMultiple.getName())).put(tousEtatsD.get(nouvelEtat.getName()), new HashMap<String, HashSet<Transition>>());
                            instructionsD.get(tousEtatsD.get(etatMultiple.getName())).get(tousEtatsD.get(nouvelEtat.getName())).put(String.valueOf(l.getLettre()), new HashSet<Transition>());
                            instructionsD.get(tousEtatsD.get(etatMultiple.getName())).get(tousEtatsD.get(nouvelEtat.getName())).get(String.valueOf(l.getLettre())).add(new Transition(tousEtatsD.get(etatMultiple.getName()), String.valueOf(l.getLettre()), nouvelEtat));
                            // empilement de la nouvel état , si elle est empilé déja et mis en attente on empile pas
                            // si elle n'est pas empilé mais déja traité la condition que elle ne contient pas de transitions nous s"assure qu'elle traité
                            if (!pile.contains(etaM)) {
                                pile.push(etaM);
                            }
                        }
                    }
                }

            }


            return new Automate(this.alphabet, this.etatInitail, instructionsD, etatsFinauxD, tousEtatsD);

        }
        public Automate complement(){

            HashMap<String,Etat> tousEtatsC = (HashMap<String,Etat>) tousEtats.clone();
            HashMap<Etat, HashMap<Etat,HashMap<String,HashSet<Transition>>>> instructionsC = (HashMap<Etat, HashMap<Etat,HashMap<String,HashSet<Transition>>>>) instructions.clone() ;
          //  if(etatsFinaux.size()>1) {
                String name = "";
                for (Etat ef : etatsFinaux
                ) {
                    name = name.concat(ef.getName());
                }
                Etat etatInitailC = new Etat(name);
                etatInitailC.setEstInitial(true);
                tousEtatsC.put(name, etatInitailC);
                instructionsC.put(etatInitailC, new HashMap<Etat, HashMap<String, HashSet<Transition>>>());
                for (Etat ef : etatsFinaux
                ) {
                    tousEtatsC.get(ef.getName()).setEstFinal(false);
                    instructionsC.get(etatInitailC).put(ef, new HashMap<String, HashSet<Transition>>());
                    instructionsC.get(etatInitailC).get(ef).put(".", new HashSet<Transition>());
                    instructionsC.get(etatInitailC).get(ef).get(".").add(new Transition(etatInitailC, ".", ef));
                }



                HashSet<Etat> etatsFinauxC = new HashSet<Etat>();
                Etat etatF = tousEtatsC.get(this.etatInitail.getName());
                etatF.setEstFinal(true);
                etatF.setEstInitial(false);
                etatsFinauxC.add(etatF);
                return new Automate(this.alphabet, etatInitailC, instructionsC, etatsFinauxC, tousEtatsC);
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
            for (Etat h: this.etatsFinaux
                 ) {
                gv.addln(h.getName()+" [shape=doublecircle];");
            }
            gv.addln(this.etatInitail.getName()+" [style=filled, fillcolor=green];");
            for (Etat e:instructions.keySet()
                 ) {
                for (Etat f:instructions.get(e).keySet()
                     ) {
                    for (String str:instructions.get(e).get(f).keySet()
                         ) {
                        for (Transition tr: instructions.get(e).get(f).get(str)
                        ) {
                            //if(str.length()==1)
                         //   System.out.println(str);
                            if(str.equals(".")){
                              //  System.out.println(tr.getEtatSrc().getName()+" -> "+tr.getEtatDest().getName()+" [label="+str+"];");
                                gv.addln(tr.getEtatSrc().getName()+" -> "+tr.getEtatDest().getName()+" [label=eps];");
                            }
                            else {
                           // System.out.println(tr.getEtatSrc().getName()+" -> "+tr.getEtatDest().getName()+" [label="+str+"];");
                            gv.addln(tr.getEtatSrc().getName()+" -> "+tr.getEtatDest().getName()+" [label="+str+"];");}
                        //    else {gv.addln(tr.getEtatSrc().getName()+" -> "+tr.getEtatDest().getName()+";");}


                        }
                    }

                }

            }
            gv.addln(gv.end_graph());
            File out = new File("C:/graphViz/"+nomFile+"."+ type);
            gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );

        }
        public class EtatMultiple implements Comparable<EtatMultiple>
        {
            public EtatMultiple(TreeSet<Etat> composants) {
                this.composants = new TreeSet<Etat>() ;
                this.name = new String() ;
                this.composants = (SortedSet<Etat>) composants.clone() ;
                String tmp ="";
                for (Etat e:this.composants
                     ) {
                    tmp = tmp.concat(e.getName());
                    System.out.println("tmp = "+tmp);
                }
                this.name= tmp ;
                System.out.println("name = "+this.name);
            }

            public String getName() {
                return name;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                EtatMultiple that = (EtatMultiple) o;
                return Objects.equals(name, that.name) ;
            }

            @Override
            public int hashCode() {
                return Objects.hash(name);
            }

            public Set<Etat> getComposants() {
                return composants;
            }

            private String name;
            private SortedSet<Etat> composants;

            @Override
            public int compareTo(EtatMultiple o) {
                return this.name.compareTo(o.getName());
            }
        }
}
