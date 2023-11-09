package fr.itakademy.messenger_jhibster.service.mapper;

import fr.itakademy.messenger_jhibster.domain.Message;
import fr.itakademy.messenger_jhibster.domain.Reaction;
import fr.itakademy.messenger_jhibster.service.dto.MessageDTO;
import fr.itakademy.messenger_jhibster.service.dto.ReactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reaction} and its DTO {@link ReactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReactionMapper extends EntityMapper<ReactionDTO, Reaction> {
    @Mapping(target = "message", source = "message", qualifiedByName = "messageId")
    ReactionDTO toDto(Reaction s);

    @Named("messageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MessageDTO toDtoMessageId(Message message);
}
