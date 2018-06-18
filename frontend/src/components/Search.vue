<template>
  <b-container fluid>
    <div class="search-container">
      <h3 class="display-4">Znajdź swoje miejsce</h3>
      <div class="search-box mx-auto text-center w-75">
        <v-container fluid grid-list-lg>
          <v-layout row>
            <v-flex xs2>
              <v-text-field
                v-model="cityValue"
                label="Wpisz miasto"
                type="text"
                solo
              ></v-text-field>
            </v-flex>
            <v-flex xs10>
              <v-text-field
                v-model="searchValue"
                label="Szukaj Flattery.pl"
                type="text"
                solo
              ></v-text-field>
            </v-flex>
          </v-layout>
          <v-layout row>
            <v-flex>
              <div class="text-right">
                <v-btn
                  :pressed.sync="isLocationOn"
                  v-on:click="onLocationClick"
                  color="secondary"
                  class="filter-button"
                  :class="{toggled: isLocationOn}">
                  <div style="margin-bottom: 12px">
                    <v-icon>gps_fixed</v-icon>
                  </div>
                </v-btn>
                <v-btn
                  color="secondary"
                  v-on:click="routeToList">
                  <span style="padding-bottom: 12px">SZUKAJ</span>
                </v-btn>
              </div>
            </v-flex>
          </v-layout>
        </v-container>
      </div>
      <div class="filter-box mx-auto w-75">
        <v-expansion-panel class="text-center subheading">
          <v-expansion-panel-content class="transparent">
            <div slot="header">ROZWIŃ FILTRY</div>
            <v-container fluid grid-list-lg>
              <v-layout row>
                <v-flex xs1></v-flex>
                <v-flex xs2>
                  <span class="body filter-title">Cena:</span>
                  <v-text-field
                    v-model="minPriceValue"
                    label="Min. cena"
                    color="indigo darken-3"
                    type="number"
                  ></v-text-field>
                  <v-text-field
                    v-model="maxPriceValue"
                    label="Maks. cena"
                    color="indigo darken-3"
                    type="number"
                  ></v-text-field>
                </v-flex>
                <v-flex xs1></v-flex>
                <v-flex xs2>
                  <span class="body filter-title">:</span>
                  <v-text-field
                    v-model="daysOldValue"
                    type="number"
                    label="Dni od publikacji"
                    color="indigo darken-3"
                  ></v-text-field>
                  <v-select
                    :items="radiusTypes"
                    v-model="radiusValue"
                    label="Zasięg [km]"
                    autocomplete
                  ></v-select>
                </v-flex>
                <v-flex xs1></v-flex>
                <v-flex xs2>
                  <span class="body filter-title">Rodzaj:</span>
                  <v-radio-group v-model="houseTypeValue" :mandatory="false" :color="green">
                    <v-radio v-for="type in houseTypes"
                             v-bind:label="type.text"
                             v-bind:value="type.value"
                             color="indigo darken-3"></v-radio>
                  </v-radio-group>
                </v-flex>
                <v-flex xs3>
                  <span class="body filter-title" v-if="houseTypeValue === 'Room'">Wielkość pokoju:</span>
                  <v-radio-group v-model="roomTypeValue" :mandatory="false" v-if="houseTypeValue === 'Room'">
                    <v-radio v-for="roomType in roomTypes"
                             v-bind:label="roomType.text"
                             v-bind:value="roomType.value"
                             color="indigo darken-3"></v-radio>
                  </v-radio-group>
                </v-flex>
              </v-layout>
            </v-container>
          </v-expansion-panel-content>
        </v-expansion-panel>
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
        isLocationOn: false,
        searchValue: '',
        cityValue: '',
        //filters
        minPriceValue: '',
        maxPriceValue: '',
        daysOldValue: '',
        houseTypeValue: 'Flat',
        roomTypeValue: 'SinglePerson',
        radiusValue: '',
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
    methods: {
      routeToList: function (event) {
        this.$router.replace({
          path: '/offers', query: {
            city: this.cityValue,
            search: this.searchValue,
            minPrice: this.minPriceValue,
            maxPrice: this.maxPriceValue,
            radiusValue: this.radiusValue,
            daysOld: this.daysOldValue,
            offerType: this.houseTypeValue,
            roomType: this.roomTypeValue,
          }
        })
      },
      onLocationClick() {
        if (!this.isLocationOn) {
          if (this.location !== '') {
            this.searchValue = this.location;
          }
          this.$emit('setLocation');
        }
        this.isLocationOn = !this.isLocationOn;
      }
    },
    watch: {
      location: function (newVal, oldVal) {
        if (this.isLocationOn) {
          this.searchInput = newVal
        }
      }
    },created: function(){
        this.backButton = false;
    },
    props: {
      location: ''
    }
  }
</script>

<style>
  .search-box {
    margin-top: 40px;
    padding: 50px 80px 0px 30px;
    /*border-radius: 5px;*/
    background-color: rgba(230, 247, 255, .7);
  }

  .search-container {
    padding-top: 50px;
  }

  .filter-box {
    border-radius: 5px;
    background-color: rgba(230, 247, 255, .7)
  }

  .filter-title {
    padding-bottom: 10px;
  }
</style>
