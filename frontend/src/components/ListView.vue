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
        fetchedOffers: []
      }
    },
    methods: {
      async searchOffers() {
        try {
          await axios.post(`http://127.0.0.1:8088/offers`, {
            city: this.search,
            offerType: 'Room',
            roomType: 'SinglePerson',
            minPrice: '700',
            maxPrice: '2000'
          }).then(result => {
            console.log(result.data)
            this.fetchedOffers = result.data
            this.isLoaded = true
          })
        } catch (e) {
          console.log(e.message)
        }
      }
    },
    props: {
      search: null
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
                        <b-button v-bind:href="offer.urlToOffer" target="_blank" class="btn--flat">Zobacz</b-button>
                        <b-button class="btn--flat btn--icon">
                          <ShareIcon/>
                        </b-button>
                        <b-button class="btn--flat btn--icon">
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
                        <div style="text-align: center" v-text="offer.publishingDate" class="text--primary subheading">25.05.2018 10:00</div>
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
                    <v-progress-circular :size="100" :width="7" indeterminate ></v-progress-circular>
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
</style>
