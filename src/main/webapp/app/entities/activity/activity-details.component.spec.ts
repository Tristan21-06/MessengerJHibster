/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ActivityDetails from './activity-details.vue';
import ActivityService from './activity.service';
import AlertService from '@/shared/alert/alert.service';

type ActivityDetailsComponentType = InstanceType<typeof ActivityDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const activitySample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Activity Management Detail Component', () => {
    let activityServiceStub: SinonStubbedInstance<ActivityService>;
    let mountOptions: MountingOptions<ActivityDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      activityServiceStub = sinon.createStubInstance<ActivityService>(ActivityService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          activityService: () => activityServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        activityServiceStub.find.resolves(activitySample);
        route = {
          params: {
            activityId: '' + 123,
          },
        };
        const wrapper = shallowMount(ActivityDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.activity).toMatchObject(activitySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        activityServiceStub.find.resolves(activitySample);
        const wrapper = shallowMount(ActivityDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
