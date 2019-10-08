package com.dariawan.meetdash.dao;

import com.dariawan.meetdash.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SQL_INSERT
            = "insert into user (username, password, firstname, lastname, email, enabled) "
            + "values (:username, :password, :firstName, :lastName, :email, :enabled)";
    private static final String SQL_UPDATE
            = "update user set username=:username, password=:password, "
            + "firstname=:firstname, lastname=:lastname, enabled=:enabled where id = :id";
    private static final String SQL_DELETE = "delete from user where id = ?";
    private static final String SQL_FIND_BY_ID = "select * from user where id = ?";
    private static final String SQL_FIND_BY_USERNAME = "select * from user where username = ?";
    private static final String SQL_FIND_ALL = "select * from user limit ?, ?";
    private static final String SQL_COUNT_ALL = "select count(*) from user";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    // User findByUsername(String username);
    public void save(User u) {
        if (u.getId() == null) {
            // create
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(u);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(SQL_INSERT, namedParameters, keyHolder);
            u.setId(keyHolder.getKey().intValue());
        } else {
            // update
            SqlParameterSource userParameter = new MapSqlParameterSource()
                    .addValue("id", u.getId())
                    .addValue("username", u.getUsername())
                    .addValue("password", u.getPassword())
                    .addValue("firstname", u.getFirstName())
                    .addValue("lastname", u.getLastName())
                    .addValue("email", u.getEmail())
                    .addValue("enabled", u.isEnabled());
            namedParameterJdbcTemplate.update(SQL_UPDATE, userParameter);
        }
    }

    public void delete(User u) {
        jdbcTemplate.update(SQL_DELETE, u.getId());
    }

    public User findById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID,
                    new ResultSetToUser(), id);
        } catch (EmptyResultDataAccessException err) {
            return null;
        }
    }

    public User findByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_USERNAME,
                    new ResultSetToUser(), username);
        } catch (EmptyResultDataAccessException err) {
            return null;
        }
    }

    public List<User> findAll(int pageNumber, int rowPerPage) {
        return jdbcTemplate.query(SQL_FIND_ALL,
                new ResultSetToUser(), getRowStart(pageNumber, rowPerPage),
                rowPerPage);
    }

    public Long count() {
        return jdbcTemplate.queryForObject(SQL_COUNT_ALL, Long.class);
    }
    
    class ResultSetToUser implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User u = new User();
            u.setId((Integer) rs.getObject("id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setFirstName(rs.getString("firstname"));
            u.setLastName(rs.getString("lastName"));
            u.setEmail(rs.getString("email"));
            u.setEnabled(rs.getBoolean("enabled"));
            return u;
        }
    }
}
