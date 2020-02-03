import Vue from 'vue'
import VueResource from 'vue-resource'
import 'api/resource'
import App from 'pages/App.vue'
import router from 'router/router.js'
import '@babel/polyfill'
import store from 'store/store.js'

Vue.use(VueResource)

new Vue({
    el: '#app',
    store,
    router,
    render: a => a(App)
})