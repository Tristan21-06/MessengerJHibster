import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import ConversationService from './conversation.service';
import { type IConversation } from '@/shared/model/conversation.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Conversation',
  setup() {
    const { t: t$ } = useI18n();
    const conversationService = inject('conversationService', () => new ConversationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const conversations: Ref<IConversation[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveConversations = async () => {
      isFetching.value = true;
      try {
        const res = await conversationService().retrieve();
        conversations.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveConversations();
    };

    onMounted(async () => {
      await retrieveConversations();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IConversation) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeConversation = async () => {
      try {
        await conversationService().delete(removeId.value);
        const message = t$('messengerJHibsterApp.conversation.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveConversations();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      conversations,
      handleSyncList,
      isFetching,
      retrieveConversations,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeConversation,
      t$,
    };
  },
});
