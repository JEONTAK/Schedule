package com.example.Todo.repository;

import com.example.Todo.dto.UserResponseDto;
import com.example.Todo.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserResponseDto saveUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", user.getName());
        parameters.put("email", user.getEmail());
        parameters.put("gender", user.getGender());
        parameters.put("create_date", user.getCreateDate());
        parameters.put("edit_date", user.getEditDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new UserResponseDto(
                key.longValue(),
                user);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        List<User> result = jdbcTemplate.query("select * from user where id = ?", userRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public String findUserNameById(Long id) {
        List<User> result = jdbcTemplate.query("select * from user where id = ?", userRowMapper(), id);
        return result.get(0).getName();
    }

    @Override
    public int updateUser(Long id, String name, String email, LocalDateTime date) {
        return jdbcTemplate.update("update user set name = ?, email = ?, edit_date = ? where id = ?", name, email, date,
                id);
    }

    @Override
    public int updateUserName(Long id, String name, LocalDateTime date) {
        return jdbcTemplate.update("update user set name = ?, edit_date = ? where id = ?", name, date, id);
    }

    @Override
    public int updateUserEmail(Long id, String email, LocalDateTime date) {
        return jdbcTemplate.update("update user set email = ?, edit_date = ? where id = ?", email, date, id);
    }

    @Override
    public int deleteUser(Long id) {
        return jdbcTemplate.update("delete from user where id = ?", id);
    }

    private RowMapper<User> userRowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("gender"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        rs.getTimestamp("edit_date").toLocalDateTime()
                );
            }
        };
    }
}
