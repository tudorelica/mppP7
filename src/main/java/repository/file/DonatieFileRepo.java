package repository.file;

import domain.Donatie;
import domain.Voluntar;
import repository.RepositoryException;
import repository.mock.DonatieMemoryRepo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class DonatieFileRepo extends DonatieMemoryRepo {

    private String filename;

    public DonatieFileRepo(String fileName){
        filename = fileName;
        readFromFile();
    }

    private void readFromFile(){
        try(BufferedReader br=new BufferedReader(new FileReader(filename))){
            String line;

            while((line=br.readLine())!=null){
                String[] elems=line.split(",");

                if (elems.length!=5){
                    System.err.println("Invalid line ..."+line);
                    continue;
                }
                try {
                    int id = Integer.parseInt(elems[0]);
                    int id_caritabil = Integer.parseInt(elems[1]);
                    String nume = elems[2];
                    String numar = elems[3];
                    double suma = Double.parseDouble(elems[4]);



                    Donatie v = new Donatie(id, id_caritabil, nume, numar, suma);
                    super.add(v);


                }catch(NumberFormatException ex){
                    System.err.println("Invalid data "+ex);
                }catch (RepositoryException ex){
                    System.err.println("Repository Error "+ex);
                }
            }
        }catch (IOException ex){
            System.out.println("Error reading "+ex);
            throw new RepositoryException("Error reading "+ex);
        }

    }

    @Override
    public Donatie add(Donatie el) {

        super.add(el);
        writeToFile();
        return el;
    }

    @Override
    public void delete(Donatie el) {
        super.delete(el);
        writeToFile();
    }



    private void writeToFile(){
        try(PrintWriter pw=new PrintWriter(filename)){

            for(Donatie crf:findAll()){
                pw.println(crf.getID()+","+crf.getID_caz_caritabil() +","+crf.getNume_donator()+","+crf.getNumar_telefon()
                + "," + crf.getSuma());
            }
        }catch(IOException ex){
            throw new RepositoryException("Error writing "+ex);
        }

    }
}
