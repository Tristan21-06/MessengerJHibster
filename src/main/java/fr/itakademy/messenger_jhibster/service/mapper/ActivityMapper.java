package fr.itakademy.messenger_jhibster.service.mapper;

import fr.itakademy.messenger_jhibster.domain.Activity;
import fr.itakademy.messenger_jhibster.service.dto.ActivityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Activity} and its DTO {@link ActivityDTO}.
 */
@Mapper(componentModel = "spring")
public interface ActivityMapper extends EntityMapper<ActivityDTO, Activity> {}
