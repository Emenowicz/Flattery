<template>
  <b-container class="mx-auto w-75 main-container" v-if="redirectIfNotAuth">
    <h1 class="display-3">Edytuj swoje dane</h1>
    <div class="profile-container clearfix p-4">
      <b-form class="form-container">
        <b-form-group horizontal :label-cols="2" label-for="userName" label="Imię" class="labels">
          <b-form-input id="userName" v-model="username" type="text" :placeholder="user.firstName"
                        size="lg"></b-form-input>
        </b-form-group>
        <b-form-group horizontal :label-cols="2" label-for="userSurname" label="Nazwisko" class="labels">
          <b-form-input id="userSurname" v-model="userSurname" type="text" :placeholder="user.lastName"
                        size="lg"></b-form-input>
        </b-form-group>
        <b-form-group horizontal :label-cols="2" label-for="userPassword" label="Hasło" class="labels">
          <b-form-input id="userPassword" v-model="userPassword" type="password" size="lg"></b-form-input>
        </b-form-group>
        <b-form-group horizontal :label-cols="2" label-for="userConfirmPassword" label="Powtórz hasło" class="labels">
          <b-form-input id="userConfirmPassword" v-model="userPasswordRepeat" type="password" size="lg"></b-form-input>
        </b-form-group>
        <b-form-group horizontal :label-cols="2" label-for="userEmail" label="E-mail" class="labels">
          <b-form-input id="userEmail" v-model="userEmailAddress" type="email"
                        :placeholder="user.emailAddress" size="lg"></b-form-input>
        </b-form-group>
        <b-button variant="success" v-on:click="changeUserData()" size="md" class="confirm-btn" style="background-color: white;">
          Zatwierdź
        </b-button>
      </b-form>
    </div>
  </b-container>
</template>

<script>
  /* eslint-disable */

  import axios from 'axios'
  import {sameAs, minLength, maxLength, email} from 'vuelidate/lib/validators'
  import {helpers} from 'vuelidate/lib/validators'

  const passwordRegex = helpers.regex('passwordRegex', /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!,%*?&])[A-Za-z\d$@$!%*?&]{6,}/)

  export default {
    name: "UserProfile",
    data() {
      return {
        username: '',
        userSurname: '',
        userPassword: '',
        userPasswordRepeat: '',
        userEmailAddress: '',
        newPass: ''
      }
    },
    props: {
      user: null
    },
    methods: {
      changeUserData: function () {
        this.$v.userDataChange.$touch();
        if (!this.$v.userDataChange.$invalid) {
          if (this.username !== '')
            this.user.firstName = this.username;

          if (this.userSurname !== '')
            this.user.lastName = this.userSurname;

          if (this.userEmailAddress !== '') {
            this.user.emailAddress = this.userEmailAddress;
          }

          if (this.userPassword !== '' && this.userPassword === this.userPasswordRepeat) {
            this.newPass = this.userPassword;
          }
          axios.post("http://127.0.0.1:8088/changeUserData", {user: this.user, password: this.newPass});
        }
      }
    }
    ,
    validations: {
      username: {
        minLength: minLength(3), maxLength:
          maxLength(20)
      }
      ,
      userSurname: {
        minLength: minLength(3), maxLength:
          maxLength(30)
      }
      ,
      userPassword: {
        minLength: minLength(6), maxLength:
          maxLength(20), passwordRegex
      }
      ,
      userPasswordRepeat: {
        sameAsPassword: sameAs('userPassword')
      }
      ,
      userEmailAddress: {
        email, maxLength:
          maxLength(70)
      }
      ,
      userDataChange: ['username', 'userSurname', 'userPassword', 'userPasswordRepeat', 'userEmailAddress'],
    },
    computed: {
      redirectIfNotAuth: function() {
        if(this.user === null){
          this.$router.replace("/");
          return false;
        }
        return true;
      }
    }
  }


</script>

<style>

  .profile-container {
    background-color: rgba(230, 247, 255, .7);
    border-radius: 10px;
    margin-top: 16px;
  }

  .labels {
    font-size: 18px;
    text-align: left;
    margin-left: 30px;
  }

  .confirm-btn:hover {
    color: darkgreen;
  }

  .main-container {
    margin-top: 0;
  }

  .form-container {
    padding: 10px;
  }
</style>
