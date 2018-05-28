<template>
      <b-container fluid>
        <div class="search-container">
          <h3 class="display-4">Znajdź swoje miejsce</h3>
          <div class="search-box mx-auto text-center w-75">
            <b-form>
              <b-form-group id="citySearch" label-size="lg" horizontal label="Miasto" label-for="cityInput">
                <b-form-input v-model="searchInput" id="cityInput" class="col-xs-2" type="text" size="lg"
                              placeholder="Wpisz miasto"
                              required></b-form-input>
              </b-form-group>
              <div class="h-40 d-inline-block"></div>
              <div class="text-right">
                <b-button type="submit" v-on:click="routeToList" size="lg" variant="primary">Szukaj</b-button>
              </div>
            </b-form>
          </div>
        </div>
      </b-container>
</template>

<script>
/* eslint-disable */
  export default {
    name: 'Search',
    data() {
      return {
        searchInput: '',
      }
    },
    methods: {
      routeToList: function (event) {
        this.$router.replace({path: '/offers', query: {search: this.searchInput}})
      }
    }
  }
</script>

<style>
  .search-box{
    margin-top: 40px;
    padding: 50px 80px 30px 30px;
    margin-bottom: 100px;
    border-radius: 5px;
    background-color: rgba(230, 247, 255, .7);
  }

  .search-container {
    padding-top: 100px;
  }

</style>
