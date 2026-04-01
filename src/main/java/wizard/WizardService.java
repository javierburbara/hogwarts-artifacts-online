public void delete(Integer id) {
    Wizard wizard = repository.findById(id)
            .orElseThrow(() -> new WizardNotFoundException(id));

    wizard.removeAllArtifacts();
    repository.deleteById(id);
}