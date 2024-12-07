package com.chathurya.Controller;

import com.chathurya.modal.Chat;
import com.chathurya.modal.Message;
import com.chathurya.modal.User;
import com.chathurya.request.CreateMessageRequest;
import com.chathurya.service.MessageService;
import com.chathurya.service.ProjectService;
import com.chathurya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request)throws Exception{
        User user=userService.findUserById(request.getSenderId());
        Chat chats= projectService.getProjectById(request.getProjectId()).getChat();
        if(chats==null) throw new Exception("Chats not found");
        Message sentMessage= messageService.sendMessage(request.getSenderId(),
                request.getProjectId(), request.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/(projectId)")
    public ResponseEntity<List<Message>> getMessages8yChatId(@PathVariable Long projectId)
            throws Exception {
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}

