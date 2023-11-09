import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ConversationService from './conversation.service';
import { type IConversation } from '@/shared/model/conversation.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ConversationDetails',
  setup() {
    const conversationService = inject('conversationService', () => new ConversationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const conversation: Ref<IConversation> = ref({});

    const retrieveConversation = async conversationId => {
      try {
        const res = await conversationService().find(conversationId);
        conversation.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.conversationId) {
      retrieveConversation(route.params.conversationId);
    }

    return {
      alertService,
      conversation,

      previousState,
      t$: useI18n().t,
    };
  },
});
