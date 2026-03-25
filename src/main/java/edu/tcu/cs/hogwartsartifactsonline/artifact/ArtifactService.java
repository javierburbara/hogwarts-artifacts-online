package edu.tcu.cs.hogwartsartifactsonline.artifact;

import edu.tcu.cs.hogwartsartifactsonline.artifact.utils.IdWorker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtifactService {

    private final ArtifactRepository repository;
    private final IdWorker idWorker;

    public ArtifactService(ArtifactRepository repository, IdWorker idWorker) {
        this.repository = repository;
        this.idWorker = idWorker;
    }

    public Artifact findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ArtifactNotFoundException(id));
    }

    public List<Artifact> findAll() {
        return repository.findAll();
    }

    public Artifact save(Artifact artifact) {
        artifact.setId("" + idWorker.nextId());
        return repository.save(artifact);
    }

    public Artifact update(String id, Artifact update) {
        return repository.findById(id)
                .map(old -> {
                    old.setName(update.getName());
                    old.setDescription(update.getDescription());
                    old.setImageUrl(update.getImageUrl());
                    return repository.save(old);
                })
                .orElseThrow(() -> new ArtifactNotFoundException(id));
    }

    public void delete(String id) {
        repository.findById(id)
                .orElseThrow(() -> new ArtifactNotFoundException(id));
        repository.deleteById(id);
    }
}