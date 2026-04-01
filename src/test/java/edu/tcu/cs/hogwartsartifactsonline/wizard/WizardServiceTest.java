package edu.tcu.cs.hogwartsartifactsonline.wizard;

import edu.tcu.cs.hogwartsartifactsonline.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WizardServiceTest {

    @Mock
    WizardRepository wizardRepository;

    @InjectMocks
    WizardService wizardService;

    @Test
    void testFindAllSuccess() {
        List<Wizard> wizards = new ArrayList<>();
        wizards.add(new Wizard(1, "Albus Dumbledore"));
        wizards.add(new Wizard(2, "Harry Potter"));

        given(this.wizardRepository.findAll()).willReturn(wizards);

        List<Wizard> actualWizards = this.wizardService.findAll();

        assertThat(actualWizards.size()).isEqualTo(wizards.size());
        verify(this.wizardRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        Wizard wizard = new Wizard(1, "Albus Dumbledore");
        given(this.wizardRepository.findById(1)).willReturn(Optional.of(wizard));

        Wizard returnedWizard = this.wizardService.findById(1);

        assertThat(returnedWizard.getId()).isEqualTo(wizard.getId());
        assertThat(returnedWizard.getName()).isEqualTo(wizard.getName());
        verify(this.wizardRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        given(this.wizardRepository.findById(1)).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> this.wizardService.findById(1));
        verify(this.wizardRepository, times(1)).findById(1);
    }

    @Test
    void testSaveSuccess() {
        Wizard newWizard = new Wizard();
        newWizard.setName("Severus Snape");

        Wizard savedWizard = new Wizard(4, "Severus Snape");

        given(this.wizardRepository.save(newWizard)).willReturn(savedWizard);

        Wizard result = this.wizardService.save(newWizard);

        assertThat(result.getId()).isEqualTo(savedWizard.getId());
        assertThat(result.getName()).isEqualTo(savedWizard.getName());
        verify(this.wizardRepository, times(1)).save(newWizard);
    }

    @Test
    void testUpdateSuccess() {
        Wizard oldWizard = new Wizard(2, "Harry Potter");
        Wizard update = new Wizard();
        update.setName("Updated Harry Potter");

        given(this.wizardRepository.findById(2)).willReturn(Optional.of(oldWizard));
        given(this.wizardRepository.save(oldWizard)).willReturn(oldWizard);

        Wizard updatedWizard = this.wizardService.update(2, update);

        assertThat(updatedWizard.getId()).isEqualTo(2);
        assertThat(updatedWizard.getName()).isEqualTo(update.getName());
        verify(this.wizardRepository, times(1)).findById(2);
        verify(this.wizardRepository, times(1)).save(oldWizard);
    }

    @Test
    void testUpdateNotFound() {
        Wizard update = new Wizard();
        update.setName("Updated Harry Potter");

        given(this.wizardRepository.findById(2)).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> this.wizardService.update(2, update));
        verify(this.wizardRepository, times(1)).findById(2);
    }

    @Test
    void testDeleteSuccess() {
        Wizard foundWizard = new Wizard(2, "Harry Potter", new ArrayList<>());

        given(this.wizardRepository.findById(2)).willReturn(Optional.of(foundWizard));
        doNothing().when(this.wizardRepository).deleteById(2);

        this.wizardService.delete(2);

        verify(this.wizardRepository, times(1)).findById(2);
        verify(this.wizardRepository, times(1)).deleteById(2);
    }

    @Test
    void testDeleteNotFound() {
        given(this.wizardRepository.findById(2)).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> this.wizardService.delete(2));
        verify(this.wizardRepository, times(1)).findById(2);
    }
}
