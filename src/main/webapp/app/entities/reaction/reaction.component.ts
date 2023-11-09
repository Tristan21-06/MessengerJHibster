import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import ReactionService from './reaction.service';
import { type IReaction } from '@/shared/model/reaction.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Reaction',
  setup() {
    const { t: t$ } = useI18n();
    const reactionService = inject('reactionService', () => new ReactionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const reactions: Ref<IReaction[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveReactions = async () => {
      isFetching.value = true;
      try {
        const res = await reactionService().retrieve();
        reactions.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveReactions();
    };

    onMounted(async () => {
      await retrieveReactions();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IReaction) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeReaction = async () => {
      try {
        await reactionService().delete(removeId.value);
        const message = t$('messengerJHibsterApp.reaction.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveReactions();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      reactions,
      handleSyncList,
      isFetching,
      retrieveReactions,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeReaction,
      t$,
    };
  },
});
