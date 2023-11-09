/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Activity from './activity.vue';
import ActivityService from './activity.service';
import AlertService from '@/shared/alert/alert.service';

type ActivityComponentType = InstanceType<typeof Activity>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Activity Management Component', () => {
    let activityServiceStub: SinonStubbedInstance<ActivityService>;
    let mountOptions: MountingOptions<ActivityComponentType>['global'];

    beforeEach(() => {
      activityServiceStub = sinon.createStubInstance<ActivityService>(ActivityService);
      activityServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          activityService: () => activityServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        activityServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Activity, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(activityServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.activities[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: ActivityComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Activity, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        activityServiceStub.retrieve.reset();
        activityServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        activityServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeActivity();
        await comp.$nextTick(); // clear components

        // THEN
        expect(activityServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(activityServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
