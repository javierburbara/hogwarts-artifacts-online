package edu.tcu.cs.hogwartsartifactsonline.wizard;

import edu.tcu.cs.hogwartsartifactsonline.artifact.Artifact;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Wizard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "owner")
    private List<Artifact> artifacts = new ArrayList<>();

    public Wizard() {
    }

    public Wizard(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Wizard(Integer id, String name, List<Artifact> artifacts) {
        this.id = id;
        this.name = name;
        this.artifacts = artifacts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public void addArtifact(Artifact artifact) {
        artifact.setOwner(this);
        this.artifacts.add(artifact);
    }

    public int getNumberOfArtifacts() {
        return this.artifacts.size();
    }

    public void removeAllArtifacts() {
        for (Artifact artifact : this.artifacts) {
            artifact.setOwner(null);
        }
        this.artifacts = null;
    }
}

public void removeArtifact(Artifact artifact) {
    artifact.setOwner(null);
    this.artifacts.remove(artifact);
}