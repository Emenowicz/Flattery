<template>
  <div id="app">
    <div>
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

    <h2>{{msg}}</h2>
    <router-view></router-view>
    <h2>Powyzej routerview</h2>
    <div>
      <div>
        <b-navbar variant="light" type="light" class="page-footer m-0 p-0">
          <b-navbar-brand class="ml-2"><small>Flattery web app-brand &copy;</small></b-navbar-brand>
        </b-navbar>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
  data () {
    return {
      msg: 'Poniżej router-view',
      appName: 'Flattery'
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

</style>
