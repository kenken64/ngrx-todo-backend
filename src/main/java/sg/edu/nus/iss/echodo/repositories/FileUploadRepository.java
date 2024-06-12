package sg.edu.nus.iss.echodo.repositories;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.nus.iss.echodo.model.Todo;

@Repository
public class FileUploadRepository {
    private static final String INSERT_TODO_TBL = "INSERT INTO todo (recorded_audio, title, description, status) VALUES(?, ?, ?, ?)";
    
    private static final String SQL_GET_TODO_BY_POST_ID = 
       "select id, title, description, recorded_audio, status from todo where id=?";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate template;


    public void uploadBlob(MultipartFile file, String title, String description) 
            throws SQLException, IOException  {
        
        try (Connection con = dataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement(INSERT_TODO_TBL)) {
            InputStream is = file.getInputStream();
            pstmt.setBinaryStream(1, is, file.getSize());
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setString(4, "0");
            pstmt.executeUpdate();
        }
    }

    public Optional<Todo> getTodoById(Integer todoId){
        return template.query(
            SQL_GET_TODO_BY_POST_ID,
            (ResultSet rs) -> {
                if(!rs.next())
                    return Optional.empty();
                final Todo td = Todo.populate(rs);
                return Optional.of(td);
            },
            todoId
        );
    }

}