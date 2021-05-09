package repository.jdbc;

import domain.CazCaritabil;
import domain.Donatie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.CaritabilRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class CaritabilDbRepo implements CaritabilRepo {

    private JdbcUtils jdbcUtils;

    private final static Logger logger= LogManager.getLogger(VoluntarDbRepo.class);

    public CaritabilDbRepo(Properties props) {
        jdbcUtils = new JdbcUtils(props);
    }


    @Override
    public List<CazCaritabil> filterByName(String titlu) {

        logger.traceEntry();
        Connection con=jdbcUtils.getConnection();
        List<CazCaritabil> cazuri = new ArrayList<>();

        try(PreparedStatement preStmt=con.prepareStatement("select * from CazuriCaritabile where titlu like ?")) {
            preStmt.setString(1,"%" + titlu + "%");
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String titluComplet = result.getString("titlu");
                    String descriere = result.getString("descriere");

                    CazCaritabil caz = new CazCaritabil(id, titluComplet, descriere);


                    cazuri.add(caz);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        return logger.traceExit(cazuri);
    }

    @Override
    public CazCaritabil add(CazCaritabil elem) {
        logger.traceEntry("saving request {} ",elem);
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into CazuriCaritabile " +
                "(id, titlu, descriere)" +
                " values (?,?,?)", Statement.RETURN_GENERATED_KEYS)){

            //preStmt.setString(1,Integer.toString(voluntar_nou.getID()));
            preStmt.setString(1,Integer.toString(elem.getID()));
            preStmt.setString(2,elem.getTitlu());
            preStmt.setString(3,elem.getDescriere());

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
    public void delete(CazCaritabil elem) {
        logger.traceEntry("saving request {} ",elem);
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from CazuriCaritabile where id = ?", Statement.RETURN_GENERATED_KEYS)){

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
    public void update(CazCaritabil elem, Integer id) {
        logger.traceEntry("updating request {} ",elem);
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update CazuriCaritabile set " +
                "titlu=?, descriere=? where id=?")){

            preStmt.setString(1,elem.getTitlu());
            preStmt.setString(2,elem.getDescriere());
            preStmt.setString(3,Integer.toString(id));

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
    public CazCaritabil findById(Integer id) {
        logger.traceEntry();
        Connection con=jdbcUtils.getConnection();
        List<CazCaritabil> cazuri = new ArrayList<>();

        try(PreparedStatement preStmt=con.prepareStatement("select * from CazuriCaritabile where id = ?")) {
            preStmt.setString(1,Integer.toString(id));
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    String titlu = result.getString("titlu");
                    String descriere = result.getString("descriere");

                    CazCaritabil caz = new CazCaritabil(id, titlu, descriere);

                    cazuri.add(caz);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        return logger.traceExit(cazuri.get(0));
    }

    @Override
    public Iterable<CazCaritabil> findAll() {
        return null;
    }

    @Override
    public Collection<CazCaritabil> getAll() {
        logger.traceEntry();
        Connection con=jdbcUtils.getConnection();
        List<CazCaritabil> cazuriCaritabile = new ArrayList<>();

        try(PreparedStatement preStmt=con.prepareStatement("select * from CazuriCaritabile")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    int id = result.getInt("id");
                    String titlu = result.getString("titlu");
                    String descriere = result.getString("descriere");

                    CazCaritabil caz = new CazCaritabil(id, titlu, descriere);

                    cazuriCaritabile.add(caz);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        return logger.traceExit(cazuriCaritabile);
    }
}
