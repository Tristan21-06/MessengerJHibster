<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="conversation">
        <h2 class="jh-entity-heading" data-cy="conversationDetailsHeading">
          <span v-text="t$('messengerJHibsterApp.conversation.detail.title')"></span> {{ conversation.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('messengerJHibsterApp.conversation.name')"></span>
          </dt>
          <dd>
            <span>{{ conversation.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('messengerJHibsterApp.conversation.color')"></span>
          </dt>
          <dd>
            <span>{{ conversation.color }}</span>
          </dd>
          <dt>
            <span v-text="t$('messengerJHibsterApp.conversation.users')"></span>
          </dt>
          <dd>
            <span v-for="(users, i) in conversation.users" :key="users.id"
              >{{ i > 0 ? ', ' : '' }}
              {{ users.id }}
            </span>
          </dd>
          <dt>
            <span v-text="t$('messengerJHibsterApp.conversation.message')"></span>
          </dt>
          <dd>
            <div v-if="conversation.message">
              <router-link :to="{ name: 'MessageView', params: { messageId: conversation.message.id } }">{{
                conversation.message.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="conversation.id"
          :to="{ name: 'ConversationEdit', params: { conversationId: conversation.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./conversation-details.component.ts"></script>
