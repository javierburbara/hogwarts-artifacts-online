@DeleteMapping("/{artifactId}")
public Result deleteArtifact(@PathVariable String artifactId) {
    this.artifactService.delete(artifactId);
    return new Result(true, StatusCode.SUCCESS, "Delete Success");
}