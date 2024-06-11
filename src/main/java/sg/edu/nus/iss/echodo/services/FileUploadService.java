package sg.edu.nus.iss.echodo.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.nus.iss.echodo.model.Todo;
import sg.edu.nus.iss.echodo.repositories.FileUploadRepository;

@Service
public class FileUploadService {
    @Autowired
    private FileUploadRepository flRepo;

    public void uploadBlob(MultipartFile file, String title, String description) 
            throws SQLException, IOException  {
        flRepo.uploadBlob(file, title, description);
    }

    public Optional<Todo> getTodoById(Integer todoId){
        return flRepo.getTodoById(todoId);
    }
}