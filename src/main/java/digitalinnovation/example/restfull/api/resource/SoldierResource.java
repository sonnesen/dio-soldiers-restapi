package digitalinnovation.example.restfull.api.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "soldier")
@Relation(collectionRelation = "soldiers")
@JsonInclude(Include.NON_NULL)
public class SoldierResource extends RepresentationModel<SoldierResource> {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 200)
    private String nome;

    @NotBlank
    @Size(min = 1, max = 200)
    private String raca;

    @NotBlank
    @Size(min = 1, max = 200)
    private String arma;

}
