package edu.tcu.cs.hogwartsartifactsonline.artifact;

import edu.tcu.cs.hogwartsartifactsonline.artifact.converter.ArtifactDtoToArtifactConverter;
import edu.tcu.cs.hogwartsartifactsonline.artifact.converter.ArtifactToArtifactDtoConverter;
import edu.tcu.cs.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import edu.tcu.cs.hogwartsartifactsonline.system.Result;
import edu.tcu.cs.hogwartsartifactsonline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artifacts")
public class ArtifactController {

    private final ArtifactService artifactService;
    private final ArtifactToArtifactDtoConverter converter;
    private final ArtifactDtoToArtifactConverter dtoToEntityConverter;

    public ArtifactController(ArtifactService artifactService,
                              ArtifactToArtifactDtoConverter converter,
                              ArtifactDtoToArtifactConverter dtoToEntityConverter) {
        this.artifactService = artifactService;
        this.converter = converter;
        this.dtoToEntityConverter = dtoToEntityConverter;
    }

    @GetMapping("/{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId) {
        Artifact artifact = artifactService.findById(artifactId);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", converter.convert(artifact));
    }

    @GetMapping
    public Result findAllArtifacts() {
        List<ArtifactDto> dtos = artifactService.findAll()
                .stream()
                .map(converter::convert)
                .toList();

        return new Result(true, StatusCode.SUCCESS, "Find All Success", dtos);
    }

    @PostMapping
    public Result addArtifact(@Valid @RequestBody ArtifactDto dto) {
        Artifact artifact = dtoToEntityConverter.convert(dto);
        Artifact saved = artifactService.save(artifact);
        return new Result(true, StatusCode.SUCCESS, "Add Success", converter.convert(saved));
    }

    @PutMapping("/{artifactId}")
    public Result updateArtifact(@PathVariable String artifactId,
                                 @Valid @RequestBody ArtifactDto dto) {

        Artifact update = dtoToEntityConverter.convert(dto);
        Artifact updated = artifactService.update(artifactId, update);

        return new Result(true, StatusCode.SUCCESS, "Update Success", converter.convert(updated));
    }

    @DeleteMapping("/{artifactId}")
    public Result deleteArtifact(@PathVariable String artifactId) {
        artifactService.delete(artifactId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}