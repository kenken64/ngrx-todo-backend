package sg.edu.nus.iss.echodo.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Todo {
    private Integer todoId;
    private String description;
    private String title;
    private String status;
    private byte[] audioFile;

    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    
    public Integer getTodoId() {
        return todoId;
    }

    public void setTodoId(Integer todoId) {
        this.todoId = todoId;
    }

    public byte[] getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(byte[] audioFile) {
        this.audioFile = audioFile;
    }

    public static Todo populate(ResultSet rs) throws SQLException {
        final Todo td = new Todo();
        td.setTodoId(rs.getInt("id"));
        td.setTitle(rs.getString("title"));
        td.setDescription(rs.getString("description"));
        td.setAudioFile(rs.getBytes("recorded_audio"));
        td.setStatus(rs.getString("status"));
        return td;
    }

    

    
}
