<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="messengerJHibsterApp.conversation.home.createOrEditLabel"
          data-cy="ConversationCreateUpdateHeading"
          v-text="t$('messengerJHibsterApp.conversation.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="conversation.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="conversation.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('messengerJHibsterApp.conversation.name')" for="conversation-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="conversation-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('messengerJHibsterApp.conversation.color')" for="conversation-color"></label>
            <input
              type="text"
              class="form-control"
              name="color"
              id="conversation-color"
              data-cy="color"
              :class="{ valid: !v$.color.$invalid, invalid: v$.color.$invalid }"
              v-model="v$.color.$model"
            />
          </div>
          <div class="form-group">
            <label v-text="t$('messengerJHibsterApp.conversation.users')" for="conversation-users"></label>
            <select
              class="form-control"
              id="conversation-users"
              data-cy="users"
              multiple
              name="users"
              v-if="conversation.users !== undefined"
              v-model="conversation.users"
            >
              <option v-bind:value="getSelected(conversation.users, userOption)" v-for="userOption in users" :key="userOption.id">
                {{ userOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="t$('messengerJHibsterApp.conversation.activities')" for="conversation-activities"></label>
            <select
              class="form-control"
              id="conversation-activities"
              data-cy="activities"
              multiple
              name="activities"
              v-if="conversation.activities !== undefined"
              v-model="conversation.activities"
            >
              <option
                v-bind:value="getSelected(conversation.activities, activityOption)"
                v-for="activityOption in activities"
                :key="activityOption.id"
              >
                {{ activityOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('messengerJHibsterApp.conversation.message')" for="conversation-message"></label>
            <select class="form-control" id="conversation-message" data-cy="message" name="message" v-model="conversation.message">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="conversation.message && messageOption.id === conversation.message.id ? conversation.message : messageOption"
                v-for="messageOption in messages"
                :key="messageOption.id"
              >
                {{ messageOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./conversation-update.component.ts"></script>
