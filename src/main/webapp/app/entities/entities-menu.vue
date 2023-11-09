<template>
  <div>
    <b-dropdown-item to="/message">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.message')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/activity">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.activity')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/reaction">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.reaction')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/conversation">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.conversation')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
