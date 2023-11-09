/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Reaction from './reaction.vue';
import ReactionService from './reaction.service';
import AlertService from '@/shared/alert/alert.service';

type ReactionComponentType = InstanceType<typeof Reaction>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Reaction Management Component', () => {
    let reactionServiceStub: SinonStubbedInstance<ReactionService>;
    let mountOptions: MountingOptions<ReactionComponentType>['global'];

    beforeEach(() => {
      reactionServiceStub = sinon.createStubInstance<ReactionService>(ReactionService);
      reactionServiceStub.retrieve.resolves({ headers: {} });

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
          reactionService: () => reactionServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        reactionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Reaction, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(reactionServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.reactions[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: ReactionComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Reaction, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        reactionServiceStub.retrieve.reset();
        reactionServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        reactionServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeReaction();
        await comp.$nextTick(); // clear components

        // THEN
        expect(reactionServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(reactionServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
