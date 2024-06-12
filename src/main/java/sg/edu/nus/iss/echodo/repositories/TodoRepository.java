package sg.edu.nus.iss.echodo.repositories;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.echodo.model.Todo;

@Repository
public class TodoRepository {

    private static final String SELECT_ALL_TODO = "select id, title, description, recorded_audio, status from todo";
    
    @Autowired
    private JdbcTemplate template;

    public List<Todo> getAllTodos() {
        return template.query(
            SELECT_ALL_TODO,
            (ResultSet rs, int rowNum) -> {
                final Todo td = Todo.populate(rs);
                return td;
            }
        );
    }

}
