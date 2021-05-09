package repository.jdbc;

import domain.Donatie;
import domain.Voluntar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.DonatieRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class DonatieDbRepo implements DonatieRepo {
    private JdbcUtils jdbcUtils;

    private final static Logger logger= LogManager.getLogger(VoluntarDbRepo.class);

    public DonatieDbRepo(Properties props) {
        jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public List<Donatie> filterByDonator(String donator) {

        logger.traceEntry();
        Connection con=jdbcUtils.getConnection();
        List<Donatie> donatii = new ArrayList<>();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Donatii where nume_donator like ?")) {
            preStmt.setString(1,"%" + donator + "%");
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    int id = result.getInt("id");
                    int id_caz_caritabil = result.getInt("id_caz_caritabil");

                    String nume_donator = result.getString("nume_donator");
                    String numar_telefon = result.getString("numar_telefon");

                    double suma = result.getDouble("suma");

                    Donatie donatie=new Donatie(id, id_caz_caritabil, nume_donator
                            , numar_telefon, suma);


                    donatii.add(donatie);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        return logger.traceExit(donatii);
    }

    @Override
    public List<Donatie> filterByPhone(String telefon) {
        logger.traceEntry();
        Connection con=jdbcUtils.getConnection();
        List<Donatie> donatii = new ArrayList<>();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Donatii where numar_telefon like ?")) {
            preStmt.setString(1,"%" + telefon + "%");
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    int id = result.getInt("id");
                    int id_caz_caritabil = result.getInt("id_caz_caritabil");

                    String nume_donator = result.getString("nume_donator");
                    String numar_telefon = result.getString("numar_telefon");

                    double suma = result.getDouble("suma");

                    Donatie donatie=new Donatie(id, id_caz_caritabil, nume_donator
                            , numar_telefon, suma);


                    donatii.add(donatie);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        return logger.traceExit(donatii);
    }

    @Override
    public List<Donatie> filterByCaritabilID(int ID) {

        logger.traceEntry();
        Connection con=jdbcUtils.getConnection();
        List<Donatie> donatii = new ArrayList<>();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Donatii where id_caz_caritabil = ?")) {
            preStmt.setString(1,Integer.toString(ID));
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    int id = result.getInt("id");

                    String nume_donator = result.getString("nume_donator");
                    String numar_telefon = result.getString("numar_telefon");

                    double suma = result.getDouble("suma");

                    Donatie donatie=new Donatie(id, ID, nume_donator
                            , numar_telefon, suma);


                    donatii.add(donatie);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        return logger.traceExit(donatii);
    }

    @Override
    public Donatie add(Donatie elem) {
        logger.traceEntry("saving request {} ",elem);
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Donatii " +
                "(id_caz_caritabil, nume_donator, numar_telefon, suma)" +
                " values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){

            //preStmt.setString(1,Integer.toString(voluntar_nou.getID()));
            preStmt.setString(1,Integer.toString(elem.getID_caz_caritabil()));
            preStmt.setString(2,elem.getNume_donator());
            preStmt.setString(3,elem.getNumar_telefon());
            preStmt.setString(4,Double.toString(elem.getSuma()));

            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);
            if (result>0){
                //obtinem ID-ul generat de baza de date
                ResultSet rs = preStmt.getGeneratedKeys();
                if (rs.next()) {
                    int id=rs.getInt(1);
                    elem.setID(id);
                    logger.trace("Generated id {} ",id);
                }

            }
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        return logger.traceExit(elem);
    }

    @Override
    public void delete(Donatie elem) {
        logger.traceEntry("saving request {} ",elem);
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Donatii where id = ?", Statement.RETURN_GENERATED_KEYS)){

            preStmt.setString(1,Integer.toString(elem.getID()));


            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);
            if (result != 1){
                System.out.println("ID inexistent.");

            }
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Donatie elem, Integer id) {
        logger.traceEntry("updating request {} ",elem);
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Donatii set id_caz_caritabil=?" +
                ", nume_donator=?, numar_telefon=?, suma=? where id=?")){

            preStmt.setString(1,Integer.toString(elem.getID_caz_caritabil()));
            preStmt.setString(2,elem.getNume_donator());
            preStmt.setString(3,elem.getNumar_telefon());
            preStmt.setString(4,Double.toString(elem.getSuma()));
            preStmt.setString(5,Integer.toString(id));

            int result=preStmt.executeUpdate();
            logger.trace("Updated {} instances",result);
            System.out.println(result);

        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Donatie findById(Integer id) {
        logger.traceEntry();
        Connection con=jdbcUtils.getConnection();
        List<Donatie> donatii = new ArrayList<>();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Donatii where id = ?")) {
            preStmt.setString(1,Integer.toString(id));
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    int id_caz_caritabil = result.getInt("id_caz_caritabil");

                    String nume_donator = result.getString("nume_donator");
                    String numar_telefon = result.getString("numar_telefon");

                    double suma = result.getDouble("suma");

                    Donatie donatie=new Donatie(id, id_caz_caritabil, nume_donator
                            , numar_telefon, suma);


                    donatii.add(donatie);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        return logger.traceExit(donatii.get(0));
    }

    @Override
    public Iterable<Donatie> findAll() {
        return null;
    }

    @Override
    public Collection<Donatie> getAll() {
        logger.traceEntry();
        Connection con=jdbcUtils.getConnection();
        List<Donatie> donatii=new ArrayList<>();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Donatii")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    int id = result.getInt("id");
                    int id_caz_caritabil = result.getInt("id_caz_caritabil");


                    String nume_donator = result.getString("nume_donator");
                    String numar_telefon = result.getString("numar_telefon");

                    double suma = result.getDouble("suma");

                    Donatie donatie=new Donatie(id, id_caz_caritabil, nume_donator
                            , numar_telefon, suma);


                    donatii.add(donatie);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        return logger.traceExit(donatii);
    }
}
