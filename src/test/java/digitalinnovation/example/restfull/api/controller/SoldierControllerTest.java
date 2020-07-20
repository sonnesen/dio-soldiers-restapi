package digitalinnovation.example.restfull.api.controller;

import static digitalinnovation.example.restfull.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import digitalinnovation.example.restfull.api.resource.SoldierResource;
import digitalinnovation.example.restfull.domain.service.SoldierService;

@ExtendWith(MockitoExtension.class)
class SoldierControllerTest {

  private static final String SOLDIER_API_URL_PATH = "/api/v1/soldiers";

  private MockMvc mockMvc;

  @Mock private SoldierService soldierService;

  @InjectMocks private SoldierController soldierController;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(soldierController).build();
  }

  @Test
  void wheGETisCalledWithValidIdThenOkStatusIsReturned() throws Exception {
    // given
    SoldierResource soldierResource =
        SoldierResource.builder().id(1L).arma("Arco e flexa").nome("Legolas").raca("Elfo").build();

    // when
    when(soldierService.findById(soldierResource.getId())).thenReturn(soldierResource);

    // then
    mockMvc
        .perform(
            get(SOLDIER_API_URL_PATH + "/" + soldierResource.getId())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(soldierResource.getId().intValue())))
        .andExpect(jsonPath("$.nome", is(soldierResource.getNome())))
        .andExpect(jsonPath("$.arma", is(soldierResource.getArma())))
        .andExpect(jsonPath("$.raca", is(soldierResource.getRaca())));
  }

  @Test
  void whenPOSTisCalledThenASoldierIsCreated() throws Exception {
    // given
    SoldierResource soldierResource =
        SoldierResource.builder().id(1L).arma("Arco e flexa").nome("Legolas").raca("Elfo").build();

    // when
    when(soldierService.save(soldierResource)).thenReturn(soldierResource);

    // then
    mockMvc
        .perform(
            post(SOLDIER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(soldierResource)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(soldierResource.getId().intValue())))
        .andExpect(jsonPath("$.arma", is(soldierResource.getArma())))
        .andExpect(jsonPath("$.nome", is(soldierResource.getNome())))
        .andExpect(jsonPath("$.raca", is(soldierResource.getRaca())));
  }

  @Test
  void whenPOSTisCalledWithoutRequiredFieldThenanErrorIsReturned() throws Exception {
    // given
    SoldierResource soldierResource =
        SoldierResource.builder().nome("Legolas").raca("Elfo").build();

    // then
    mockMvc
        .perform(
            post(SOLDIER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(soldierResource)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void wheGETListSoldiersIsCalledThenOkStatusIsReturned() throws Exception {
    // given
    SoldierResource soldierResource =
        SoldierResource.builder().id(1L).arma("Arco e flexa").nome("Legolas").raca("Elfo").build();

    // when
    when(soldierService.findAll()).thenReturn(Collections.singletonList(soldierResource));

    // then
    mockMvc
        .perform(get(SOLDIER_API_URL_PATH).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id", is(soldierResource.getId().intValue())))
        .andExpect(jsonPath("$.content[0].nome", is(soldierResource.getNome())))
        .andExpect(jsonPath("$.content[0].arma", is(soldierResource.getArma())))
        .andExpect(jsonPath("$.content[0].raca", is(soldierResource.getRaca())));
  }

  @Test
  void whenPUTisCalledWithValidIdAndFieldsThenOkStatusIsReturned() throws Exception {
    // given
    SoldierResource soldierResource =
        SoldierResource.builder()
            .id(1L)
            .arma("Arco e flexa")
            .nome("Legolas Greenleaf")
            .raca("Elfo")
            .build();

    // when
    when(soldierService.update(soldierResource.getId(), soldierResource))
        .thenReturn(soldierResource);

    // then
    mockMvc
        .perform(
            put(SOLDIER_API_URL_PATH + "/" + soldierResource.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(soldierResource)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(soldierResource.getId().intValue())))
        .andExpect(jsonPath("$.nome", is(soldierResource.getNome())))
        .andExpect(jsonPath("$.arma", is(soldierResource.getArma())))
        .andExpect(jsonPath("$.raca", is(soldierResource.getRaca())));
  }

  @Test
  void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
    // given
    SoldierResource soldierResource =
        SoldierResource.builder().id(1L).arma("Arco e flexa").nome("Legolas").raca("Elfo").build();
    // when
    doNothing().when(soldierService).deleteById(soldierResource.getId());

    // then
    mockMvc
        .perform(
            delete(SOLDIER_API_URL_PATH + "/" + soldierResource.getId())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }
}
