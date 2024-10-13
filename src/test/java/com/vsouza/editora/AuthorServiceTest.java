package com.vsouza.editora;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsouza.editora.dto.AuthorDTO;
import com.vsouza.editora.entities.Author;
import com.vsouza.editora.repositories.AuthorRepository;
import com.vsouza.editora.services.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthorServiceTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	private AuthorDTO authorDTO;

	@Mock
	private AuthorService authorService;

	@Autowired
    private AuthorRepository authorRepository;

	@Test
	void contextLoads() {
	}


	//inicializar objetos
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		authorDTO = getAuthorDTO();
		authorService.save(authorDTO);

	}

	@Test
	void testSaveAuthor() throws Exception {

		mockMvc.perform(post("/")
				.contentType("application/json")
				.content(mapper.writeValueAsString(authorDTO)))
				.andExpect(status().isOk());

	}

	@Test
	void testGetAuthorById() throws Exception {

		authorService.save(authorDTO);

		mockMvc.perform(get("/" + authorDTO.getId())
						.contentType("application/json")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("George"))
				.andExpect(jsonPath("$.lastName").value("RR Martin"));



	}

	@Test
	void testGetAuthorNotFound() throws Exception {

		authorService.save(authorDTO);

		mockMvc.perform(get("/" + 132)
						.contentType("application/json")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}

    @Test
    void testUpdateAuthor() throws Exception {
        authorDTO = getAuthorDTO();
        authorDTO.setEmail("georginho@email.com");

        mockMvc.perform(put("/" + authorDTO.getId())
        .contentType("application/json")
                .content(mapper.writeValueAsString(authorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("georginho@email.com"));
    }

	@Test
	void testDeleteAuthor() throws Exception {
		Author author = authorRepository.findAll().get(0);
		mockMvc.perform(delete("/" + author.getId())
						.contentType("application/json")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	AuthorDTO getAuthorDTO() {
		AuthorDTO authorDTO = new AuthorDTO();
		authorDTO.setId(14L);
		authorDTO.setFullName("George RR Martin");
		authorDTO.setEmail("george.martin@vsouza.com");
		return authorDTO;
	}
}
