import App from './App'
import Vue from 'vue'
import tmVuetify from "./tm-vuetify";

Vue.use(tmVuetify)
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
    ...App
})
app.$mount()