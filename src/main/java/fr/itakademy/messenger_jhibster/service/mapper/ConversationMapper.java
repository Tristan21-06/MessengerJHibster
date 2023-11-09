package fr.itakademy.messenger_jhibster.service.mapper;

import fr.itakademy.messenger_jhibster.domain.Conversation;
import fr.itakademy.messenger_jhibster.domain.Message;
import fr.itakademy.messenger_jhibster.service.dto.ConversationDTO;
import fr.itakademy.messenger_jhibster.service.dto.MessageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Conversation} and its DTO {@link ConversationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConversationMapper extends EntityMapper<ConversationDTO, Conversation> {
    @Mapping(target = "message", source = "message", qualifiedByName = "messageId")
    ConversationDTO toDto(Conversation s);

    @Named("messageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MessageDTO toDtoMessageId(Message message);
}
