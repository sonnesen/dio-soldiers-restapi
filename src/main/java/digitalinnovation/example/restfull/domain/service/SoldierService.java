package digitalinnovation.example.restfull.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import digitalinnovation.example.restfull.api.resource.SoldierResource;
import digitalinnovation.example.restfull.core.mapper.SoldierMapper;
import digitalinnovation.example.restfull.domain.entity.Soldier;
import digitalinnovation.example.restfull.domain.exception.SoldierNotFoundException;
import digitalinnovation.example.restfull.domain.repository.SoldierRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SoldierService {

    private final SoldierRepository soldierRepository;
    private SoldierMapper soldierMapper = SoldierMapper.INSTANCE;

    public SoldierResource findById(Long id) throws SoldierNotFoundException {
	Soldier soldier = verifyIfExists(id);
	return soldierMapper.toDTO(soldier);
    }

    private Soldier verifyIfExists(Long id) throws SoldierNotFoundException {
	return soldierRepository.findById(id).orElseThrow(() -> new SoldierNotFoundException(id));
    }

    public SoldierResource save(SoldierResource soldierResource) {
	Soldier soldier = soldierMapper.toModel(soldierResource);
	Soldier savedSoldier = soldierRepository.save(soldier);
	return soldierMapper.toDTO(savedSoldier);
    }

    public SoldierResource update(Long id, SoldierResource soldierResource) throws SoldierNotFoundException {
	Soldier soldier = verifyIfExists(id);

	soldier = soldierMapper.toModel(soldierResource);
	soldier.setId(id);

	Soldier updatedSoldier = soldierRepository.save(soldier);

	return soldierMapper.toDTO(updatedSoldier);
    }

    public void deleteById(Long id) throws SoldierNotFoundException {
	Soldier soldier = verifyIfExists(id);
	soldierRepository.delete(soldier);
    }

    public List<SoldierResource> findAll() {
	return soldierRepository.findAll()
		.stream()
		.map(soldierMapper::toDTO)
		.collect(Collectors.toList());
    }

}
