import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ConversationService from './conversation.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';
import MessageService from '@/entities/message/message.service';
import { type IMessage } from '@/shared/model/message.model';
import { type IConversation, Conversation } from '@/shared/model/conversation.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ConversationUpdate',
  setup() {
    const conversationService = inject('conversationService', () => new ConversationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const conversation: Ref<IConversation> = ref(new Conversation());
    const userService = inject('userService', () => new UserService());
    const users: Ref<Array<any>> = ref([]);

    const messageService = inject('messageService', () => new MessageService());

    const messages: Ref<IMessage[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      userService()
        .retrieve()
        .then(res => {
          users.value = res.data;
        });
      messageService()
        .retrieve()
        .then(res => {
          messages.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {},
      color: {},
      users: {},
      message: {},
    };
    const v$ = useVuelidate(validationRules, conversation as any);
    v$.value.$validate();

    return {
      conversationService,
      alertService,
      conversation,
      previousState,
      isSaving,
      currentLanguage,
      users,
      messages,
      v$,
      t$,
    };
  },
  created(): void {
    this.conversation.users = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.conversation.id) {
        this.conversationService()
          .update(this.conversation)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('messengerJHibsterApp.conversation.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.conversationService()
          .create(this.conversation)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('messengerJHibsterApp.conversation.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    getSelected(selectedVals, option): any {
      if (selectedVals) {
        return selectedVals.find(value => option.id === value.id) ?? option;
      }
      return option;
    },
  },
});
