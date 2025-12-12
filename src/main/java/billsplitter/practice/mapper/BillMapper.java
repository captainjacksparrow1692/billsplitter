package billsplitter.practice.mapper;

import billsplitter.practice.dto.PersonCostDto;
import billsplitter.practice.dto.request.BillRequestDto;
import billsplitter.practice.dto.response.BillResponseDto;
import billsplitter.practice.entity.Bill;
import billsplitter.practice.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillMapper {

    // Request → Entity
    @Mapping(target = "commissionPercent", ignore = true) // сервис сам посчитает
    Bill toEntity(BillRequestDto dto);

    // PersonCostDto → Person
    Person toEntity(PersonCostDto dto);

    // Person → PersonCostDto
    PersonCostDto toDto(Person person);

    // Entity → Response
    BillResponseDto toResponse(Bill bill);
}
