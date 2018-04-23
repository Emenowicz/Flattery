<template>
  <div id="app">
    <div class="header fixed-top">
      <b-navbar toggleable="md" type="light" variant="light">
        <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
        <b-navbar-brand href="#" class="ml-4"><p class="logo nav-font">{{ appName }}</p></b-navbar-brand>
        <b-collapse is-nav id="nav_collapse">
          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item href="#" class="mr-4"><p class="navbar-link navbar-font">Zaloguj się</p></b-nav-item>
            <b-nav-item href="#" class="mr-4"><p class="navbar-link navbar-font">Zarejestruj się</p></b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>

    <div class="text-center all main-section">
      <router-view></router-view>
    </div>

    <div class="footer fixed-bottom">
      <!-- As a heading -->
      <b-navbar variant="light" type="light" class="page-footer m-0 p-0">
        <b-navbar-brand class="ml-2">
          <small>Flattery web app-brand &copy;</small>
        </b-navbar-brand>
      </b-navbar>
    </div>
  </div>


</template>

<script>
  export default {
    name: 'App',
    data() {
      return {
        msg: 'Poniżej router-view',
        appName: 'Flattery'
      }
    }
  }
</script>

<style>

  @import '../../../static/css/home.css';
  @import '../../../static/css/website.css';
  @import '../../../static/css/header.css';

  .search-boxing {

  }


</style>
