package digitalinnovation.example.restfull.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import digitalinnovation.example.restfull.api.resource.SoldierResource;
import digitalinnovation.example.restfull.domain.entity.Soldier;

@Mapper(uses = RacaMapper.class)
public interface SoldierMapper {

  SoldierMapper INSTANCE = Mappers.getMapper(SoldierMapper.class);

  Soldier toModel(SoldierResource soldierResource);

  SoldierResource toDTO(Soldier soldier);
}
