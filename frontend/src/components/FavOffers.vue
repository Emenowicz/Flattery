<template>
  <b-container class="margin-fix" v-if="redirectIfNotAuth" >
    <ul id="listview" v-if="emptyList">
      <li class="card" v-for="offer in favOffers" >
        <v-flex xs12>
          <v-card
            color="white"
            class="black--text">
            <v-container fluid grid-list-lg>
              <v-layout row>
                <v-flex xs3>
                  <v-card-media
                    :src="offer.offer.photoUrl"
                    height="150px"
                    width="150px"
                    contain
                    class="card-image"
                  ></v-card-media>
                </v-flex>
                <v-flex xs7>
                  <v-card-title primary-title>
                    <div class="card-text">
                      <div class="title" v-text="offer.offer.title">abc</div>
                      <div class="grey--text" v-text="offer.offer.city"></div>
                    </div>

                  </v-card-title>

                  <v-card-actions>
                    <div>
                      <b-button :href="offer.offer.urlToOffer" target="_blank" class="btn--flat lookButton">Zobacz
                      </b-button>
                      <b-button class="btn--flat btn--icon lookButton">
                        <ShareIcon/>
                      </b-button>
                      <b-button v-on:click="removeFromFavourites(offer)" class="btn--flat btn--icon favButton">
                        <HeartIcon/>
                      </b-button>
                    </div>
                  </v-card-actions>
                </v-flex>
                <v-flex xs2>
                  <v-layout fill-height column justify-space-between>
                    <v-card-title primary-title>
                      <v-flex>
                        <div class="text--primary title" style="font-weight: 500;">{{ offer.offer.price }} zł</div>
                      </v-flex>
                    </v-card-title>
                    <v-spacer></v-spacer>
                    <v-flex>
                      <div style="text-align: center" class="text--primary subheading"
                           v-text="offer.offer.publishingDate">
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
    <div v-else>
      <v-card
        class="container-radius"
        raised
        light>
        <h3 class="loading-title display-1 redtext">Nie posiadasz ulubionych ofert!</h3>
      </v-card>
    </div>

  </b-container>
</template>

<script>
  import 'vuetify/dist/vuetify.min.css'
  import axios from 'axios'
  import ShareIcon from 'vue-material-design-icons/share-variant.vue'
  import HeartOutlineIcon from 'vue-material-design-icons/heart-outline.vue'
  import HeartIcon from 'vue-material-design-icons/heart.vue'

  export default {
    name: "FavOffers",
    props: {
      user: null,
      favOffers: []
    },
    components: {
      ShareIcon, HeartOutlineIcon, HeartIcon
    },
    methods: {
      removeFromFavourites(currentOffer) {
        axios.post('http://127.0.0.1:8088/removeFavouriteOffer', {
            offer: currentOffer.offer
          }
        ).then(e => this.updateFavouriteList());
      },
      updateFavouriteList(){
        axios.get('http://127.0.0.1:8088/getUsersFavourites').then(result => {
          this.favOffers = result.data;
          }
        );
      }
    },

    computed: {
      redirectIfNotAuth: function () {
        if (this.user === null) {
          this.$router.replace("/");
          return false;
        }
        return true;
      },
      emptyList() {
        return this.favOffers.length !== 0;
      }
    }
  }
</script>

<style scoped>
  #listview {
    position: center;
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

  .margin-fix {
    margin-top: 0
  }

  .lookButton:hover {
    color: cornflowerblue;
  }

  .favButton {
    color: darkred;
    transition: linear 0.4s;
    border-color: darkred;
  }

  .favButton:hover {
    color: black;
    transition: linear 0.4s;
    border-color: black;
  }


</style>
