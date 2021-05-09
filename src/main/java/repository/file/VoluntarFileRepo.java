package repository.file;

import domain.Voluntar;
import repository.RepositoryException;
import repository.mock.VoluntarMemoryRepo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class VoluntarFileRepo extends VoluntarMemoryRepo {
    private String filename;

    public VoluntarFileRepo(String fileName){
        filename = fileName;
        readFromFile();
    }

    private void readFromFile(){
        try(BufferedReader br=new BufferedReader(new FileReader(filename))){
            String line;

            while((line=br.readLine())!=null){
                String[] elems=line.split(",");

                if (elems.length!=4){
                    System.err.println("Invalid line ..."+line);
                    continue;
                }
                try {
                    int id = Integer.parseInt(elems[0]);
                    String user = elems[1];
                    String password = elems[2];
                    String nume = elems[3];

                    Voluntar v = new Voluntar(id, user, password, nume);
                    System.out.println(v.getNume());
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
    public Voluntar add(Voluntar el) {

        super.add(el);
        writeToFile();
        return el;
    }

    @Override
    public void delete(Voluntar el) {
        super.delete(el);
        writeToFile();
    }



    private void writeToFile(){
        try(PrintWriter pw=new PrintWriter(filename)){

            for(Voluntar crf:findAll()){
                pw.println(crf.getID()+","+crf.getUser() +","+crf.getPassword()+","+crf.getNume());
            }
        }catch(IOException ex){
            throw new RepositoryException("Error writing "+ex);
        }

    }

}
