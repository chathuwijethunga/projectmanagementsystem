package com.chathurya.Controller;

import com.chathurya.modal.*;
import com.chathurya.repository.InviteRequest;
import com.chathurya.response.MessageResponse;
import com.chathurya.service.InvitationService;
import com.chathurya.service.ProjectService;
import com.chathurya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/projects")
@RequestMapping
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<Project>>getProjects(
            @RequestParam(required = false)String category,
            @RequestParam(required = false)String tag,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {

        User user =userService.findUserProfileByJwt(jwt);
        List<Project> projects=projectService.getProjectByTeam(user, category, tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project>getProjects(
            @PathVariable Long projectId,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {

        User user =userService.findUserProfileByJwt(jwt);
        Project project=projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project>createProjects(
            @RequestHeader("Authorization")String jwt,
            @RequestBody Project project
    ) throws Exception {

        User user =userService.findUserProfileByJwt(jwt);
        Project createdProject=projectService.createProject(project, user);
        return new ResponseEntity<>(createdProject, HttpStatus.OK);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project>updateProjects(
            @PathVariable Long projectId,
            @RequestHeader("Authorization")String jwt,
            @RequestBody Project project
    ) throws Exception {

        User user =userService.findUserProfileByJwt(jwt);
        Project updatedProject=projectService.updateProject(project, projectId);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse>deleteProjects(
            @PathVariable Long projectId,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {

        User user =userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user.getId());
        MessageResponse response=new MessageResponse("project deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>>searchProjects(
            @RequestParam(required = false)String keyword,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {

        User user =userService.findUserProfileByJwt(jwt);
        List<Project> projects=projectService.searchProjects(keyword, user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat>getChatByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {

        User user =userService.findUserProfileByJwt(jwt);
        Chat chat=projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse>inviteProjects(
            @RequestBody InviteRequest req,
            @RequestHeader("Authorization")String jwt,
            @RequestBody Project project
    ) throws Exception {

        User user =userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(req.getEmail(), req.getProjectId());
        MessageResponse res=new MessageResponse("User Invitation Sent");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/accept_invitation")
    public ResponseEntity<Invitation>acceptInviteProject(
            @RequestBody String token,
            @RequestHeader("Authorization")String jwt,
            @RequestBody Project project
    ) throws Exception {

        User user =userService.findUserProfileByJwt(jwt);
        Invitation invitation= invitationService.acceptInvitation(token, user.getId());
        projectService.addUserToProject(invitation.getProjectId(), user.getId());
        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }

}
