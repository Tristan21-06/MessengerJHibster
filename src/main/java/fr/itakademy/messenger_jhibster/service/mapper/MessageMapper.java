package fr.itakademy.messenger_jhibster.service.mapper;

import fr.itakademy.messenger_jhibster.domain.Message;
import fr.itakademy.messenger_jhibster.service.dto.MessageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Message} and its DTO {@link MessageDTO}.
 */
@Mapper(componentModel = "spring")
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {}
