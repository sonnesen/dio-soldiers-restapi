package digitalinnovation.example.restfull.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import digitalinnovation.example.restfull.api.resource.SoldierResource;
import digitalinnovation.example.restfull.domain.exception.SoldierNotFoundException;
import digitalinnovation.example.restfull.domain.service.SoldierService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/soldiers")
@RequiredArgsConstructor
public class SoldierController {

  private final SoldierService soldierService;

  @GetMapping("/{id}")
  public ResponseEntity<SoldierResource> findById(@PathVariable() Long id)
      throws SoldierNotFoundException {
    SoldierResource resource = soldierService.findById(id);
    addLinkToResource(resource);
    return ResponseEntity.status(HttpStatus.OK).body(resource);
  }

  private SoldierResource addLinkToResource(SoldierResource resource) {
    return resource.add(linkTo(SoldierController.class).slash(resource.getId()).withSelfRel());
  }

  @PostMapping
  public ResponseEntity<SoldierResource> save(@RequestBody @Valid SoldierResource soldierResource) {
    SoldierResource resource = soldierService.save(soldierResource);
    addLinkToResource(resource);
    return ResponseEntity.status(HttpStatus.CREATED).body(resource);
  }

  @PutMapping("/{id}")
  public ResponseEntity<SoldierResource> update(
      @PathVariable() Long id, @RequestBody @Valid SoldierResource soldierResource)
      throws SoldierNotFoundException {
    SoldierResource resource = soldierService.update(id, soldierResource);
    addLinkToResource(resource);
    return ResponseEntity.ok(resource);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) throws SoldierNotFoundException {
    soldierService.deleteById(id);
  }

  @GetMapping
  public ResponseEntity<CollectionModel<SoldierResource>> findAll() {
    List<SoldierResource> soldiers =
        soldierService
            .findAll()
            .stream()
            .map(soldier -> addLinkToResource(soldier))
            .collect(Collectors.toList());

    return ResponseEntity.ok()
        .body(CollectionModel.of(soldiers, linkTo(SoldierController.class).withRel("soldiers")));
  }
}
