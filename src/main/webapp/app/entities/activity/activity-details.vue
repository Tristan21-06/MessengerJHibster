<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="activity">
        <h2 class="jh-entity-heading" data-cy="activityDetailsHeading">
          <span v-text="t$('messengerJHibsterApp.activity.detail.title')"></span> {{ activity.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('messengerJHibsterApp.activity.imageActivity')"></span>
          </dt>
          <dd>
            <span>{{ activity.imageActivity }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="activity.id" :to="{ name: 'ActivityEdit', params: { activityId: activity.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./activity-details.component.ts"></script>
