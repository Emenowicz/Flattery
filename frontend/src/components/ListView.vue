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
        minPrice: '',
        maxPrice: '',
        daysSinceToday: '',
        chosenDate: '',
        radiusFromLocation: '5',
        houseTypeDropdownSelection: 'Flat',
        roomTypeDropdownSelection: 'SinglePerson',
        roomDropdownDisable: true,
        roomTypes: [
          {
            value: "SinglePerson",
            text: "Jednoosobowy"
          },
          {
            value: "TwoPeople",
            text: "Dwuosobowy"
          },
          {
            value: "ThreeOrMorePeople",
            text: "Trzyosobowy lub więcej"
          }
        ],
        houseTypes: [
          {
            value: "Flat",
            text: "Mieszkanie"
          },
          {
            value: "Room",
            text: "Pokój"
          }, {
            value: "House",
            text: "Dom"
          }],
        radiusTypes: [
          '0', '2', '5', '10', '15', '30', '50', '75', '100'
        ]
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
            offerType: this.houseTypeDropdownSelection,
            roomType: this.roomTypeDropdownSelection,
            minPrice: this.minPrice,
            maxPrice: this.maxPrice,
            offerDaysOld: this.offerDaysOld,
            radiusFromLocation: this.radiusFromLocation
          }).then(result => {
            console.log(result.data);
            this.fetchedOffers = result.data;
            this.isLoaded = true;
            document.getElementById("filter-container").hidden = false;

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
      hideDiv: function () {
        let filterMenu = document.getElementById("filter-menu");
        if (filterMenu.classList.contains('hide')) {
          filterMenu.classList.remove('hide');
        } else {
          filterMenu.classList.add('hide');
          document.getElementById("filter-button").textContent = "Pokaż Filtry";
        }
      },
      setHouseType(arg) {
        this.houseTypeDropdownSelection = arg;
        this.roomDropdownDisable = arg !== 'Room';
        if (this.roomDropdownDisable === true) {
          this.roomTypeDropdownSelection = 'SinglePerson';
        }
      },
      setOfferDaysOld() {
        let currentDate = new Date();
        let chosenDate = new Date(this.chosenDate);
        let timeDifference = Math.abs(currentDate.getTime() - chosenDate.getTime());
        this.daysSinceToday = Math.ceil(timeDifference / (1000 * 3600 * 24));
      },
      setRoomType(arg) {
        this.roomTypeDropdownSelection = arg;
        document.getElementById("room-type").text(arg);
      },
      checkFiltersAndSubmit() {
        this.searchInput = this.search;
        if (this.searchInput === '') {
          document.getElementById("errorParagraph").hidden = false;
        } else {
          this.searchOffers();
          this.isLoaded = false;
          document.getElementById("errorParagraph").hidden = true;
          document.getElementById("filter-container").hidden = true;
        }
      },
      setRadiusType(arg) {
        this.radiusFromLocation = arg;
      }
    },
    components: {
      ShareIcon, HeartOutlineIcon, HeartIcon
    },
    computed: {
      isListViewEmpty: function() {
        return this.fetchedOffers.length === 0;
      }
    },
    created: function () {
      this.searchOffers()
    }
  }
</script>
<template>
  <div class="container" id="container">
    <div id="filter-container" class="f-container container-radius clearfix" hidden>
      <b-button id="filter-button" class="float-right" v-on:click="hideDiv">Pokaż Filtry</b-button>
      <div id="filter-menu" class="filter-menu hide">
        <b-row class="justify-content-md-center">
          <b-col col lg="2">
            <b-dropdown id="home-type" class="mx-1" right text="Czego szukasz?" v-b-tooltip.hover
                        title="Domyślnie: Mieszkanie">
              <b-dropdown-item disabled value="0">Czego szukasz?</b-dropdown-item>
              <b-dropdown-item v-for="houseType in houseTypes" :value="houseType.text"
                               v-model="houseTypeDropdownSelection"
                               v-on:click="setHouseType(houseType.value)"> {{ houseType.text }}
              </b-dropdown-item>
            </b-dropdown>
          </b-col>
          <b-col col lg="3">
            <b-dropdown id="room-type" class="mx-1" right text="Liczba osób"
                        v-bind:disabled="roomDropdownDisable" v-b-tooltip.hover title="Domyślnie: Jednoosobowe">
              <b-dropdown-item disabled value="0">Ile osób?</b-dropdown-item>
              <b-dropdown-item v-for="roomType in roomTypes" v-model="roomTypeDropdownSelection"
                               v-on:click="setRoomType(roomType.value)">{{roomType.text}}
              </b-dropdown-item>
            </b-dropdown>
          </b-col>
          <b-col col lg="4">
            <b-form>
              <b-form-group label="Cena od" class="price" horizontal>
                <b-input v-model="minPrice" id="pricefrom" placeholder="1000 zł"></b-input>
              </b-form-group>
              <b-form-group label="Cena do" class="price" horizontal>
                <b-input v-model="maxPrice" id="priceto" placeholder="2000 zł"></b-input>
              </b-form-group>
            </b-form>
          </b-col>
          <b-col col lg="2"></b-col>
        </b-row>
        <b-row class="sec-row">
          <b-form inline>
            <b-form-group id="citySearch" label-size="lg" horizontal label="Miasto" label-for="cityInput">
              <b-form-input v-model="search" id="cityInput" type="text" size="lg" placeholder="Wpisz miasto"
                            required>
              </b-form-input>
            </b-form-group>
            <b-col col sm="1">
              <b-dropdown id="radius-type" class="mx-1" right text="Odległość"
                          v-b-tooltip.hover title="Domyślnie: 5 km">
                <b-dropdown-item disabled value="0">Wybierz promień?</b-dropdown-item>
                <b-dropdown-item v-for="radiusType in radiusTypes" v-on:click="setRadiusType(radiusType)">{{ radiusType
                  }} km
                </b-dropdown-item>
              </b-dropdown>
            </b-col>
          </b-form>
          <b-form-group class="date" label="Od" label-size="lg" horizontal>
            <b-input id="date" v-model="chosenDate" v-on:change="setOfferDaysOld" type="date" size="lg"></b-input>
          </b-form-group>
        </b-row>

        <div style="text-align: right;">
          <b-button class="search-button" variant="success" v-on:click="checkFiltersAndSubmit">Szukaj</b-button>
        </div>
        <p id="errorParagraph" class="redtext" hidden>Proszę wpisz miasto!</p>
      </div>
    </div>

    <ul id="listview" v-if="isLoaded">
      <div v-if="!isListViewEmpty">
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
                        </div>
                      </v-flex>
                    </v-layout>
                  </v-flex>
                </v-layout>
              </v-container>
            </v-card>
          </v-flex>
        </li>
      </div>
      <div v-else>
        <v-card
          class="container-radius"
          raised
          light>
          <h3 class="loading-title display-1 redtext">Niestety nie znaleźliśmy ofert spełniających warunki filtrów szukających. Proszę spróbuj ponownie!</h3>
        </v-card>
      </div>
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
    /*background-color: lightgrey;*/
    /*opacity: 0.9;*/
    padding: 40px;
    margin-bottom: 30px;
    background-color: rgba(230, 247, 255, .7);
  }

  .container-radius {
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

  .filter-menu.hide {
    display: none;
  }

  .search-button:hover {
    color: darkgreen;
  }

  .redtext {
    color: red;
  }
</style>
