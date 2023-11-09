<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="messengerJHibsterApp.activity.home.createOrEditLabel"
          data-cy="ActivityCreateUpdateHeading"
          v-text="t$('messengerJHibsterApp.activity.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="activity.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="activity.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('messengerJHibsterApp.activity.imageAcivity')" for="activity-imageAcivity"></label>
            <input
              type="text"
              class="form-control"
              name="imageAcivity"
              id="activity-imageAcivity"
              data-cy="imageAcivity"
              :class="{ valid: !v$.imageAcivity.$invalid, invalid: v$.imageAcivity.$invalid }"
              v-model="v$.imageAcivity.$model"
            />
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
<script lang="ts" src="./activity-update.component.ts"></script>
