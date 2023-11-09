/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ConversationUpdate from './conversation-update.vue';
import ConversationService from './conversation.service';
import AlertService from '@/shared/alert/alert.service';

import MessageService from '@/entities/message/message.service';

type ConversationUpdateComponentType = InstanceType<typeof ConversationUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const conversationSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ConversationUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Conversation Management Update Component', () => {
    let comp: ConversationUpdateComponentType;
    let conversationServiceStub: SinonStubbedInstance<ConversationService>;

    beforeEach(() => {
      route = {};
      conversationServiceStub = sinon.createStubInstance<ConversationService>(ConversationService);
      conversationServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          conversationService: () => conversationServiceStub,
          messageService: () =>
            sinon.createStubInstance<MessageService>(MessageService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ConversationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.conversation = conversationSample;
        conversationServiceStub.update.resolves(conversationSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(conversationServiceStub.update.calledWith(conversationSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        conversationServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ConversationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.conversation = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(conversationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        conversationServiceStub.find.resolves(conversationSample);
        conversationServiceStub.retrieve.resolves([conversationSample]);

        // WHEN
        route = {
          params: {
            conversationId: '' + conversationSample.id,
          },
        };
        const wrapper = shallowMount(ConversationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.conversation).toMatchObject(conversationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        conversationServiceStub.find.resolves(conversationSample);
        const wrapper = shallowMount(ConversationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
