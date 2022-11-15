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
  public int[][] tablaPuntuacio = {
        {3, 4, 5, 7, 7, 5, 4, 3},
        {4, 6, 8,10,10, 8, 6, 4},
        {5, 8,11,13,13,11, 8, 5},
        {7,10,13,16,16,13,10, 7},
        {7,10,13,16,16,13,10, 7},
        {5, 8,11,13,13,11, 8, 5},
        {4, 6, 8,10,10, 8, 6, 4},
        {3, 4, 5, 7, 7, 5, 4, 3}
    };


  public oushiet()
  {
    nom = "oushiet";
  }
  
  @Override
  public int moviment(Tauler t, int color)
  {
    torn=color;
    int col=minimax(t,8);
      System.out.println("cont: "+cont);
      System.out.println("col: "+col);
    return col;
    
  }
  
  @Override
  public String nom()
  {
    return nom;
  }
   public int minimax(Tauler taulell, int profunditat){
        int val=Integer.MIN_VALUE;  
        int columna=4;//SE PUEDE CAMBIAR EL VALORA PARA ESRUDIAR EL COPORTAMIENTO 
        //Definim la funcio inici de la crida miniamx, es a dir, la funció que iniciará
        //la crida.
        //Primerament definim els valors alfa i beta y el numero de columna(useless aquest ultim)
        //val es el valor con el que se incia esta poda , es decir , el que nos subiamos y bajabamos
        //siempre en los ejercicios de minmax
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
                   //comprobamos la poda?¿?¿?¿?¿
                   //RELEEEEEEEEEEEEEEEER!!!!!!!!!!!!!!!
                    val = valSeg;
                    columna = i;
                }     
            }     
        }
       
       return columna;
   }
 
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
                val=Math.min(val,auxVal);
                beta=Math.min(val,beta);
                //si alfa es major a beta llavors podem
                if(alfa>=/*=¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿*/beta)break;
             }
        }
        return val;
        }
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
                l'algoritme max, ja que despres del min va el max jaja
                */
                Tauler noutaulell = new Tauler(taulell);
                noutaulell.afegeix(i, torn);
                /*
                Cridem la crida max per tal de trobar un altre valor de l'heuristica 
                al mateix moment, agafem el numero mes petit i l'actualitzem a la variable
                per mirar si fem la poda alfabeta
                */
                int auxVal=cridaMin(noutaulell,alfa,beta, i,profunditat-1);
                val=Math.max(val,auxVal);
                alfa=Math.max(val,alfa);
                //si alfa es major a beta llavors podem
                if(alfa>/*=¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿*/beta)break;
             }
        }
        return val;
        }

  public int Heuristica(Tauler t){
      ++cont;
      int valorHeur = 0;
      
      
      
        for (int i = 0; i < 8; i++) {
           
            for (int j = 0; j < 8; j++) {
                if (t.getColor(i, j) == torn) {
                    valorHeur += tablaPuntuacio[i][j];
                } else if (t.getColor(i, j) == torn*-1) {
                    valorHeur -= tablaPuntuacio[i][j];
                }
            }
        }
        //System.out.println("heur: "+valorHeur);
        //System.out.println("------------------");
        //pt.pintaTaulerALaConsola();
        //System.out.println("------------------");
        return valorHeur;

  
  }
    public int HeurChorizontales(Tauler t){
        int heur=0;
        for (int i = 0; i < t.getMida()-3; i++) {
            for (int j = 0; j < t.getMida()-3; j++) {
                int NumFitxesUtils=0;
                int WrongFitxaUtil=0;
                int NumFitxesInutils=0;
                int WrongFitxaInutil=0;
                for(int k=0;k<4;++k){
                    //aquest for mira les 4 files seguides, si està 
                    if(t.getColor(i+k, j) == torn && WrongFitxaUtil==0){
                        ++NumFitxesUtils;
                       //si en una mateixa fila,hi han x fitxes meves, anem sumant
                       //per l'heur.
                    }
                    if(t.getColor(i+k, j) == -torn){
                        NumFitxesUtils=0;
                        WrongFitxaUtil=1;
                        //si tenim una fitxa de l'enemic, llavors els parells queden
                        //invalids i ens petem lo demes d'aquest bucle concret.   
                    }
                    if(t.getColor(i+k, j) == -torn && WrongFitxaInutil==0){
                        ++NumFitxesInutils;
                       //si en una mateixa fila,hi han x fitxes meves, anem sumant
                       //per l'heur.
                    }
                    if(t.getColor(i+k, j) == -torn){
                        NumFitxesInutils=0;
                        WrongFitxaInutil=1;
                        //si tenim una fitxa de l'enemic, llavors els parells queden
                        //invalids i ens petem lo demes d'aquest bucle concret.   
                    }
                }
                heur=heur+NumFitxesUtils-NumFitxesInutils;   
            } 
        }  
      return heur;
  }
 
    public int HeurBertuscales(Tauler t){
        int heur=0;
        for (int i = 0; i < t.getMida()-3; i++) {
            for (int j = 0; j < t.getMida()-3; j++) {
                int NumFitxesUtils=0;
                int WrongFitxaUtil=0;
                int NumFitxesInutils=0;
                int WrongFitxaInutil=0;
                for(int k=0;k<4;++k){
                    //aquest for mira les 4 files seguides, si està 
                    if(t.getColor(i, j+k) == torn && WrongFitxaUtil==0){
                        ++NumFitxesUtils;
                       //si en una mateixa fila,hi han x fitxes meves, anem sumant
                       //per l'heur.
                    }
                    if(t.getColor(i, j+k) == -torn){
                        NumFitxesUtils=0;
                        WrongFitxaUtil=1;
                        //si tenim una fitxa de l'enemic, llavors els parells queden
                        //invalids i ens petem lo demes d'aquest bucle concret.   
                    }
                    if(t.getColor(i, j+k) == -torn && WrongFitxaInutil==0){
                        ++NumFitxesInutils;
                       //si en una mateixa fila,hi han x fitxes meves, anem sumant
                       //per l'heur.
                    }
                    if(t.getColor(i, j+k) == -torn){
                        NumFitxesInutils=0;
                        WrongFitxaInutil=1;
                        //si tenim una fitxa de l'enemic, llavors els parells queden
                        //invalids i ens petem lo demes d'aquest bucle concret.   
                    }
                }
                heur=heur+NumFitxesUtils-NumFitxesInutils;   
            } 
        }  
      return heur;
  }
    
    public int HeurDeIzqaDerDiagonal(Tauler t){
        int heur=0;

        return heur;
    }
    public int HeurDeDeraIzqDiagonal(Tauler t){
        int heur=0;
        
        return heur;
    }
    
    
    

}

