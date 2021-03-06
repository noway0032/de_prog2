package piac;

import botanika.Gyumolcs;
import botanika.Noveny;
import botanika.Novenytar;
import botanika.Zoldseg;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class Zoldseges implements Novenytar {
    
    private List<Noveny> novenyek;
    private String zoldsegesCim;

    public Zoldseges(Noveny[] novenyek, String zoldsegesCim) {
        List<Noveny> lista = new ArrayList<>();
        for (Noveny noveny : Arrays.asList(novenyek)) {
                lista.add(noveny);
        }
        this.novenyek = lista;
        this.zoldsegesCim = zoldsegesCim;
    }

    @Override
    public String toString() {
        return zoldsegesCim + "\n\n" + termekList();
    }
    
    private String termekList() {
        Collections.sort(novenyek);
        StringBuilder sb = new StringBuilder();
        for (Noveny noveny : novenyek){
            sb.append(noveny + "\n");
        }
        return sb.toString();
    }

    @Override
    public List<Noveny> adottSzinuNovenyek(String szin) {
        List<Noveny> lista = new ArrayList<>();
        for (Noveny noveny : novenyek) {
            if(Arrays.asList(noveny.getSzin()).contains(szin))
                lista.add(noveny);
        }
        Collections.sort(lista);
        return lista;
    }

    @Override
    public List<Noveny> adottSzinuNovenyek(String szin, boolean zoldseg) {
        List<Noveny> lista = new ArrayList<>();
        for (Noveny noveny : novenyek) {
            if (zoldseg){
                if(Arrays.asList(noveny.getSzin()).contains(szin) &&
                        noveny instanceof Zoldseg)
                    lista.add(noveny);
            } else {
                if(Arrays.asList(noveny.getSzin()).contains(szin) &&
                        noveny instanceof Gyumolcs)
                    lista.add(noveny);
            }
        }
        Collections.sort(lista);
        return lista;
    }
    
    Comparator<Noveny> rendez2 = new Comparator<Noveny>(){
      public int compare(Noveny v1, Noveny v2){
        if(v1.isEheto() != v2.isEheto()){
            if(v2.isEheto()){
                return -1;
            } else {
                return +1;
            }
        }
        return v1.getMegnevezes().compareTo(v2.getMegnevezes());
      }
    };
    
    @Override
    public void kiir(String fajlnev) {
        Collections.sort(novenyek, rendez2);
        try (PrintStream ps = new PrintStream(fajlnev)){
            for(Noveny noveny : novenyek)
                ps.println( noveny );
        }
        catch (FileNotFoundException ex) {
            System.err.println("Nem sikerult megnyitni az allomanyt!");
        }
    }
    
    public boolean nagybetusitKb(char kezdobetu){
        boolean voltModositas = false;
        for (Noveny noveny : novenyek) {
            if (noveny.getMegnevezes().charAt(0) == kezdobetu) {
                noveny.setMegnevezes(noveny.getMegnevezes().toUpperCase());
                voltModositas = true;
            }
        }
        return voltModositas;
    } 
    public int zoldsegSzam(boolean ertek) {
        int szam = 0;
        for (Noveny noveny : novenyek) {
            if (ertek){
                if(noveny instanceof Zoldseg)
                    szam++;
            } else {
                if(noveny instanceof Gyumolcs)
                    szam++;
            }
        }
        return szam;
    }
}
