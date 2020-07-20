package digitalinnovation.example.restfull.core.mapper;

import digitalinnovation.example.restfull.domain.entity.Raca;

public class RacaMapper {

  public String asString(Raca raca) {
    return raca == null ? null : raca.getValue().toLowerCase();
  }

  public Raca asRaca(String raca) {
    return Raca.valueOf(raca.toUpperCase());
  }
}
