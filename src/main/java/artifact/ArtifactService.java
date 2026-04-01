public void delete(String artifactId) {
    this.artifactRepository.findById(artifactId)
            .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    this.artifactRepository.deleteById(artifactId);
}