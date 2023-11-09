package fr.itakademy.messenger_jhibster.service.mapper;

import fr.itakademy.messenger_jhibster.domain.Activity;
import fr.itakademy.messenger_jhibster.domain.Conversation;
import fr.itakademy.messenger_jhibster.domain.Message;
import fr.itakademy.messenger_jhibster.domain.User;
import fr.itakademy.messenger_jhibster.service.dto.ActivityDTO;
import fr.itakademy.messenger_jhibster.service.dto.ConversationDTO;
import fr.itakademy.messenger_jhibster.service.dto.MessageDTO;
import fr.itakademy.messenger_jhibster.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Conversation} and its DTO {@link ConversationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConversationMapper extends EntityMapper<ConversationDTO, Conversation> {
    @Mapping(target = "users", source = "users", qualifiedByName = "userIdSet")
    @Mapping(target = "activities", source = "activities", qualifiedByName = "activityIdSet")
    @Mapping(target = "message", source = "message", qualifiedByName = "messageId")
    ConversationDTO toDto(Conversation s);

    @Mapping(target = "removeUsers", ignore = true)
    @Mapping(target = "removeActivities", ignore = true)
    Conversation toEntity(ConversationDTO conversationDTO);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("userIdSet")
    default Set<UserDTO> toDtoUserIdSet(Set<User> user) {
        return user.stream().map(this::toDtoUserId).collect(Collectors.toSet());
    }

    @Named("activityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ActivityDTO toDtoActivityId(Activity activity);

    @Named("activityIdSet")
    default Set<ActivityDTO> toDtoActivityIdSet(Set<Activity> activity) {
        return activity.stream().map(this::toDtoActivityId).collect(Collectors.toSet());
    }

    @Named("messageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MessageDTO toDtoMessageId(Message message);
}
