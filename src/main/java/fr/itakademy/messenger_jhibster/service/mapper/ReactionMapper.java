package fr.itakademy.messenger_jhibster.service.mapper;

import fr.itakademy.messenger_jhibster.domain.Message;
import fr.itakademy.messenger_jhibster.domain.Reaction;
import fr.itakademy.messenger_jhibster.domain.User;
import fr.itakademy.messenger_jhibster.service.dto.MessageDTO;
import fr.itakademy.messenger_jhibster.service.dto.ReactionDTO;
import fr.itakademy.messenger_jhibster.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reaction} and its DTO {@link ReactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReactionMapper extends EntityMapper<ReactionDTO, Reaction> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "message", source = "message", qualifiedByName = "messageId")
    ReactionDTO toDto(Reaction s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("messageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MessageDTO toDtoMessageId(Message message);
}
