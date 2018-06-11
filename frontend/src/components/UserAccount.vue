<template>
  <b-container class="mx-auto w-75">
    <h1 class="display-3"></h1>
    <div class="profile-container clearfix p-4">
      <b-form>
        <b-form-group horizontal :label-cols="2" label-for="userName" label="Imię">
          <b-form-input id="userName" v-model="username" type="text" :placeholder="user.firstName"></b-form-input>
        </b-form-group>
        <b-form-group horizontal :label-cols="2" label-for="userSurname" label="Nazwisko">
          <b-form-input id="userSurname" v-model="userSurname" type="text" :placeholder="user.lastName"></b-form-input>
        </b-form-group>
        <b-form-group horizontal :label-cols="2" label-for="userPassword" label="Hasło">
          <b-form-input id="userPassword" v-model="userPassword" type="password"></b-form-input>
        </b-form-group>
        <b-form-group horizontal :label-cols="2" label-for="userConfirmPassword" label="Powtórz hasło">
          <b-form-input id="userConfirmPassword" v-model="userPasswordRepeat" type="password"></b-form-input>
        </b-form-group>
        <b-form-group horizontal :label-cols="2" label-for="userEmail" label="E-mail">
          <b-form-input id="userEmail" v-model="userEmailAddress" type="email"
                        :placeholder="user.emailAddress"></b-form-input>
        </b-form-group>
        <b-form-group horizontal :label-cols="2" label-for="userTelephone" label="Numer telefonu">
          <b-form-input id="number"></b-form-input>
        </b-form-group>
        <b-form-group horizontal :label-cols="2" label-for="userCity" label="Miasto">
          <b-form-input id="userCity"></b-form-input>
        </b-form-group>
        <b-button variant="primary" class="float-right" v-on:click="changeUserData()">Zatwierdź</b-button>
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
    }
  }


</script>

<style>

  .profile-container {
    background-color: rgba(230, 247, 255, .9);
  }


</style>
