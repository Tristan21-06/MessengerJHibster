import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ReactionService from './reaction.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';
import MessageService from '@/entities/message/message.service';
import { type IMessage } from '@/shared/model/message.model';
import { type IReaction, Reaction } from '@/shared/model/reaction.model';
import { ReactionType } from '@/shared/model/enumerations/reaction-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ReactionUpdate',
  setup() {
    const reactionService = inject('reactionService', () => new ReactionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const reaction: Ref<IReaction> = ref(new Reaction());
    const userService = inject('userService', () => new UserService());
    const users: Ref<Array<any>> = ref([]);

    const messageService = inject('messageService', () => new MessageService());

    const messages: Ref<IMessage[]> = ref([]);
    const reactionTypeValues: Ref<string[]> = ref(Object.keys(ReactionType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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
      type: {},
      user: {},
      message: {},
    };
    const v$ = useVuelidate(validationRules, reaction as any);
    v$.value.$validate();

    return {
      reactionService,
      alertService,
      reaction,
      previousState,
      reactionTypeValues,
      isSaving,
      currentLanguage,
      users,
      messages,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.reaction.id) {
        this.reactionService()
          .update(this.reaction)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('messengerJHibsterApp.reaction.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.reactionService()
          .create(this.reaction)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('messengerJHibsterApp.reaction.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
