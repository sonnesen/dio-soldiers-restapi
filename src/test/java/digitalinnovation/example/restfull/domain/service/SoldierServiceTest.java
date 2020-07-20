package digitalinnovation.example.restfull.domain.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import digitalinnovation.example.restfull.api.resource.SoldierResource;
import digitalinnovation.example.restfull.core.mapper.SoldierMapper;
import digitalinnovation.example.restfull.domain.entity.Soldier;
import digitalinnovation.example.restfull.domain.exception.SoldierNotFoundException;
import digitalinnovation.example.restfull.domain.repository.SoldierRepository;

@ExtendWith(MockitoExtension.class)
class SoldierServiceTest {

  @Mock private SoldierRepository repository;

  @InjectMocks private SoldierService service;

  private SoldierMapper mapper = SoldierMapper.INSTANCE;

  @Test
  void whenValidSoldierIdIsGivenThenReturnASoldier() throws SoldierNotFoundException {
    // given
    SoldierResource expectedSoldierResource =
        SoldierResource.builder().id(1L).arma("Arco e flexa").nome("Legolas").raca("elfo").build();

    Soldier expectedFoundSoldier = mapper.toModel(expectedSoldierResource);

    // when
    when(repository.findById(expectedSoldierResource.getId()))
        .thenReturn(Optional.of(expectedFoundSoldier));

    // then
    SoldierResource foundSoldierResouce = service.findById(expectedSoldierResource.getId());

    assertThat(foundSoldierResouce.getId(), is(equalTo(expectedSoldierResource.getId())));
    assertThat(foundSoldierResouce.getNome(), is(equalTo(expectedSoldierResource.getNome())));
    assertThat(foundSoldierResouce.getArma(), is(equalTo(expectedSoldierResource.getArma())));
    assertThat(foundSoldierResouce.getRaca(), is(equalTo(expectedSoldierResource.getRaca())));
  }

  @Test
  void whenNotRegisteredSoldierIdIsGivenThenThrowAnException() {
    // given
    SoldierResource expectedSoldierResource =
        SoldierResource.builder().id(1L).arma("Arco e flexa").nome("Legolas").raca("elfo").build();

    // when
    when(repository.findById(expectedSoldierResource.getId())).thenReturn(Optional.empty());

    // then
    assertThrows(
        SoldierNotFoundException.class, () -> service.findById(expectedSoldierResource.getId()));
  }

  @Test
  void whenListSoldiersIsCalledThenReturnAListOfSoldiers() {
    // given
    SoldierResource soldierResource =
        SoldierResource.builder().id(1L).arma("Arco e flexa").nome("Legolas").raca("elfo").build();

    Soldier soldier = mapper.toModel(soldierResource);

    // when
    when(repository.findAll()).thenReturn(Collections.singletonList(soldier));

    // then
    List<SoldierResource> foundListSoldiersResource = service.findAll();

    assertThat(foundListSoldiersResource, is(not(empty())));
    assertThat(foundListSoldiersResource.get(0), is(equalTo(soldierResource)));
  }

  @Test
  void whenListSoldiersIsCalledThenReturnAnEmptyListOfSoldiers() {
    // when
    when(repository.findAll()).thenReturn(Collections.emptyList());

    // then
    List<SoldierResource> foundListSoldiersResource = service.findAll();

    assertThat(foundListSoldiersResource, is(empty()));
  }

  @Test
  void whenExclusionIsCalledWithValidIdThenASoldierShouldBeDeleted()
      throws SoldierNotFoundException {
    // given
    SoldierResource expectedDeletedSoldierResource =
        SoldierResource.builder().id(1L).arma("Arco e flexa").nome("Legolas").raca("elfo").build();

    Soldier expectedDeletedSoldier = mapper.toModel(expectedDeletedSoldierResource);

    // when
    when(repository.findById(expectedDeletedSoldierResource.getId()))
        .thenReturn(Optional.of(expectedDeletedSoldier));
    doNothing().when(repository).deleteById(expectedDeletedSoldierResource.getId());

    // then
    service.deleteById(expectedDeletedSoldierResource.getId());

    verify(repository, times(1)).findById(expectedDeletedSoldierResource.getId());
    verify(repository, times(1)).deleteById(expectedDeletedSoldierResource.getId());
  }

  @Test
  void whenSoldierInformedThenItShouldBeCreated() {
    // given
    SoldierResource expectedCreatedSoldierResource =
        SoldierResource.builder().id(1L).arma("Arco e flexa").nome("Legolas").raca("elfo").build();

    Soldier expectedCreatedSoldier = mapper.toModel(expectedCreatedSoldierResource);

    // when
    when(repository.save(expectedCreatedSoldier)).thenReturn(expectedCreatedSoldier);

    // then
    SoldierResource createdSoldierResource = service.save(expectedCreatedSoldierResource);

    assertThat(createdSoldierResource.getId(), is(equalTo(expectedCreatedSoldierResource.getId())));
    assertThat(
        createdSoldierResource.getNome(), is(equalTo(expectedCreatedSoldierResource.getNome())));
    assertThat(
        createdSoldierResource.getArma(), is(equalTo(expectedCreatedSoldierResource.getArma())));
    assertThat(
        createdSoldierResource.getRaca(), is(equalTo(expectedCreatedSoldierResource.getRaca())));
  }

  @Test
  void whenAValidSoldierIsInformedThenItShouldBeUpdated() throws SoldierNotFoundException {
    // given
    SoldierResource expectedUpdatedSoldierResource =
        SoldierResource.builder().id(1L).arma("Arco e flexa").nome("Legolas").raca("elfo").build();

    Soldier expectedUpdatedSoldier = mapper.toModel(expectedUpdatedSoldierResource);

    // when
    when(repository.findById(expectedUpdatedSoldierResource.getId()))
        .thenReturn(Optional.of(expectedUpdatedSoldier));
    when(repository.save(expectedUpdatedSoldier)).thenReturn(expectedUpdatedSoldier);

    // then
    SoldierResource createdSoldierResource =
        service.update(expectedUpdatedSoldierResource.getId(), expectedUpdatedSoldierResource);

    assertThat(createdSoldierResource.getId(), is(equalTo(expectedUpdatedSoldierResource.getId())));
    assertThat(
        createdSoldierResource.getNome(), is(equalTo(expectedUpdatedSoldierResource.getNome())));
    assertThat(
        createdSoldierResource.getArma(), is(equalTo(expectedUpdatedSoldierResource.getArma())));
    assertThat(
        createdSoldierResource.getRaca(), is(equalTo(expectedUpdatedSoldierResource.getRaca())));
  }
}
