<template>
  <div>
    <h2 id="page-heading" data-cy="ConversationHeading">
      <span v-text="t$('messengerJHibsterApp.conversation.home.title')" id="conversation-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('messengerJHibsterApp.conversation.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ConversationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-conversation"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('messengerJHibsterApp.conversation.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && conversations && conversations.length === 0">
      <span v-text="t$('messengerJHibsterApp.conversation.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="conversations && conversations.length > 0">
      <table class="table table-striped" aria-describedby="conversations">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('messengerJHibsterApp.conversation.name')"></span></th>
            <th scope="row"><span v-text="t$('messengerJHibsterApp.conversation.color')"></span></th>
            <th scope="row"><span v-text="t$('messengerJHibsterApp.conversation.users')"></span></th>
            <th scope="row"><span v-text="t$('messengerJHibsterApp.conversation.activities')"></span></th>
            <th scope="row"><span v-text="t$('messengerJHibsterApp.conversation.message')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="conversation in conversations" :key="conversation.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ConversationView', params: { conversationId: conversation.id } }">{{
                conversation.id
              }}</router-link>
            </td>
            <td>{{ conversation.name }}</td>
            <td>{{ conversation.color }}</td>
            <td>
              <span v-for="(users, i) in conversation.users" :key="users.id"
                >{{ i > 0 ? ', ' : '' }}
                {{ users.id }}
              </span>
            </td>
            <td>
              <span v-for="(activities, i) in conversation.activities" :key="activities.id"
                >{{ i > 0 ? ', ' : '' }}
                <router-link class="form-control-static" :to="{ name: 'ActivityView', params: { activityId: activities.id } }">{{
                  activities.id
                }}</router-link>
              </span>
            </td>
            <td>
              <div v-if="conversation.message">
                <router-link :to="{ name: 'MessageView', params: { messageId: conversation.message.id } }">{{
                  conversation.message.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ConversationView', params: { conversationId: conversation.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ConversationEdit', params: { conversationId: conversation.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(conversation)"
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
          id="messengerJHibsterApp.conversation.delete.question"
          data-cy="conversationDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-conversation-heading" v-text="t$('messengerJHibsterApp.conversation.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-conversation"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeConversation()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./conversation.component.ts"></script>
