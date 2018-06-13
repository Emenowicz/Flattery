<script>
  /* eslint-disable */
  import 'vuetify/dist/vuetify.min.css'
  import axios from 'axios'
  import ShareIcon from 'vue-material-design-icons/share-variant.vue'
  import HeartOutlineIcon from 'vue-material-design-icons/heart-outline.vue'
  import HeartIcon from 'vue-material-design-icons/heart.vue'

  export default {
    name: 'ListView',
    data() {
      return {
        isLoaded: false,
        searchInput: '',
        fetchedOffers: [],
      }
    },
    props: {
      search: null,
      auth: null
    },
    methods: {
      async searchOffers() {
        try {
          await axios.post(`http://127.0.0.1:8088/offers`, {
            query: this.search,
            offerType: 'Room',
            roomType: "SinglePerson",
            minPrice: '700'
          }).then(result => {
            console.log(result.data);
            this.fetchedOffers = result.data;
            this.isLoaded = true
          })
        } catch (e) {
          console.log(e.message)
        }
      },
      async addToFavourite(offer) {
        try {
          await axios.put(`http://127.0.0.1:8088/addFavourite`, {
            offer: {
              title: offer.title,
              price: offer.price,
              city: offer.city,
              publishingDate: offer.publishingDate
            }
          }).then(result => {
            console.log(result.status)
          })
        } catch (e) {
          console.log(e.message)
        }
      },
      hideDiv: function(){
        let filterMenu = document.getElementById("filter-menu");
        if(filterMenu.classList.contains('hide')){
          filterMenu.classList.remove('hide');
          document.getElementById("filter-button").textContent = "Zamknij Filtry";

        } else {
          filterMenu.classList.add('hide');
          document.getElementById("filter-button").textContent = "Pokaż Filtry";
        }
      },
    },
    components: {
      ShareIcon, HeartOutlineIcon, HeartIcon
    },
    created: function () {
      this.searchOffers()
    }
  }
</script>
<template>
  <div class="container" id="container">
    <div class="f-container clearfix">
      <b-button id="filter-button" class="float-right" v-on:click="hideDiv">Pokaż Filtry</b-button>
      <div id="filter-menu" class="filter-menu hide">
        <b-row class="justify-content-md-center">
          <b-col col lg="2">
            <b-dropdown class="mx-1 type" right text="Typ">
              <b-dropdown-item>Pokój</b-dropdown-item>
              <b-dropdown-item>Mieszkanie</b-dropdown-item>
            </b-dropdown>
          </b-col>
          <b-col col lg="3">
            <b-dropdown class="mx-1 number-people" right text="Liczba osób">
              <b-dropdown-item>Jednoosobowy</b-dropdown-item>
              <b-dropdown-item>Dwuosobowy</b-dropdown-item>
              <b-dropdown-item>Trzyosobowy</b-dropdown-item>
            </b-dropdown>
          </b-col>
          <b-col col lg="4">
            <b-form>
              <b-form-group label="Cena od" class="price" horizontal>
                <b-input id="pricefrom" placeholder="1000 zł"/>
              </b-form-group>
              <b-form-group label="Cena do" class="price" horizontal>
                <b-input id="priceto" placeholder="2000 zł"/>
              </b-form-group>
            </b-form>
          </b-col>
          <b-col col lg="2"></b-col>
        </b-row>
        <b-row class="sec-row">
          <b-form inline>
            <b-form-group id="citySearch" label-size="lg" horizontal label="Miasto" label-for="cityInput">
              <b-form-input v-model="searchInput" id="cityInput" type="text" size="lg" placeholder="Wpisz miasto"
                            required>
              </b-form-input>
            </b-form-group>
            <b-col col sm="1">
              <b-form-group class="distance">
                <b-input id="distance" size="lg" placeholder="1 km"/>
              </b-form-group>
            </b-col>
          </b-form>
          <b-form-group class="date" label="Od" label-size="lg" horizontal>
            <b-input id="date" type="date" size="lg"/>
          </b-form-group>
        </b-row>

        <div style="text-align: right;">
          <b-button class="search-button" variant="success">Szukaj</b-button>
        </div>
      </div>
    </div>

    <ul id="listview" v-if="isLoaded">
      <li class="card" v-for="offer in fetchedOffers">
        <v-flex xs12>
          <v-card
            color="white"
            class="black--text">
            <v-container fluid grid-list-lg>
              <v-layout row>
                <v-flex xs3>
                  <v-card-media
                    v-bind:src="offer.photoUrl"
                    height="150px"
                    width="150px"
                    contain
                    class="card-image"
                  ></v-card-media>
                </v-flex>
                <v-flex xs7>
                  <v-card-title primary-title>
                    <div class="card-text">
                      <div v-text="offer.title" class="title"></div>
                      <div v-text="offer.city" class="grey--text"></div>
                    </div>

                  </v-card-title>

                  <v-card-actions>
                    <div>
                      <b-button v-bind:href="offer.urlToOffer" target="_blank" class="btn--flat lookButton">Zobacz
                      </b-button>
                      <b-button class="btn--flat btn--icon lookButton">
                        <ShareIcon/>
                      </b-button>
                      <b-button v-on:click="addToFavourite(offer)" class="btn--flat btn--icon favButton" v-if="auth">
                        <HeartIcon/>
                      </b-button>
                    </div>
                  </v-card-actions>
                </v-flex>
                <v-flex xs2>
                  <v-layout fill-height column justify-space-between>
                    <v-card-title primary-title>
                      <v-flex>
                        <div v-text="offer.price + ' zł'" class="text--primary title" style="font-weight: 500;"></div>
                      </v-flex>
                    </v-card-title>
                    <v-spacer></v-spacer>
                    <v-flex>
                      <div style="text-align: center" v-text="offer.publishingDate" class="text--primary subheading">
                        25.05.2018 10:00
                      </div>
                    </v-flex>
                  </v-layout>
                </v-flex>
              </v-layout>
            </v-container>
          </v-card>
        </v-flex>
      </li>
    </ul>
    <ul class="loading-circle" v-else>
      <v-card
        style="color: #757575;"
        raised
        light
        width="400px"
        height="250px">
        <v-card-title>
          <v-container fluid grid-list-lg>
            <v-layout row>
              <v-flex xs12>
                <v-progress-circular :size="100" :width="7" indeterminate></v-progress-circular>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-title>
        <h3 class="loading-title">Wyszukiwanie ofert...</h3>
      </v-card>
    </ul>
  </div>
</template>

<style>
  .container {
    display: inline;
  }

  .card {
    margin-top: 2px;
  }

  .card-text {
    text-align: left;
  }

  .card-image {
    background-color: #F5F5F5;
    align-content: center;
  }

  .loading-circle {
    position: fixed;
    top: 35%;
    left: 37%;
    z-index: 12;
    text-align: center;
  }

  .loading-title {
    margin-top: 18px;
  }

  .lookButton:hover {
    color: cornflowerblue;
  }

  .favButton:hover {
    color: darkred;
  }

  .f-container {
    background-color: lightgrey;
    /*opacity: 0.9;*/
    padding: 40px;
    margin-bottom: 30px;
    border-radius: 10px;
  }

  .price {
    text-align: left;
  }

  .sec-row {
    margin-top: 30px;
  }

  .date {
    margin-top: 16px;
  }

  .filter-menu {
    display: block;
  }

  .filter-menu.hide{
    display: none;
  }

  .search-button:hover {
   color: darkgreen;
  }

</style>
