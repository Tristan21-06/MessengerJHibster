import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ReactionService from './reaction.service';
import { type IReaction } from '@/shared/model/reaction.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ReactionDetails',
  setup() {
    const reactionService = inject('reactionService', () => new ReactionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const reaction: Ref<IReaction> = ref({});

    const retrieveReaction = async reactionId => {
      try {
        const res = await reactionService().find(reactionId);
        reaction.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.reactionId) {
      retrieveReaction(route.params.reactionId);
    }

    return {
      alertService,
      reaction,

      previousState,
      t$: useI18n().t,
    };
  },
});
