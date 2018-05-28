<template>
  <div id="app">
    <div class="header fixed-top">
      <b-navbar toggleable="md" type="light" variant="light">
        <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
        <b-navbar-brand href="#" class="ml-4"><p class="logo nav-font">{{ appName }}</p></b-navbar-brand>
        <b-collapse is-nav id="nav_collapse">
          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item href="#" class="mr-4" v-on:click.prevent="open('login',$event)"><p
              class="navbar-link navbar-font">
              Zaloguj się</p></b-nav-item>
            <b-nav-item href="#" class="mr-4" v-on:click.prevent="open('register',$event)"><p
              class="navbar-link navbar-font">
              Zarejestruj się</p></b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
    <div class="text-center all main-section">
      <router-view></router-view>
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
          <li v-on:click.prevent="flip('register',$event)"><a id="register-form" href="#">Register</a></li>
        </ul>
        <div class="form-register" id="form-register">
          <b-form>
            <b-form-group>
              <b-form-input id="new-account-name" placeholder="Imię" required>
              </b-form-input>
            </b-form-group>
            <b-form-group>
              <b-form-input id="new-account-surname" placeholder="Nazwisko" required>
              </b-form-input>
            </b-form-group>
            <b-form-group>
              <b-form-input id="new-account-login" placeholder="Login" required>
              </b-form-input>
            </b-form-group>
            <b-form-group>
              <b-form-input id="new-account-password" placeholder="Hasło" required>
              </b-form-input>
            </b-form-group>
            <b-form-group>
              <b-form-input id="new-account-password-two" placeholder="Powtórz Hasło" required>
              </b-form-input>
            </b-form-group>
            <b-form-group>
              <b-form-input id="new-account-name" placeholder="Email" required>
              </b-form-input>
            </b-form-group>
            <b-form-group>
              <div>Rejestrując się akceptujesz <a href="#" v-b-modal.modal1>Regulamin</a></div>
              <b-modal id="modal1">
                Regulamin
              </b-modal>
            </b-form-group>
            <b-button type="submit" class="btn-block" variant="primary">Zarejestruj się</b-button>
          </b-form>
        </div>
        <div class="form-login" id="form-login">
          <b-form>
            <b-form-group>
              <b-form-input id="login-input" placeholder="Użytkownik" v-model="userName" required>
              </b-form-input>
            </b-form-group>
            <b-form-group>
              <b-form-input id="password-input" placeholder="Hasło" type="password" v-model="password" required>
              </b-form-input>
            </b-form-group>
            <div>
            </div>
            <b-button @click="submit('login')" type="submit" class="btn-block" variant="primary">Login</b-button>
          </b-form>
        </div>
        <div class="form-password" id="form-password"></div>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import axios from 'axios';

  export default {
    name: 'App',
    data() {
      return {
        appName: 'Flattery',
        active: null,
        status: 'not_accepted',

        // login-data
        userName: '',
        password: ''
      }
    }, methods: {
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
      submit: function (which, e) {
        axios.get('/user').then(res => console.log(res));
        // axios.get('/test').then(res => console.log(res)).catch(error => console.log(error.response));
        // let data = {form: which};
        // switch(which){
        //   case 'login':
        //     data.userName = this.userName;
        //     data.password = this.password;
        //     axios.post('/login', {
        //       userName: this.userName,
        //       password: this.password
        //     }).then(res => {
        //       console.log(res);
        //
        //     }).catch(error => console.log(error.response));
        // }
      }
    }
  }
</script>

<style>
  @import '../../../static/css/home.css';
  @import '../../../static/css/website.css';
  @import '../../../static/css/header.css';
</style>
