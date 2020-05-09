package de.darfichraus.controller;

import de.darfichraus.api.WebresourcesApi;
import de.darfichraus.model.WebResource;
import de.darfichraus.service.WebResourceService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebResourceController implements WebresourcesApi {

    private final WebResourceService webResourceService;


    public WebResourceController(WebResourceService webResourceService) {
        this.webResourceService = webResourceService;
    }

    @Override
    public ResponseEntity<Resource> getRawWebResourcesById(String id) {
        final WebResource webResource = webResourceService.getWebResourceById(id);
        return ResponseEntity.ok()
                .header("Content-Disposition","attachment; filename=" + webResource.getFileName())
                .contentType(MediaType.parseMediaType(webResource.getContentType()))
                .body(new ByteArrayResource(webResource.getFile()));
    }

    @Override
    public ResponseEntity<WebResource> getWebResourcesById(String id) {
        return ResponseEntity.ok(webResourceService.getWebResourceById(id));
    }

}
