import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import ActivityService from './activity.service';
import { type IActivity } from '@/shared/model/activity.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Activity',
  setup() {
    const { t: t$ } = useI18n();
    const activityService = inject('activityService', () => new ActivityService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const activities: Ref<IActivity[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveActivitys = async () => {
      isFetching.value = true;
      try {
        const res = await activityService().retrieve();
        activities.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveActivitys();
    };

    onMounted(async () => {
      await retrieveActivitys();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IActivity) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeActivity = async () => {
      try {
        await activityService().delete(removeId.value);
        const message = t$('messengerJHibsterApp.activity.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveActivitys();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      activities,
      handleSyncList,
      isFetching,
      retrieveActivitys,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeActivity,
      t$,
    };
  },
});
