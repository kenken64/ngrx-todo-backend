package sg.edu.nus.iss.echodo.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Todo {
    private String complain;
    private String title;
    private byte[] image;
    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    private Integer postId;

    public static Todo populate(ResultSet rs) throws SQLException {
        final Todo post = new Todo();
        post.setPostId(rs.getInt("id"));
        post.setComplain(rs.getString("complain"));
        post.setTitle(rs.getString("title"));
        post.setImage(rs.getBytes("blobc"));
        return post;
    }
}
