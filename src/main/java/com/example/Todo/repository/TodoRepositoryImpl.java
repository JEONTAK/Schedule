package com.example.Todo.repository;

import com.example.Todo.Exception.CustomExceptionHandler;
import com.example.Todo.Exception.ErrorCode;
import com.example.Todo.dto.TodoResponseDto;
import com.example.Todo.entity.Todo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class TodoRepositoryImpl implements TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TodoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TodoResponseDto saveTodo(Todo todo) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todo").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("contents", todo.getContents());
        parameters.put("user_id", todo.getUserId());
        parameters.put("password", todo.getPassword());
        parameters.put("create_date", todo.getCreateDate());
        parameters.put("edit_date", todo.getEditDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new TodoResponseDto(
                key.longValue(),
                todo);
    }

    @Override
    public Page<TodoResponseDto> findTodos(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int offset = pageNumber * pageSize;

        List<TodoResponseDto> todos = jdbcTemplate.query(
                "select * from todo t join user u on t.user_id = u.id limit ? offset ?",
                todoResponseDtoRowMapper(), pageSize, offset);
        int total = getTotalCount();

        return new PageImpl<>(todos, pageable, total);
    }

    @Override
    public Page<TodoResponseDto> findTodos(Long userId, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int offset = pageNumber * pageSize;

        List<TodoResponseDto> todos = jdbcTemplate.query(
                "select * from todo t join user u on t.user_id = u.id where user_id = ? limit ? offset ?",
                todoResponseDtoRowMapper(), userId, pageSize, offset);

        int total = getTotalCount();

        return new PageImpl<>(todos, pageable, total);
    }

    private int getTotalCount() {
        return jdbcTemplate.queryForObject("select count(*) from todo", Integer.class);
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
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.TODO_FIND_BAD_REQUEST));
    }

    @Override
    public int updateContents(Long id, String contents, LocalDateTime date) {
        return jdbcTemplate.update("update todo set contents = ?, edit_date = ? where id = ?", contents, date, id);
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
                        rs.getLong("user_id"),
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
                        rs.getLong("user_id"),
                        rs.getString("password"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        rs.getTimestamp("edit_date").toLocalDateTime()
                );
            }
        };
    }
}
