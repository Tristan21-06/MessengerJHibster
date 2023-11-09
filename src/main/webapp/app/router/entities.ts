import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Message = () => import('@/entities/message/message.vue');
const MessageUpdate = () => import('@/entities/message/message-update.vue');
const MessageDetails = () => import('@/entities/message/message-details.vue');

const Reaction = () => import('@/entities/reaction/reaction.vue');
const ReactionUpdate = () => import('@/entities/reaction/reaction-update.vue');
const ReactionDetails = () => import('@/entities/reaction/reaction-details.vue');

const Conversation = () => import('@/entities/conversation/conversation.vue');
const ConversationUpdate = () => import('@/entities/conversation/conversation-update.vue');
const ConversationDetails = () => import('@/entities/conversation/conversation-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'message',
      name: 'Message',
      component: Message,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'message/new',
      name: 'MessageCreate',
      component: MessageUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'message/:messageId/edit',
      name: 'MessageEdit',
      component: MessageUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'message/:messageId/view',
      name: 'MessageView',
      component: MessageDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reaction',
      name: 'Reaction',
      component: Reaction,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reaction/new',
      name: 'ReactionCreate',
      component: ReactionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reaction/:reactionId/edit',
      name: 'ReactionEdit',
      component: ReactionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'reaction/:reactionId/view',
      name: 'ReactionView',
      component: ReactionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'conversation',
      name: 'Conversation',
      component: Conversation,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'conversation/new',
      name: 'ConversationCreate',
      component: ConversationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'conversation/:conversationId/edit',
      name: 'ConversationEdit',
      component: ConversationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'conversation/:conversationId/view',
      name: 'ConversationView',
      component: ConversationDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
