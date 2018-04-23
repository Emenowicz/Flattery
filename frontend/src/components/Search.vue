<template xmlns:th="http://www.w3.org/1999/xhtml">
  <div>
    <div class="text-center all main-section">
      <b-container>
        <h1 class="display-4">Znajdź swoje miejsce</h1>
        <div class="search-box mx-auto text-center w-75">
          <b-form>
            <b-form-group id="citySearch" horizontal label="Miasto" label-for="cityInput">
              <b-form-input v-model="searchInput" id="cityInput" class="col-xs-2" type="text" size="lg"
                            placeholder="Wpisz miasto"
                            required></b-form-input>
            </b-form-group>
            <div class="h-40 d-inline-block"></div>
            <div class="text-right">
              <b-button th:type="submit" v-on:click="routeToList" size="lg" variant="primary">Szukaj</b-button>
            </div>
          </b-form>
        </div>
      </b-container>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
  export default {
    name: 'Search',
    data() {
      return {
        searchInput: '',
        }},
        methods: {
            routeToList: function (event) {
            this.$router.replace({ path:'/recyclerview', query: { search: this.searchInput}})
            }
        }
  }
</script>

<style>
  @import '../../static/css/home.css';
  @import '../../static/css/website.css';
</style>
