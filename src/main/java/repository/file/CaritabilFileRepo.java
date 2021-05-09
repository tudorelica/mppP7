package repository.file;

import domain.CazCaritabil;
import domain.Donatie;
import repository.RepositoryException;
import repository.mock.CaritabilMemoryRepo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class CaritabilFileRepo extends CaritabilMemoryRepo {
    private String filename;

    public CaritabilFileRepo(String fileName){
        filename = fileName;
        readFromFile();
    }

    private void readFromFile(){
        try(BufferedReader br=new BufferedReader(new FileReader(filename))){
            String line;

            while((line=br.readLine())!=null){
                String[] elems=line.split(",");

                if (elems.length!=3){
                    System.err.println("Invalid line ..."+line);
                    continue;
                }
                try {
                    int id = Integer.parseInt(elems[0]);
                    String titlu = elems[1];
                    String descriere = elems[2];




                    CazCaritabil v = new CazCaritabil(id, titlu, descriere);
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
    public CazCaritabil add(CazCaritabil el) {

        super.add(el);
        writeToFile();
        return el;
    }

    @Override
    public void delete(CazCaritabil el) {
        super.delete(el);
        writeToFile();
    }



    private void writeToFile() {
        try (PrintWriter pw = new PrintWriter(filename)) {

            for (CazCaritabil crf : findAll()) {
                pw.println(crf.getID() + "," + crf.getTitlu() + "," + crf.getDescriere());
            }
        } catch (IOException ex) {
            throw new RepositoryException("Error writing " + ex);
        }
    }
}
