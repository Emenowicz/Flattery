<template>
  <div id="app">
    <v-app id="inspire">
      <div class="header fixed-top">
        <b-navbar toggleable="md" type="light" variant="light">
          <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
          <b-navbar-brand href="/" class="ml-4"><p class="logo nav-font">{{ appName }}</p></b-navbar-brand>
          <b-collapse is-nav id="nav_collapse">

            <!-- Right aligned nav items -->
            <b-navbar-nav class="ml-auto" v-if="!isAuthenticated">
              <b-nav-item href="#" class="mr-4" v-on:click.prevent="open('login',$event)"><p
                class="navbar-link navbar-font">
                Zaloguj się</p></b-nav-item>
              <b-nav-item href="#" class="mr-4" v-on:click.prevent="open('register',$event)"><p
                class="navbar-link navbar-font">
                Zarejestruj się</p></b-nav-item>
            </b-navbar-nav>

            <b-navbar-nav class="ml-auto" v-else>
              <b-nav-item v-on:click="showUserAccount()" class="mr4 navbar-link navbar-font">{{ user.userName }}
              </b-nav-item>
              <b-nav-item href="#" class="mr-4" v-on:click="logout">
                <p class="navbar-link navbar-font">
                  Wyloguj się</p></b-nav-item>
            </b-navbar-nav>
          </b-collapse>
        </b-navbar>
      </div>
      <div class="text-center all main-section">
        <router-view
          :auth="isAuthenticated"
          :location="location"
          :user="user"
          :searchInput="search"
          v-on:setLocation="setGeoLocation"></router-view>
      </div>
      <div class="footer fixed-bottom">
        <!-- As a heading -->
        <b-navbar variant="light" type="light" class="page-footer m-0 p-0">
          <b-navbar-brand class="ml-2">
            <small>Flattery web app-brand &copy;</small>
          </b-navbar-brand>
        </b-navbar>
      </div>
      <div class="login-register-container" id="login-register" v-on:click.prevent="close">
        <div class="login-register-modal">
          <ul class="form-switcher">
            <li v-on:click.prevent="flip('login',$event)"><a id="login-form" href="#">Login</a></li>
            <li v-on:click.prevent="flip('register',$event)"><a id="register-form" href="#">Zarejestruj się</a></li>
          </ul>
          <div class="form-register" id="form-register">
            <!--Register confirmation if completed-->
            <div v-if="registrationCompleted">
              <v-container class="register-complete-container">
                <v-layout row justify-center>
                  <v-flex xs1>
                    <div>
                      <img src="/static/images/check-circle.png"/>
                    </div>
                  </v-flex>
                  <v-flex xs5 class="register-complete-text">
                    <div class="black--text h6">Zarejestrowano pomyślnie!</div>
                  </v-flex>
                </v-layout>
                <v-layout row justify-center>
                  <b-button class="btn btn-outline-secondary" v-on:click="closeLoginRegisterPopup">ZAMKNIJ</b-button>
                </v-layout>
              </v-container>
            </div>
            <!--Register form if registration is not completed yet-->
            <b-form v-else>
              <b-form-group>
                <b-form-input id="new-account-name" placeholder="Imię" v-model="firstName"></b-form-input>
                <span
                  v-if="$v.registerGroup.$dirty && $v.firstName.$invalid"
                  class="alert alert-danger">{{ firstNameErrorMessage }}</span>
              </b-form-group>

              <b-form-group>
                <b-form-input id="new-account-surname" placeholder="Nazwisko" v-model="lastName"></b-form-input>
                <span
                  v-if="$v.registerGroup.$dirty && $v.lastName.$invalid"
                  class="alert alert-danger">{{ lastNameErrorMessage }}</span>
              </b-form-group>

              <b-form-group>
                <b-form-input id="new-account-login" placeholder="Login" v-model="userName"></b-form-input>
                <span
                  v-if="$v.registerGroup.$dirty && $v.userName.$invalid"
                  class="alert alert-danger">{{ userNameErrorMessage }}</span>
              </b-form-group>

              <b-form-group>
                <b-form-input id="new-account-password" placeholder="Hasło" v-model="password" type="password"></b-form-input>
                <span
                  v-if="$v.registerGroup.$dirty && $v.password.$invalid"
                  class="alert alert-danger">{{ passwordErrorMessage }}</span>
              </b-form-group>

              <b-form-group>
                <b-form-input id="new-account-password-two" placeholder="Powtórz Hasło" v-model="confirmPassword"
                              type="password"></b-form-input>
                <span
                  v-if="$v.registerGroup.$dirty && $v.confirmPassword.$invalid"
                  class="alert alert-danger">{{ confirmPasswordErrorMessage }}</span>
              </b-form-group>

              <b-form-group>
                <b-form-input id="new-account-email" placeholder="Email" v-model="emailAddress"></b-form-input>
                <span
                  v-if="$v.registerGroup.$dirty && $v.emailAddress.$invalid"
                  class="alert alert-danger">{{ emailAddressErrorMessage }}</span>
              </b-form-group>
              <b-form-group>
                <div class="row">
                  <div class="col-sm-1">
                    <v-checkbox
                      id="new-account-terms"
                      v-model="terms"
                    ></v-checkbox>
                  </div>
                  <div class="col-sm-11">
                    <span>Oświadczam, że zapoznałem się z Regulaminem portalu Flattery.pl i akceptuję jego treść.</span>
                  </div>
                </div>
                <span
                  v-if="$v.registerGroup.$dirty && !terms"
                  class="alert alert-danger">{{ termsErrorMessage }}</span>
              </b-form-group>
              <b-button v-on:click="onRegister" type="submit" class="btn-block" variant="primary">Zarejestruj się
              </b-button>
            </b-form>
          </div>
          <div class="form-login" id="form-login">
            <b-form>
              <b-form-group>
                <b-form-input id="login-input" placeholder="Login" v-model="loginUserName"/>
                <span
                  v-if="$v.loginUserName.$dirty && $v.loginUserName.$error"
                  class="alert alert-danger">{{ loginUserNameErrorMessage }}</span>
              </b-form-group>
              <b-form-group>
                <b-form-input id="password-input" placeholder="Hasło" type="password" v-model="loginPassword"/>
                <span
                  v-if="$v.loginPassword.$dirty&& $v.loginPassword.$error"
                  class="alert alert-danger">{{ loginPasswordErrorMessage }}</span>
              </b-form-group>
              <span
                v-if="$v.loginGroup.$dirty && hasLoginError"
                class="alert alert-danger">{{ loginErrorMessage }}</span>
              <b-button type="submit" class="btn-block" variant="primary" v-on:click="onLogin">Zaloguj się</b-button>
            </b-form>
          </div>
          <div class="form-password" id="form-password"></div>
        </div>
      </div>
    </v-app>
  </div>
</template>

<script>
  // @formatter:off
  /* eslint-disable */
  // @formatter:on
  import 'vuetify/dist/vuetify.min.css'
  import 'vue-material-design-icons/styles.css'
  import axios from 'axios'
  import CheckCircleIcon from 'vue-material-design-icons/check-circle.vue'
  import {required, sameAs, minLength, maxLength, email} from 'vuelidate/lib/validators'
  import {helpers} from 'vuelidate/lib/validators'

  const passwordRegex = helpers.regex('passwordRegex', /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!,%*?&])[A-Za-z\d$@$!%*?&]{6,}/)

  export default {
    name: 'App',
    components: {
      CheckCircleIcon
    },
    computed: {
      firstNameErrorMessage() {
        if (!this.$v.firstName.required) {
          return 'Imię nie może być puste.'
        } else if (!this.$v.firstName.minLength) {
          return 'Imię jest za krókie. (Minimalna długość wynosi ' + this.$v.firstName.$params.minLength.min + ')';
        }
      },
      lastNameErrorMessage() {
        if (!this.$v.lastName.required) {
          return 'Nazwisko nie może być puste.'
        } else if (!this.$v.lastName.minLength) {
          return 'Nazwisko jest za krótkie. (Minimalna długość wynosi ' + this.$v.lastName.$params.minLength.min + ')';
        }
      },
      userNameErrorMessage() {
        if (!this.$v.userName.required) {
          return 'Login nie może być pusty.'
        } else if (!this.$v.userName.minLength) {
          return 'Login jest za krótki. (Minimalna długość wynosi ' + this.$v.userName.$params.minLength.min + ')';
        }
      },
      passwordErrorMessage() {
        if (!this.$v.password.required) {
          return 'Hasło nie może być puste.'
        } else if (!this.$v.password.minLength) {
          return 'Hasło jest za krótkie. (Minimalna długość wynosi ' + this.$v.password.$params.minLength.min + ')';
        } else if (!this.$v.password.passwordRegex) {
          return 'Hasło jest za łatwe. Powinno zawierać jedną małą literę, dużą literę, znak specjalny.';
        }
      },
      confirmPasswordErrorMessage() {
        if (!this.$v.confirmPassword.required) {
          return 'Hasło nie może być puste'
        } else if (!this.$v.confirmPassword.sameAsPassword) {
          return 'Podane hasła nie są identyczne';
        }
      },
      emailAddressErrorMessage() {
        if (!this.$v.emailAddress.required) {
          return 'Email nie może być pusty.'
        } else if (!this.$v.emailAddress.email) {
          return 'Podany email jest niepoprawny.';
        }
      },
      termsErrorMessage() {
        return 'Musisz zaakceptować regulamin.'
      },
      loginPasswordErrorMessage() {
        if (!this.$v.loginPassword.required) {
          return 'Hasło nie może być puste.'
        }
      },
      loginUserNameErrorMessage() {
        if (!this.$v.loginUserName.required) {
          return 'Login nie może być pusty.'
        }
      },
      loginErrorMessage() {
        return "Podane dane są niepoprawne."
      }
    },
    data() {
      return {
        appName: 'Flattery',
        active: null,
        status: 'not_accepted',
        user: null,
        isAuthenticated: false,
        registrationCompleted: false,
        positionLong: '',
        positionLat: '',
        location: '',
        //registration fields
        firstName: '',
        lastName: '',
        userName: '',
        password: '',
        confirmPassword: '',
        emailAddress: '',
        terms: false,
        //login fields
        loginUserName: '',
        loginPassword: '',
        hasLoginError: false
      }
    },
    methods: {
      open: function (which, e) {
        e.preventDefault();
        if (this.active !== null) {
          document.getElementById("form-" + this.active).classList.remove("active");
          document.getElementById(this.active + "-form").classList.remove("active");
        }
        document.getElementById("login-register").classList.add("active");
        document.getElementById("form-" + which).classList.add("active");
        document.getElementById(which + "-form").classList.add("active");

        this.active = which;
      },
      close: function (e) {
        e.preventDefault();
        let background = document.getElementById("login-register");
        if (e.target === background)
          background.classList.remove("active");
      },
      flip: function (which, e) {
        if (which !== this.active) {
          document.getElementById("form-" + this.active).classList.remove("active");
          document.getElementById("form-" + which).classList.add("active");
          document.getElementById(which + "-form").classList.add("active");
          document.getElementById(this.active + "-form").classList.remove("active");
        }
        this.active = which;
      },
      async checkIfAuthenticated() {
        try {
          await axios.get('http://127.0.0.1:8088/loggedUserData', {}).then(result => {
            console.log(result.data);
            this.user = result.data;
            this.location = this.user.location;
            this.isAuthenticated = true;
          })
        } catch (e) {
          this.isAuthenticated = false;
        }
      },
      async onLogin() {
        this.hasLoginError = false;
        this.$v.loginGroup.$touch();
        //if validated login process begins
        if (!this.$v.loginGroup.$invalid) {
          try {
            await axios.post('http://127.0.0.1:8088/login', {
              userName: this.loginUserName,
              password: this.loginPassword
            }).then(result => {
              this.checkIfAuthenticated();
              this.closeLoginRegisterPopup();
              console.log(result);
            });
          } catch (e) {
            console.log(e.message + '\nStatus: ' + e.status)
            this.hasLoginError = true;
          }
        }
      },
      async onRegister() {
        //validating fields
        this.$v.registerGroup.$touch();
        //if validated registration begins
        if (!this.$v.registerGroup.$invalid && this.terms) {
          try {
            await axios.post(`http://127.0.0.1:8088/register`, {
              firstName: this.firstName,
              lastName: this.lastName,
              userName: this.userName,
              password: this.password,
              confirmPassword: this.confirmPassword,
              emailAddress: this.emailAddress
            }).then(result => {
              this.registrationCompleted = true;
              console.log(result.status)
            })
          } catch (e) {
            console.log(e.message)
          }
        }
      },
      closeLoginRegisterPopup() {
        document.getElementById("login-register").classList.remove("active");
        //inside setTimeout function "this" is referring to something else
        let self = this;
        setTimeout(function () {
          self.firstName = self.lastName = self.userName = self.password = self.confirmPassword = self.emailAddress = '';
          self.registrationCompleted = false;
        }, 2000);
      },
      async setGeoLocation() {
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(position => {
            this.positionLat = position.coords.latitude;
            this.positionLong = position.coords.longitude;
            if (this.isAuthenticated)
              this.updateUserLocation();
          });
        }
      },
      async updateUserLocation() {
        let longDiff = Math.abs(this.user.latitude - this.positionLat);
        let latDiff = Math.abs(this.user.longitude - this.positionLong);
        if (longDiff > 0.1 || latDiff > 0.1) {
          try {
            await axios.put(`http://127.0.0.1:8088/saveUserLocation`, {
              longitude: this.positionLong,
              latitude: this.positionLat
            }).then(result => {
              this.location = this.user.location = result.data;
              this.user.longitude = this.positionLong;
              this.user.latitude = this.positionLat;
            })
          } catch (e) {
            console.log(e.message);
          }
        }
      },
      async showUserAccount() {
        this.$router.replace({path: '/account'});
      },
      async logout(){
        try{
          await axios.get('http://127.0.0.1:8088/out').then(() =>
            {
              this.isAuthenticated = !this.isAuthenticated;
            }
          )
        } catch(e) {
          console.log(e.message);
        }
      }
    },
    // is called onPageLoad
    mounted() {
      this.checkIfAuthenticated();
      // this.setGeoLocation();
    },
    validations: {
      firstName: {
        required, minLength: minLength(3), maxLength: maxLength(20)
      },
      lastName: {
        required, minLength: minLength(3), maxLength: maxLength(30)
      },
      userName: {
        required, minLength: minLength(4), maxLength: maxLength(20)
      },
      password: {
        required, minLength: minLength(6), maxLength: maxLength(20), passwordRegex
      },
      confirmPassword: {
        required, sameAsPassword: sameAs('password')
      },
      emailAddress: {
        required, email, maxLength: maxLength(70)
      },
      terms: {
        required
      },
      loginPassword: {
        required
      },
      loginUserName: {
        required
      },
      loginGroup: ['loginPassword', 'loginUserName'],
      registerGroup: ['firstName', 'lastName', 'userName', 'password', 'confirmPassword', 'emailAddress', 'terms']
    }
  }
</script>

<style>
  @import '../../../static/css/home.css';
  @import '../../../static/css/website.css';
  @import '../../../static/css/header.css';
</style>
