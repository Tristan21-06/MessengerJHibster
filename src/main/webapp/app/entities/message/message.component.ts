import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import MessageService from './message.service';
import { type IMessage } from '@/shared/model/message.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Message',
  setup() {
    const { t: t$ } = useI18n();
    const messageService = inject('messageService', () => new MessageService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const messages: Ref<IMessage[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMessages = async () => {
      isFetching.value = true;
      try {
        const res = await messageService().retrieve();
        messages.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMessages();
    };

    onMounted(async () => {
      await retrieveMessages();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMessage) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMessage = async () => {
      try {
        await messageService().delete(removeId.value);
        const message = t$('messengerJHibsterApp.message.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMessages();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      messages,
      handleSyncList,
      isFetching,
      retrieveMessages,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMessage,
      t$,
    };
  },
});
