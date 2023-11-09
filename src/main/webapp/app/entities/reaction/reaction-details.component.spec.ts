/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ReactionDetails from './reaction-details.vue';
import ReactionService from './reaction.service';
import AlertService from '@/shared/alert/alert.service';

type ReactionDetailsComponentType = InstanceType<typeof ReactionDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const reactionSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Reaction Management Detail Component', () => {
    let reactionServiceStub: SinonStubbedInstance<ReactionService>;
    let mountOptions: MountingOptions<ReactionDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      reactionServiceStub = sinon.createStubInstance<ReactionService>(ReactionService);

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
          reactionService: () => reactionServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        reactionServiceStub.find.resolves(reactionSample);
        route = {
          params: {
            reactionId: '' + 123,
          },
        };
        const wrapper = shallowMount(ReactionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.reaction).toMatchObject(reactionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        reactionServiceStub.find.resolves(reactionSample);
        const wrapper = shallowMount(ReactionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
