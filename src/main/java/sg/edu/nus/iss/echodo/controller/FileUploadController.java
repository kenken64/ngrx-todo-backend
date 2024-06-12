package sg.edu.nus.iss.echodo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.echodo.model.Todo;
import sg.edu.nus.iss.echodo.services.FileUploadService;
import sg.edu.nus.iss.echodo.services.TodoService;


@Controller
@RequestMapping(path="/api/v1")
public class FileUploadController {
    
    @Autowired
    private FileUploadService flSvc;

    @Autowired
    private TodoService todoService;

    private static final String BASE64_PREFIX_DECODER = "data:audio/wav;base64,";
        
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
        System.out.printf("\n desc: %s", description);
        
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

    @GetMapping(path="/todo/{todoId}")
    @CrossOrigin()
    public ResponseEntity<String> getTodo(@PathVariable Integer todoId, Model model){
        Optional<Todo> opt= flSvc.getTodoById(todoId);
        Todo p = opt.get();
        String encodedString = Base64.getEncoder().encodeToString(p.getAudioFile());
        JsonObject payload = Json.createObjectBuilder()
            .add("audio", BASE64_PREFIX_DECODER + encodedString)
            .build();
        return ResponseEntity.ok(payload.toString());
    }

    @GetMapping(path="/todo")
    public ResponseEntity<String> getAllTodos(){
        List<Todo> todos  = this.todoService.getAllTodos();
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        if(todos.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("No todos found");
        }

        todos.forEach(todo -> {
            String encodedString = Base64.getEncoder().encodeToString(todo.getAudioFile());
        
            JsonObject obj = Json.createObjectBuilder()
                .add("id", todo.getTodoId())
                .add("title", todo.getTitle())
                .add("description", todo.getTitle())
                .add("audiofile", BASE64_PREFIX_DECODER + encodedString)
                .add("status", todo.getStatus())
                .build();
            arrBuilder.add(obj);
        });
        JsonArray result = arrBuilder.build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }
}