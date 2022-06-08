package repositories;

import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepository {
    private static final String DATABASE_SOURCE = "xy"; //TODO add valid database
    //TODO Nico

    public void save(User user){
        try (
                Connection conn = DriverManager.getConnection(DATABASE_SOURCE);
                PreparedStatement prepareStatement = conn.prepareStatement("insert into users values (?, ?)")
        ) {
            // ID is auto-generated
            prepareStatement.setString(2, user.getName());
            prepareStatement.setString(3, user.getEmail());
            // TODO ggfs location of user? Id einfügen

            conn.setAutoCommit(false);
            prepareStatement.executeBatch();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
