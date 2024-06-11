package sg.edu.nus.iss.echodo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.echodo.model.Todo;
import sg.edu.nus.iss.echodo.services.FileUploadService;


@Controller
@RequestMapping(path="/api/v1")
public class FileUploadController {
    
    @Autowired
    private FileUploadService flSvc;
    private static final String BASE64_PREFIX_DECODER = "data:image/png;base64,";
        
    @PostMapping(path="/upload", 
            consumes=MediaType.MULTIPART_FORM_DATA_VALUE, 
            produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> uploadForAngular(
        @RequestPart MultipartFile audioFile,
        @RequestPart String title,
        @RequestPart String description
    ) throws SQLException{
        
        System.out.printf("title: %s", title);
        System.out.printf("complain: %s", description);
        
        try {
            
            flSvc.uploadBlob(audioFile, title, description);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonObject payload = Json.createObjectBuilder()
            .add("status", "success")
            .build();

        return ResponseEntity.ok(payload.toString());
    }

    @GetMapping(path="/todo/{postId}")
    @CrossOrigin()
    public ResponseEntity<String> getTodo(@PathVariable Integer todoId, Model model){
        Optional<Todo> opt= flSvc.getTodoById(todoId);
        Todo p = opt.get();
        String encodedString = Base64.getEncoder().encodeToString(p.getImage());
        JsonObject payload = Json.createObjectBuilder()
            .add("image", BASE64_PREFIX_DECODER + encodedString)
            .build();
        return ResponseEntity.ok(payload.toString());
    }
}