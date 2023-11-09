<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="reaction">
        <h2 class="jh-entity-heading" data-cy="reactionDetailsHeading">
          <span v-text="t$('messengerJHibsterApp.reaction.detail.title')"></span> {{ reaction.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('messengerJHibsterApp.reaction.type')"></span>
          </dt>
          <dd>
            <span>{{ reaction.type }}</span>
          </dd>
          <dt>
            <span v-text="t$('messengerJHibsterApp.reaction.message')"></span>
          </dt>
          <dd>
            <div v-if="reaction.message">
              <router-link :to="{ name: 'MessageView', params: { messageId: reaction.message.id } }">{{ reaction.message.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="reaction.id" :to="{ name: 'ReactionEdit', params: { reactionId: reaction.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./reaction-details.component.ts"></script>
