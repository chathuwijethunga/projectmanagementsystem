package com.chathurya.service;

import com.chathurya.modal.Chat;
import com.chathurya.modal.Project;
import com.chathurya.modal.User;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project, User user)throws Exception;

    List<Project> getProjectByTeam(User user, String category, String tag)throws Exception;

    Project getProjectById(Long projectId)throws Exception;

    void deleteProject(Long projectId, Long userId)throws Exception;
    //why userId here. should check weather the owner is deleting the project.

    Project updateProject(Project updatedProject, Long id)throws Exception;

    void addUserToProject(Long projectId, Long userId)throws Exception;

    void removeUserToProject(Long projectId, Long userId)throws Exception;

    Chat getChatByProjectId(Long projectId)throws Exception;

    List<Project> searchProjects(String keyword, User user)throws Exception;

}
