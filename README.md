# Automata-operations
this is a java program written to autotomize different operations on Automota as Construction, Accessibility, CoAccessibility, Complement, Mirror, Readable words , Graphs are represented using GraphViz API 
<h2>Instructions</h2>
In order to use GraphViz correctly you should download the executable Packages of GraphViz program from the official site (https://graphviz.org/download/) then : <br>
<ul>
<li>Create a folder that will contain created temporary files and <b>results as graphs</b>.</li>
<li>Go to "src\graphvizapi\Graphviz.java"</li>
<li>Search for the variable TEMP_DIR and assign for it the path of the folder created in the first step</li>
<li>Search for the variable DOT and assign for it the path of the dot program so it can be called externally</li>
</ul>
 
    
    private static String TEMP_DIR = "C:/graphViz";

    /**
     * Where is your dot program located? It will be called externally.
     */
    private static String DOT = "C:/Program Files (x86)/graphviz-2.38/release/bin/dot.exe";
  
