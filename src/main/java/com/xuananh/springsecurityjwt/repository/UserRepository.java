package com.xuananh.springsecurityjwt.repository;
import com.xuananh.springsecurityjwt.config.sql.DataSource;
import com.xuananh.springsecurityjwt.config.sql.SqlGetter;
import com.xuananh.springsecurityjwt.model.Role;
import com.xuananh.springsecurityjwt.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@Data
@Slf4j
@RequiredArgsConstructor
public class UserRepository {
    private final DataSource dataSource;
    private final SqlGetter sqlGetter;

    public Optional<User> findByEmail(String email) {
        String sql = sqlGetter.get("FIND_BY_EMAIL");
        ResultSet rs = null;
        User user = null;
        try (PreparedStatement ps = dataSource.prepareStatement(sql)) {
            ps.setString(1, email);
            rs = ps.executeQuery();
            log.info(ps.toString());
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("EMAIL"));
                user.setFirstname(rs.getString("LASTNAME"));
                user.setFirstname(rs.getString("FIRSTNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setRole(Role.valueOf(rs.getString("ROLE")));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                assert rs != null;
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return Optional.ofNullable(user);
    }

    public boolean save(User user) {
        String sql = sqlGetter.get("INSERT_USER");
        try(PreparedStatement ps = dataSource.prepareStatement(sql)) {
            int idx = 0;
            ps.setString(++idx, user.getEmail());
            ps.setString(++idx, user.getFirstname());
            ps.setString(++idx, user.getLastname());
            ps.setString(++idx, user.getPassword());
            ps.setString(++idx, user.getRole().name());
            log.info(ps.toString());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
