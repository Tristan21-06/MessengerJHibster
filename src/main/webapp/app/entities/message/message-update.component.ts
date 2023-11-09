import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MessageService from './message.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IMessage, Message } from '@/shared/model/message.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MessageUpdate',
  setup() {
    const messageService = inject('messageService', () => new MessageService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const message: Ref<IMessage> = ref(new Message());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMessage = async messageId => {
      try {
        const res = await messageService().find(messageId);
        message.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.messageId) {
      retrieveMessage(route.params.messageId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      texte: {},
      reactions: {},
      conversations: {},
    };
    const v$ = useVuelidate(validationRules, message as any);
    v$.value.$validate();

    return {
      messageService,
      alertService,
      message,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.message.id) {
        this.messageService()
          .update(this.message)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('messengerJHibsterApp.message.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.messageService()
          .create(this.message)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('messengerJHibsterApp.message.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
