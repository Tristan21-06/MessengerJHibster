<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="message">
        <h2 class="jh-entity-heading" data-cy="messageDetailsHeading">
          <span v-text="t$('messengerJHibsterApp.message.detail.title')"></span> {{ message.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('messengerJHibsterApp.message.texte')"></span>
          </dt>
          <dd>
            <span>{{ message.texte }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="message.id" :to="{ name: 'MessageEdit', params: { messageId: message.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./message-details.component.ts"></script>
