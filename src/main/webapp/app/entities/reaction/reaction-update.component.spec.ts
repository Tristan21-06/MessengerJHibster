/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ReactionUpdate from './reaction-update.vue';
import ReactionService from './reaction.service';
import AlertService from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';
import MessageService from '@/entities/message/message.service';

type ReactionUpdateComponentType = InstanceType<typeof ReactionUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const reactionSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ReactionUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Reaction Management Update Component', () => {
    let comp: ReactionUpdateComponentType;
    let reactionServiceStub: SinonStubbedInstance<ReactionService>;

    beforeEach(() => {
      route = {};
      reactionServiceStub = sinon.createStubInstance<ReactionService>(ReactionService);
      reactionServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          reactionService: () => reactionServiceStub,

          userService: () =>
            sinon.createStubInstance<UserService>(UserService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
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
        const wrapper = shallowMount(ReactionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.reaction = reactionSample;
        reactionServiceStub.update.resolves(reactionSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(reactionServiceStub.update.calledWith(reactionSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        reactionServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ReactionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.reaction = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(reactionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        reactionServiceStub.find.resolves(reactionSample);
        reactionServiceStub.retrieve.resolves([reactionSample]);

        // WHEN
        route = {
          params: {
            reactionId: '' + reactionSample.id,
          },
        };
        const wrapper = shallowMount(ReactionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.reaction).toMatchObject(reactionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        reactionServiceStub.find.resolves(reactionSample);
        const wrapper = shallowMount(ReactionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
