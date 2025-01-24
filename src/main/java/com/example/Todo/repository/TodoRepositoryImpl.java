package com.example.Todo.repository;

import com.example.Todo.dto.TodoResponseDto;
import com.example.Todo.entity.Todo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class TodoRepositoryImpl implements TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TodoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TodoResponseDto saveTodo(Todo todo) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("contents", todo.getContents());
        parameters.put("name", todo.getName());
        parameters.put("password", todo.getPassword());
        parameters.put("create_date", todo.getCreateDate());
        parameters.put("edit_date", todo.getEditDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new TodoResponseDto(
                key.longValue(),
                todo);
    }

    @Override
    public List<TodoResponseDto> findTodos(String name, LocalDateTime editDate) {
        return jdbcTemplate.query("select * from todo where name = ? and edit_date = ? order by edit_date desc",
                todoResponseDtoRowMapper(), name,
                editDate);
    }

    @Override
    public List<TodoResponseDto> findTodos() {
        return jdbcTemplate.query("select * from todo order by edit_date desc", todoResponseDtoRowMapper());
    }

    @Override
    public List<TodoResponseDto> findByName(String name) {
        return jdbcTemplate.query("select * from todo where name = ? order by edit_date desc",
                todoResponseDtoRowMapper(), name);
    }

    @Override
    public List<TodoResponseDto> findByEditDate(LocalDateTime editDate) {
        return jdbcTemplate.query("select * from todo where edit_date = ? order by edit_date desc",
                todoResponseDtoRowMapper(),
                editDate);
    }

    @Override
    public Optional<Todo> findTodoById(Long id) {
        List<Todo> result = jdbcTemplate.query("select * from todo where id = ?", todoRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public TodoResponseDto findTodoByIdOrElseThrow(Long id) {
        List<TodoResponseDto> result = jdbcTemplate.query("select * from todo where id = ?",
                todoResponseDtoRowMapper(), id);
        return result.stream().findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public int updateContents(Long id, String contents, LocalDateTime date) {
        return jdbcTemplate.update("update todo set contents = ?, edit_date = ? where id = ?", contents, date, id);
    }

    @Override
    public int updateName(Long id, String name, LocalDateTime date) {
        return jdbcTemplate.update("update todo set name = ?, edit_date = ? where id = ?", name, date, id);
    }

    @Override
    public int updateTodo(Long id, String contents, String name, LocalDateTime date) {
        return jdbcTemplate.update("update todo set contents = ?, name = ?, edit_date = ? where id = ?", contents,
                name, date, id);
    }

    @Override
    public int deleteTodo(Long id) {
        return jdbcTemplate.update("delete from todo where id = ?", id);
    }

    private RowMapper<TodoResponseDto> todoResponseDtoRowMapper() {
        return new RowMapper<TodoResponseDto>() {
            @Override
            public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TodoResponseDto(
                        rs.getLong("id"),
                        rs.getString("contents"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        rs.getTimestamp("edit_date").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Todo> todoRowMapper() {
        return new RowMapper<Todo>() {
            @Override
            public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Todo(
                        rs.getLong("id"),
                        rs.getString("contents"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        rs.getTimestamp("edit_date").toLocalDateTime()
                );
            }
        };
    }
}
