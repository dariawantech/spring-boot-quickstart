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
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert jdbcInsertUser;

    private static final String SQL_UPDATE
            = "update d_user set username=:username, password=:password, "
            + "firstname=:firstname, lastname=:lastname, enabled=:enabled where id = :id";
    private static final String SQL_DELETE = "delete from d_user where id = ?";
    private static final String SQL_FIND_BY_ID = "select * from d_user where id = ?";
    private static final String SQL_FIND_BY_USERNAME = "select * from d_user where username = ?";
    private static final String SQL_FIND_ALL = "select * from d_user limit ? offset ?";
    private static final String SQL_COUNT_ALL = "select count(*) from d_user";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("d_user")
                .usingGeneratedKeyColumns("id");
    }

    // User findByUsername(String username);
    public void save(User u) {
        if (u.getId() == null) {
            // create
            SqlParameterSource produkParameter = new BeanPropertySqlParameterSource(u);
            Number newId = jdbcInsertUser.executeAndReturnKey(produkParameter);
            u.setId(newId.intValue());
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
                new ResultSetToUser(), rowPerPage, getRowStart(pageNumber, rowPerPage));
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
