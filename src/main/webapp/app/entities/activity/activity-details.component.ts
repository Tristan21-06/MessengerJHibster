import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ActivityService from './activity.service';
import { type IActivity } from '@/shared/model/activity.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ActivityDetails',
  setup() {
    const activityService = inject('activityService', () => new ActivityService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const activity: Ref<IActivity> = ref({});

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

    return {
      alertService,
      activity,

      previousState,
      t$: useI18n().t,
    };
  },
});
