<template>
  <b-container class="margin-fix" v-if="redirectIfNotAuth">
    <ul id="listview">
      <li class="card">
        <v-flex xs12>
          <v-card
            color="white"
            class="black--text">
            <v-container fluid grid-list-lg>
              <v-layout row>
                <v-flex xs3>
                  <v-card-media
                    height="150px"
                    width="150px"
                    contain
                    class="card-image"
                  ></v-card-media>
                </v-flex>
                <v-flex xs7>
                  <v-card-title primary-title>
                    <div class="card-text">
                      <div class="title">Spoko mieszkania</div>
                      <div class="grey--text">Lask</div>
                    </div>

                  </v-card-title>

                  <v-card-actions>
                    <div>
                      <b-button target="_blank" class="btn--flat lookButton">Zobacz
                      </b-button>
                      <b-button class="btn--flat btn--icon lookButton">
                        <ShareIcon/>
                      </b-button>
                      <b-button class="btn--flat btn--icon favButton">
                        <HeartIcon/>
                      </b-button>
                    </div>
                  </v-card-actions>
                </v-flex>
                <v-flex xs2>
                  <v-layout fill-height column justify-space-between>
                    <v-card-title primary-title>
                      <v-flex>
                        <div class="text--primary title" style="font-weight: 500;">500 zł</div>
                      </v-flex>
                    </v-card-title>
                    <v-spacer></v-spacer>
                    <v-flex>
                      <div style="text-align: center" class="text--primary subheading">
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
      user: null
    },
    components: {
      ShareIcon, HeartOutlineIcon, HeartIcon
    }, computed: {
      redirectIfNotAuth: function(){
        if(this.user === null){
          this.$router.replace("/");
          return false;
        }
        return true;
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
  .margin-fix{
    margin-top: 0
  }
  .lookButton:hover {
    color: cornflowerblue;
  }
  .favButton {
    color:darkred;
    transition: linear 0.4s;
    border-color: darkred;
  }

  .favButton:hover {
    color: black;
    transition: linear 0.4s;
    border-color: black;
  }


</style>
