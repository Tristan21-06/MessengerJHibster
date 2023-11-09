<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="messengerJHibsterApp.reaction.home.createOrEditLabel"
          data-cy="ReactionCreateUpdateHeading"
          v-text="t$('messengerJHibsterApp.reaction.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="reaction.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="reaction.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('messengerJHibsterApp.reaction.type')" for="reaction-type"></label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !v$.type.$invalid, invalid: v$.type.$invalid }"
              v-model="v$.type.$model"
              id="reaction-type"
              data-cy="type"
            >
              <option
                v-for="reactionType in reactionTypeValues"
                :key="reactionType"
                v-bind:value="reactionType"
                v-bind:label="t$('messengerJHibsterApp.ReactionType.' + reactionType)"
              >
                {{ reactionType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('messengerJHibsterApp.reaction.message')" for="reaction-message"></label>
            <select class="form-control" id="reaction-message" data-cy="message" name="message" v-model="reaction.message">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="reaction.message && messageOption.id === reaction.message.id ? reaction.message : messageOption"
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
<script lang="ts" src="./reaction-update.component.ts"></script>
