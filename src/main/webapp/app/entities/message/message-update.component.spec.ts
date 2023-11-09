/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MessageUpdate from './message-update.vue';
import MessageService from './message.service';
import AlertService from '@/shared/alert/alert.service';

type MessageUpdateComponentType = InstanceType<typeof MessageUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const messageSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MessageUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Message Management Update Component', () => {
    let comp: MessageUpdateComponentType;
    let messageServiceStub: SinonStubbedInstance<MessageService>;

    beforeEach(() => {
      route = {};
      messageServiceStub = sinon.createStubInstance<MessageService>(MessageService);
      messageServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          messageService: () => messageServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(MessageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.message = messageSample;
        messageServiceStub.update.resolves(messageSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(messageServiceStub.update.calledWith(messageSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        messageServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MessageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.message = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(messageServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        messageServiceStub.find.resolves(messageSample);
        messageServiceStub.retrieve.resolves([messageSample]);

        // WHEN
        route = {
          params: {
            messageId: '' + messageSample.id,
          },
        };
        const wrapper = shallowMount(MessageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.message).toMatchObject(messageSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        messageServiceStub.find.resolves(messageSample);
        const wrapper = shallowMount(MessageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
