package edu.tcu.cs.hogwartsartifactsonline.wizard;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.hogwartsartifactsonline.system.StatusCode;
import edu.tcu.cs.hogwartsartifactsonline.system.exception.ObjectNotFoundException;
import edu.tcu.cs.hogwartsartifactsonline.wizard.dto.WizardDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class WizardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WizardService wizardService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    List<Wizard> wizards;

    @BeforeEach
    void setUp() {
        this.wizards = new ArrayList<>();
        this.wizards.add(new Wizard(1, "Albus Dumbledore"));
        this.wizards.add(new Wizard(2, "Harry Potter"));
        this.wizards.add(new Wizard(3, "Neville Longbottom"));
    }

    @Test
    void testFindAllWizardsSuccess() throws Exception {
        given(this.wizardService.findAll()).willReturn(this.wizards);

        this.mockMvc.perform(get(this.baseUrl + "/wizards").accept("application/json"))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data.length()").value(this.wizards.size()))
                .andExpect(jsonPath("$.data[0].id").value(this.wizards.get(0).getId()))
                .andExpect(jsonPath("$.data[0].name").value(this.wizards.get(0).getName()));
    }

    @Test
    void testFindWizardByIdSuccess() throws Exception {
        given(this.wizardService.findById(1)).willReturn(this.wizards.get(0));

        this.mockMvc.perform(get(this.baseUrl + "/wizards/1").accept("application/json"))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Albus Dumbledore"));
    }

    @Test
    void testFindWizardByIdNotFound() throws Exception {
        given(this.wizardService.findById(99))
                .willThrow(new ObjectNotFoundException("wizard", 99));

        this.mockMvc.perform(get(this.baseUrl + "/wizards/99").accept("application/json"))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find wizard with Id 99"));
    }

    @Test
    void testAddWizardSuccess() throws Exception {
        WizardDto wizardDto = new WizardDto(null, "Severus Snape", null);
        String json = this.objectMapper.writeValueAsString(wizardDto);

        Wizard savedWizard = new Wizard(4, "Severus Snape");
        given(this.wizardService.save(any(Wizard.class))).willReturn(savedWizard);

        this.mockMvc.perform(post(this.baseUrl + "/wizards")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(json))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").value(4))
                .andExpect(jsonPath("$.data.name").value("Severus Snape"));
    }

    @Test
    void testUpdateWizardSuccess() throws Exception {
        WizardDto wizardDto = new WizardDto(null, "Updated Harry Potter", null);
        String json = this.objectMapper.writeValueAsString(wizardDto);

        Wizard updatedWizard = new Wizard(2, "Updated Harry Potter");
        given(this.wizardService.update(eq(2), any(Wizard.class))).willReturn(updatedWizard);

        this.mockMvc.perform(put(this.baseUrl + "/wizards/2")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(json))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.id").value(2))
                .andExpect(jsonPath("$.data.name").value("Updated Harry Potter"));
    }

    @Test
    void testUpdateWizardErrorWithNonExistentId() throws Exception {
        WizardDto wizardDto = new WizardDto(null, "Updated Harry Potter", null);
        String json = this.objectMapper.writeValueAsString(wizardDto);

        given(this.wizardService.update(eq(99), any(Wizard.class)))
                .willThrow(new ObjectNotFoundException("wizard", 99));

        this.mockMvc.perform(put(this.baseUrl + "/wizards/99")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(json))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find wizard with Id 99"));
    }

    @Test
    void testDeleteWizardSuccess() throws Exception {
        doNothing().when(this.wizardService).delete(2);

        this.mockMvc.perform(delete(this.baseUrl + "/wizards/2").accept("application/json"))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"));
    }

    @Test
    void testDeleteWizardErrorWithNonExistentId() throws Exception {
        doThrow(new ObjectNotFoundException("wizard", 99))
                .when(this.wizardService).delete(99);

        this.mockMvc.perform(delete(this.baseUrl + "/wizards/99").accept("application/json"))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find wizard with Id 99"));
    }
}
