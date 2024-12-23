package com.project.resume.service;

import com.project.resume.model.Project;
import com.project.resume.repo.ProjectRepository;
import com.project.resume.service.enums.Folder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final FilesService filesService;

    @Value("${projects-path-from-templates}")
    String project_path_from_templates;

    public void save(Project project, MultipartFile page_file, MultipartFile image_file) {
        // this method copies received file to PROJECT_PAGES
        filesService.addFileToFolderStatic(page_file, Folder.PROJECT_PAGES);
        project.setPage(page_file.getOriginalFilename());
        // this method copies received file to IMAGES
        filesService.addFileToFolderStatic(image_file, Folder.IMAGES);
        project.setImage(image_file.getOriginalFilename());
        projectRepository.save(project);
    }

    public void edit(Project project, MultipartFile page_file, MultipartFile image_file) {
        if (findById(project.getId()).isPresent()) {
            Project original_project = findById(project.getId()).get();

            // changes project's page html only if file was passed to the method
            if (!page_file.isEmpty()) {
                filesService.addFileToFolderStatic(page_file, Folder.PROJECT_PAGES);
                original_project.setPage(page_file.getOriginalFilename());
            }

            // changes project's title image only if file was passed to the method
            if (!image_file.isEmpty()) {
                filesService.addFileToFolderStatic(image_file, Folder.IMAGES);
                original_project.setImage(image_file.getOriginalFilename());
            }

            original_project.setTitle(project.getTitle());
            original_project.setDescription(project.getDescription());
            original_project.setMain(project.getMain());
            original_project.setOrder(project.getOrder());

            projectRepository.save(original_project);
        }
    }

    public Optional<Project> findById(Integer id) {
        return projectRepository.findById(id);
    }

    public void deleteById(int id) {
        projectRepository.deleteById(id);
    }

    public List<Project> findAll(Sort sort) {
        return projectRepository.findAll(sort);
    }

    public String getFragmentPathOfProject(Project project) {
        return project_path_from_templates + "/" + getFragmentNameOfProject(project);

    }

    public String getFragmentNameOfProject(Project project) {
        return FilenameUtils.removeExtension(new File(project.getPage()).getName());
    }
}
