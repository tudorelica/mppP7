package repository.jdbc;

import domain.Voluntar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.VoluntarRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class VoluntarDbRepo implements VoluntarRepo {
    private JdbcUtils jdbcUtils;

    private final static Logger logger= LogManager.getLogger(VoluntarDbRepo.class);

    public VoluntarDbRepo(Properties props) {
        jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public Voluntar add(Voluntar voluntar_nou) {
        logger.traceEntry("saving request {} ",voluntar_nou);
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Voluntari (nume, username, password) values (?,?,?)", Statement.RETURN_GENERATED_KEYS)){

            //preStmt.setString(1,Integer.toString(voluntar_nou.getID()));
            preStmt.setString(1,voluntar_nou.getNume());
            preStmt.setString(2,voluntar_nou.getUser());
            preStmt.setString(3,voluntar_nou.getPassword());

            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);
            if (result>0){
                //obtinem ID-ul generat de baza de date
                ResultSet rs = preStmt.getGeneratedKeys();
                if (rs.next()) {
                    int id=rs.getInt(1);
                    voluntar_nou.setID(id);
                    logger.trace("Generated id {} ",id);
                }

            }
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        return logger.traceExit(voluntar_nou);
    }

    @Override
    public void delete(Voluntar voluntar_nou) {
        logger.traceEntry("saving request {} ",voluntar_nou);
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Voluntari where id = ?", Statement.RETURN_GENERATED_KEYS)){

            preStmt.setString(1,Integer.toString(voluntar_nou.getID()));


            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);
            if (result!= 1){
                System.out.println("ID inexistent.");

            }
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
    }


    @Override
    public void update(Voluntar elem, Integer id){
        logger.traceEntry("updating request {} ",elem);
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Voluntari set username=?, password=?, nume=? where id=?")){

            preStmt.setString(1,elem.getUser());
            preStmt.setString(2,elem.getPassword());
            preStmt.setString(3,elem.getNume());
            preStmt.setString(4,Integer.toString(id));

            int result=preStmt.executeUpdate();
            logger.trace("Updated {} instances",result);
            System.out.println(result);

        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    /**
     * Aceasi parere ca la functia delete
     * @param username
     * @param password
     * @return contul voluntarului a carui credentiale corespund cu cele de intrare
     */
    @Override
    public Voluntar login(String username, String password) {
        logger.traceEntry();
        Connection con=jdbcUtils.getConnection();
        List<Voluntar> voluntari=new ArrayList<>();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Voluntari")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    Integer id = Integer.parseInt(result.getString("id"));
                    String nume = result.getString("nume");
                    String databaseUsername = result.getString("username");
                    String databasePassword = result.getString("password");

                    Voluntar voluntar=new Voluntar(id, databaseUsername, databasePassword, nume);

                    if (username.equals(databaseUsername) && password.equals(databasePassword))
                        voluntari.add(voluntar);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }

        if (voluntari.isEmpty()) {
            logger.traceExit("Cont Inexistent");
            return null;
        }
        else
            return logger.traceExit(voluntari.get(0));
    }



    /**
     * Lista voluntari va avea doar 1 element deoarece id-urile din baza de date
     * sunt unice.
     * (probabil nu cea mai buna rezolvare deoarece se bazeaza pe integritatea
     * datelor din baza)
    * */
    @Override
    public Voluntar findById(Integer id){
        logger.traceEntry();
        Connection con=jdbcUtils.getConnection();
        List<Voluntar> voluntari=new ArrayList<>();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Voluntari where id = ?")) {
            preStmt.setString(1,Integer.toString(id));
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    String nume = result.getString("nume");
                    String username = result.getString("username");
                    String password = result.getString("password");

                    Voluntar voluntar=new Voluntar(id, username, password, nume);


                    voluntari.add(voluntar);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        return logger.traceExit(voluntari.get(0));
    }

    @Override
    public List<Voluntar> findAll() {
        return null;
    }

    @Override
    public List<Voluntar> getAll() {
        logger.traceEntry();
        Connection con=jdbcUtils.getConnection();
        List<Voluntar> voluntari=new ArrayList<>();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Voluntari")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String nume = result.getString("nume");
                    String username = result.getString("username");
                    String password = result.getString("password");

                    Voluntar voluntar=new Voluntar(id, username, password, nume);


                    voluntari.add(voluntar);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        return logger.traceExit(voluntari);
    }






}