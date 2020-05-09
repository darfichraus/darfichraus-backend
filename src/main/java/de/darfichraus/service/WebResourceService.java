package de.darfichraus.service;

import de.darfichraus.model.WebResource;
import de.darfichraus.repository.WebResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Service
public class WebResourceService {

    final WebResourceRepository webResourceRepository;

    public WebResourceService(WebResourceRepository webResourceRepository) {
        this.webResourceRepository = webResourceRepository;
    }

    public WebResource getWebResourceById(final String id) {
        return webResourceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("the id provided was not found"));
    }

    public void deleteWebResource(final String id) {
        webResourceRepository.deleteById(id);
    }

    public String addWebResource(@Valid MultipartFile file) throws IOException {
        final WebResource webResource = new WebResource();
        webResource.setContentType(file.getContentType());
        webResource.setFile(file.getBytes());
        webResource.setFileName(file.getOriginalFilename());
        return webResourceRepository.save(webResource).getId();
    }

    public List<WebResource> getAllResources(@Valid Boolean includeFiles) {
        final List<WebResource> webResources = webResourceRepository.findAll();
        if(!Boolean.TRUE.equals(includeFiles)) {
            for (WebResource webResource : webResources) {
                webResource.setFile(null);
            }
        }
        return webResources;
    }
}
