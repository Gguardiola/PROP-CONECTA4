package edu.epsevg.prop.lab.c4;

/**
 * Jugador aleatori
 * "Alea jacta est"
 * @author Profe
 */
public class oushiet
  implements Jugador, IAuto
{
  private String nom;
  private int torn=1;
  private int cont=0;
  int profmax=8;
    public oushiet(int profmax1)
  {
    profmax=profmax1;
    nom = "oushiet";
  }
  
  @Override
  public int moviment(Tauler t, int color)
  {
    torn=color;
    int col=minimax(t,profmax);
    System.out.println("cont: "+cont);
    cont=0;
    return col;
    
  }
  
  @Override
  public String nom()
  {
    return nom;
  }
  /**
   * Retorna la columna con mejor heuristica en base del algoritmo minimax y la
  *         heuristica.
   * @param taulell taulell amb la partida concreta
   * @param profunditat profunditat maxima de l'arbre
   * @return columna amb millor heuristica
   */
   public int minimax(Tauler taulell, int profunditat){
        int val=Integer.MIN_VALUE;  
        int columna=4;
        //Definim la funcio inici de la crida miniamx, es a dir, la funció que iniciará
        //la crida.
            for (int i = 0; i < taulell.getMida(); ++i){
            //recorremos todo el taulell creando nuevas jugadas
            if(taulell.movpossible(i)){
                //si el moviemiento es posible , entonces creamos esta nueva jugada
                //en la columna i , hacemos nuevo taulell y llamamos a las llamadas especificas
               Tauler noutaulell=new Tauler(taulell);
               noutaulell.afegeix(i,torn);
               //obviamente tendremos que ponerle el siguiente estado
               int valSeg = cridaMin(noutaulell,Integer.MIN_VALUE,Integer.MAX_VALUE,i,profunditat-1);
               //esta llamada es donde pediremos el valor obtenido con la llamada la los hijos siguientes
               if(valSeg > val){
                   //coge el maximo de todos
                    val = valSeg;
                    columna = i;
                    //y pillamos q esa columna es la adecuada
                }     
            }     
        }
       
       return columna;
   }
 /**
  * Retorna el valor que te menor heuristica amb el seu moviment.   
  * @param taulell taulell amb la partida concreta
  * @param alfa nombre alfa per la poda alfa-beta
  * @param beta nombre beta per la poda alfa-beta
  * @param antMov moviment anterior (num de columna)
  * @param profunditat profunditat maxima de l'arbre
  * @return  valor amb l'heuristica major
  */
   public int cridaMin(Tauler taulell,int alfa,int beta, int antMov,int profunditat){
       //Primer de tot comprobem que l'ultima jugada sigui la guanyadora , si es aquest el cas
       //llavors retornem una heuristica infinita jej
        if(taulell.solucio(antMov,torn)){
               return Integer.MAX_VALUE-83647;        
        }
        /*
        Pero si l'enemic te la solucio llavors tallem y enviem una heuristica
        negativa, com la meva nota del parcial de prop *se le cae una lagrima*
        */
        else if(taulell.solucio(antMov,-(torn))){
           return Integer.MIN_VALUE+83647;
        }
        //si ja s'ha acabat la profunditat o el taulell esta ple , llavors cridem a la heuristica
        else if ((profunditat == 0 ) || (!(taulell.espotmoure()))){
            return Heuristica(taulell);
        }
        //Definim els valors val i preparem un bool per poder comprabar despres si podem o no 
        int val=Integer.MAX_VALUE;
        for(int i = 0; i < taulell.getMida(); i++){
            //mentres poguem fer el moviment i no hagi poda...
            if(taulell.movpossible(i)){
                /*Creem un nou taulell amb el seguent posible moviment (ara de l'enemic)
                per tal de poder fer la proxima crida. Ara toca fer la crida a
                l'algoritme max, ja que despres del min va el max jaja
                */
                Tauler noutaulell = new Tauler(taulell);
                noutaulell.afegeix(i, -(torn));
                /*
                Cridem la crida max per tal de trobar un altre valor de l'heuristica 
                al mateix moment, agafem el numero mes petit i l'actualitzem a la variable
                per mirar si fem la poda alfabeta
                */
                int auxVal=cridaMax(noutaulell,alfa,beta, i,profunditat-1);
                val=Math.min(val, auxVal);
                beta=Math.min(val, beta);
                //si alfa es major a beta llavors podem i ens petem tot lo altre
                if(alfa>=beta)break;
             }
        }
        return val;
        }
   /**
    * Retorna el valor que te major heuristica amb el seu moviment.  
    * @param taulell taulell amb la partida concreta
    * @param alfa nombre alfa per la poda alfa-beta
    * @param beta nombre beta per la poda alfa-beta
    * @param antMov moviment anterior(numero de columna)
    * @param profunditat profunditat maxima de l'arbre
    * @return valor amb l'heuristica major
    */
   public int cridaMax(Tauler taulell,int alfa,int beta, int antMov,int profunditat){
       //Primer de tot comprobem que l'ultima jugada sigui la guanyadora , si es aquest el cas
       //llavors retornem una heuristica infinita jej
        if(taulell.solucio(antMov,torn)){
               return Integer.MAX_VALUE-83647;        
        }
        /*
        Pero si l'enemic te la solucio llavors tallem y enviem una heuristica
        negativa, com la meva nota del parcial de prop *se le cae una lagrima*
        */
        else if(taulell.solucio(antMov,-(torn))){
           return Integer.MIN_VALUE+83647;
        }
        //si ja s'ha acabat la profunditat o el taulell esta ple , llavors cridem a la heuristica
        else if ((profunditat == 0 )|| (!(taulell.espotmoure()))){
            return Heuristica(taulell);
        }
        
        //Definim els valors val i preparem un bool per poder comprabar despres si podem o no 
        int val=Integer.MIN_VALUE;
        for(int i = 0; i < taulell.getMida(); i++){
            //mentres poguem fer el moviment i no hagi poda...
            if(taulell.movpossible(i) ){
                /*Creem un nou taulell amb el seguent posible moviment (ara de l'enemic)
                per tal de poder fer la proxima crida. Ara toca fer la crida a
                l'algoritme min, ja que despres del max va el min jaja
                */
                Tauler noutaulell = new Tauler(taulell);
                noutaulell.afegeix(i, torn);
                /*
                Cridem la crida min per tal de trobar un altre valor de l'heuristica 
                al mateix moment, agafem el numero mes gran i l'actualitzem a la variable
                per mirar si fem la poda alfabeta
                */
                int auxVal=cridaMin(noutaulell,alfa,beta, i,profunditat-1);
                val=Math.max(val,auxVal);
                alfa=Math.max(val,alfa);
                //si alfa es major a beta llavors podem i ens petem el bucle
                if(alfa>=beta)break;
             }
        }
        return val;
        }
    /**
     * 
     * @param t  taulell amb la partida concreta
     * @return nombre anomenat heuristica depenent de les fitxes i les combinaciions del taulell
     */
    public int Heuristica(Tauler t){
        ++cont;
        //Sumem totes les heuristiques posibles de les posibles combinacions 
        //que es poden fer.
        int heuristica=HeurChorizontales(t)+ HeurBertuscales(t) + HeurDeIzqaDerDiagonal(t) + HeurDeDeraIzqDiagonal(t);
        return heuristica;

  
  }
    /**
     * 
     * @param t taulell amb la partida concreta
     * @return heuristica de totes les fitxes mirades en horitzontals
     */
    public int HeurChorizontales(Tauler t){
        int heur=0;
        for (int i = 0; i < t.getMida()-3; i++) {
            for (int j = 0; j < t.getMida(); j++) {
                int NumFitxesUtils=0;
                for(int k=0;k<4;++k){
                    //aquest for mira les 4 files seguides, si està 
                    if(t.getColor(i+k, j) == torn ){
                        ++NumFitxesUtils;
                       //si en una mateixa fila,hi han x fitxes meves, anem sumant
                       //per l'heur.
                    }
                    if(t.getColor(i+k, j) == -torn){
                        --NumFitxesUtils;
                        //si tenim una fitxa de l'enemic, llavors anem restant lentament.   
                    }
                }
                heur=heur+NumFitxesUtils;
            } 
        }  
      return heur;
  }
    
    /**
     * 
     * @param t taulell amb la partida concreta
     * @return heuristica de totes les fitxes mirades en vertical
     */
    public int HeurBertuscales(Tauler t){
        //lo mismo pa vertiacles l
        int heur=0;
        for (int i = 0; i < t.getMida(); i++) {
            for (int j = 0; j < t.getMida()-3; j++) {
                int NumFitxesUtils=0;
                for(int k=0;k<4;++k){
                    if(t.getColor(i, j+k) == torn ){
                        ++NumFitxesUtils;
                      
                    }
                    if(t.getColor(i, j+k) == -torn){
                        --NumFitxesUtils;
                          
                    }
                }
                heur=heur+NumFitxesUtils;
            } 
        }  
      return heur;
        }
    /**
     * 
     * @param t taulell amb la partida concreta
     * @return heuristica de totes les fitxes mirades en diagonal
     */
    public int HeurDeIzqaDerDiagonal(Tauler t){
        //ahora para lineas de . a l
        int heur=0;
        for (int i = 0; i < t.getMida()-3; i++) {
            
            for (int j = 0; j < t.getMida()-3; j++) {
                int NumFitxesUtils=0;
                for(int k=0;k<4;++k){
                    if(t.getColor(i+k, j+k) == torn ){
                        ++NumFitxesUtils;
                    }
                    if(t.getColor(i+k, j+k) == -torn){
                        --NumFitxesUtils;
                   
                    }
                }
                heur=heur+NumFitxesUtils;
            } 
        }  
      return heur;
        }
    /**
     * 
     * @param t taulell amb la partida concreta
     * @return heuristica de totes les fitxes mirades en l'altre diagonal
     */
    public int HeurDeDeraIzqDiagonal(Tauler t){
        //ahora de l a .
        int heur=0;

        for (int i = 3; i < t.getMida(); i++) {
                                    

            for (int j = 0; j < t.getMida()-3; j++) {
                int NumFitxesUtils=0;
                for(int k=0;k<4;++k){
                    //aquest for mira les 4 files seguides, si està 
                    if(t.getColor(i-k, j+k) == torn ){
                        ++NumFitxesUtils;
                       //si en una mateixa fila,hi han x fitxes meves, anem sumant
                       //per l'heur.
                    }
                    if(t.getColor(i-k, j+k) == -torn){
                        --NumFitxesUtils;
                        //si tenim una fitxa de l'enemic, llavors els parells queden
                        //invalids i ens petem lo demes d'aquest bucle concret.   
                    }
                }
                heur=heur+NumFitxesUtils;
            } 
        }  
      return heur;
    }

    
    
    

}

