package com.salama.company.Multinational.Company.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salama.company.Multinational.Company.entities.Skill;
import com.salama.company.Multinational.Company.entities.base.BaseResponse;
import com.salama.company.Multinational.Company.entities.enums.SkillLevel;
import com.salama.company.Multinational.Company.services.SkillsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
/**
 * @WebMvcTest is only going to scan the controller you've defined and the MVC infrastructure
 * . That's it. So if your controller has some dependency to other beans from your service layer
 * the test won't start until you either load that config yourself or provide a mock for it.
 * This is much faster as we only load a tiny portion of your app. This annotation uses slicing.
 * NOTE: i couldn't use this b/c of security beans and filters look for this later
 * EX: @WebMvcTest(SkillController.class)
 *     @ExtendWith(SpringExtension.class)
 */
//@SpringBootTest
/**
 * @SpringBootTest runs the whole application context
 * there is another option with is @ExtendWith(SpringExtension.class) which
 * just enables spring boot features like @Autowire, @MockBean during junit testing
 */
class SkillsControllerTest {

    @MockBean
    private SkillsService skillsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    @WithMockUser(username = "spring")
    void whenCallingGetSkills_thenReturn200WithSkillsList() throws Exception {
        //Given
        final List<Skill> skills = List.of(new Skill(0L, "Awesome Skill", SkillLevel.INTERMEDIATE));
        final BaseResponse<List<Skill>> baseResponse = BaseResponse.of(skills);
        when(skillsService.getAllSkills()).thenReturn(skills);
        final String expectedResponseContent = objectMapper.writeValueAsString(baseResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/skills").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponseContent));
    }
}