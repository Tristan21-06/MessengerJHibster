import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ActivityService from './activity.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IActivity, Activity } from '@/shared/model/activity.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ActivityUpdate',
  setup() {
    const activityService = inject('activityService', () => new ActivityService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const activity: Ref<IActivity> = ref(new Activity());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveActivity = async activityId => {
      try {
        const res = await activityService().find(activityId);
        activity.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.activityId) {
      retrieveActivity(route.params.activityId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      imageAcivity: {},
      conversations: {},
    };
    const v$ = useVuelidate(validationRules, activity as any);
    v$.value.$validate();

    return {
      activityService,
      alertService,
      activity,
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
      if (this.activity.id) {
        this.activityService()
          .update(this.activity)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('messengerJHibsterApp.activity.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.activityService()
          .create(this.activity)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('messengerJHibsterApp.activity.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
