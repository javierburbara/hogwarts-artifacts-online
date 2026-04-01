public void removeAllArtifacts() {
    for (Artifact a : artifacts) {
        a.setOwner(null);
    }
    artifacts = null;
}