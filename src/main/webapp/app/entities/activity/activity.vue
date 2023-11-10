<template>
  <div>
    <h2 id="page-heading" data-cy="ActivityHeading">
      <span v-text="t$('messengerJHibsterApp.activity.home.title')" id="activity-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('messengerJHibsterApp.activity.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ActivityCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-activity"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('messengerJHibsterApp.activity.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && activities && activities.length === 0">
      <span v-text="t$('messengerJHibsterApp.activity.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="activities && activities.length > 0">
      <table class="table table-striped" aria-describedby="activities">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('messengerJHibsterApp.activity.imageActivity')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="activity in activities" :key="activity.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ActivityView', params: { activityId: activity.id } }">{{ activity.id }}</router-link>
            </td>
            <td>{{ activity.imageActivity }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ActivityView', params: { activityId: activity.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ActivityEdit', params: { activityId: activity.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(activity)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="messengerJHibsterApp.activity.delete.question"
          data-cy="activityDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-activity-heading" v-text="t$('messengerJHibsterApp.activity.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-activity"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeActivity()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./activity.component.ts"></script>
