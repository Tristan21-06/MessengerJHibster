<template>
  <div>
    <h2 id="page-heading" data-cy="MessageHeading">
      <span v-text="t$('messengerJHibsterApp.message.home.title')" id="message-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('messengerJHibsterApp.message.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MessageCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-message"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('messengerJHibsterApp.message.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && messages && messages.length === 0">
      <span v-text="t$('messengerJHibsterApp.message.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="messages && messages.length > 0">
      <table class="table table-striped" aria-describedby="messages">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('messengerJHibsterApp.message.texte')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="message in messages" :key="message.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MessageView', params: { messageId: message.id } }">{{ message.id }}</router-link>
            </td>
            <td>{{ message.texte }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MessageView', params: { messageId: message.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MessageEdit', params: { messageId: message.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(message)"
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
          id="messengerJHibsterApp.message.delete.question"
          data-cy="messageDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-message-heading" v-text="t$('messengerJHibsterApp.message.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-message"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMessage()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./message.component.ts"></script>
