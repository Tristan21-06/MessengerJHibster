import { defineComponent, provide } from 'vue';

import MessageService from './message/message.service';
import ActivityService from './activity/activity.service';
import ReactionService from './reaction/reaction.service';
import ConversationService from './conversation/conversation.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('messageService', () => new MessageService());
    provide('activityService', () => new ActivityService());
    provide('reactionService', () => new ReactionService());
    provide('conversationService', () => new ConversationService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
